package com.ch.gallery.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.gallery.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

// 클라이언트의 업로드를 처리할 서블릿
public class UploadServlet extends HttpServlet{
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); // 응답  객체가 보유한 스트림 얻기
		
		// 업로드를 처리할 cos컴포넌트를 사용해보자 
		// MultipartRequest 객체는 일반 클래스이므로, 개발자가 new 연산자를 이용하여 인스턴스를 직접
		// 생성할 수 있다. 따라서 ㅇ 이객체가 지원하는 생성조사하여 사용
		
		// MultipartRequest는 생성자 업로드 처리를 하는 객체
		// file:///C:/Users/GUN/Downloads/cos-22.05/doc/index.html 에서 볼수있음
		// 4번째 생성자는 용량뿐만 아니라, 파일명에 한글이 포함되어 있어도 깨지지 않도록 처리가 되어있다..
		//  용량은 기본인 바이트 단위이다.(최소단위-bit) , //
		int maxSize=1024*1024*5; // 유지 보수성을 위해서는 직접 계산하지 않고, 풀어쓴다.
		MultipartRequest multi = new MultipartRequest(req, "C:\\upload", maxSize, "utf-8");
		
		// 클라이 언트가 전송한 데이터 중 텍스트 기반의 데이터를 파라미터를 이용하여 받아보자
		// 클라이언트가 전송한 데이터 인코딩 형식이 mukltipart/form-data 일때는 기존에 파라미터를 받ㅇ는 코드인
		// request.getParameter() 는 동작 하지 못함.. 대신 업로드를 처리한 컴 포넌트 를 통해서 파라미터값들을
		// 추출해야한다.
		String title =multi.getParameter("title");
		out.println("클라이ㅏ언트가 전송한 제목은" + title);
		
		
		// 이미 업로드된 파일은, 사용자가 정한 파일명이므로, 웹브라우저에서 표현시 불안할수 있음.
		// 해결책? 파일명을 개발자가 정한 규칙, 또는 알고리즘으로 변경한다
		// 방법) 현재시간(밀리세컨트까지 표현) , 해시- 16진수 문자열
		long time = System.currentTimeMillis();
		out.print(time);
		out.print("<br>");
		out.print("업로드 성공");
		
		// 방금 업로든된 파일명을 조사하여, 현재 시간과 확장자를 조합하여 새로운 파일명 만들기
		// 이미 업로든 된 파일 정보는 파일컴포넌트 스스로 알고 있다. 우리의 경우 multi 
		String oriName=multi.getOriginalFileName("photo"); // HTML 에서 부여한 파라미터명을 매개변수로 넣어줘야함
		out.print("<br>");
		out.print(oriName);
		
		// 자바의 string 조사하여 (어디부터 어디까지)
		String extend = StringUtil.getExtendsFrom(oriName);
		out.print("추출된 확장자는" + extend);
		
		// 파일명과 확장자를 구했으니, 업로드된 파일의 이름을 변경하자
		// 자바에서는 파일명을 변경하거나, 삭제하려면 javaSE.io.File 클래스를 이용해야한다..
		File file=multi.getFile("photo"); // 서버에 업로드된 파일을 반환해줌!!
		out.print("<br>");
		out.print(file);
		
		// File 클래스 매서드중 파일명을 바꾸는 매서드
		// renameTo () 의 매서드ㅓ의 매개변수에는 새롭게 생성될 파일의 경로를 넣어야한다
		String filename= time+"."+extend;

		boolean result= file.renameTo(new File("C:/upload/"+filename));
		out.print("<br>");
		if(result) {
			out.print("업로드 성공");
			
			Connection con=null;
			PreparedStatement pstmt=null; // 쿼리수행
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url, user, pass);
				if(con==null) {
					out.println("접속실패");
				}else {
					out.println("접속성공");
					
					// 오라클 접속하여 insert 문 수행하기
					String sql="insert into gallery(gallery_id, title, filename) values(seq_gallery.nextval, ?,?)";
					pstmt = con.prepareStatement(sql); // 접속 객체로 부터 쿼리수행 객체 인스턴스 얻기ㅏ
					
					// 쿼리문 수행에 앞서 바인드 변수값을 결정하자
					pstmt.setString(1,  extend);
					pstmt.setString(2,  filename);
					
					// 쿼리 수행 DML 이므로 executeUpdate() 사용해야함
					// executeUpdate() 는 쿼리 수행 후 영향을 받은 레코드 수를 반환하므로, 
					// insert 성공이라면 0이 아니어야한다
					int n= pstmt.executeUpdate();
					if(n<1) {
						out.println("등록 실패");
						
					}else {
						out.println("등록 성공");
						// 목록으로 자동 전환...
						
						resp.sendRedirect("/upload/list.jsp");
						
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			out.print("업로드 실패");
		}
	}
}
