package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;

/*오라클에 들어있는 회원의 목록을 가져와서 화면에 출력
01_ 클라이언트 요청 
02 _서블릿 컨테이너
03_ HTTP 메서드 확인
04_ GET → doGet() / POST → doPost()
05_ 서블릿 로직 처리
06_ 응답 인코딩/컨텐츠 타입 설정
07_ 클라이언트 브라우저 출력
*/

public class MemberList extends HttpServlet{
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pass = "1234";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// [1] 응답 설정: 브라우저에게 HTML + UTF-8로 읽으라고 알려줌
		//                   이렇게 세미콜론으로 연결하면 아래 메서드 작성불필요
		response.setContentType("text/html;charset=utf-8");
		// request (요청) / response (응답)

		// [2] 브라우저로 데이터 출력하 객체 준비
		PrintWriter out = response.getWriter();
		
		// [3] JDBC 관련 객체 선언
		// 접속 후 그 정보를 가진 객체, 따라서 이 객체가 null 인 경우 접속은 실패
		// 쿼리문 수행 객체, 오직 Connection 객체로부터 인스턴스 얻음
		// 쿼리문이란, 접속을 전제로 하기 때문..
		Connection con = null; 					// DB와의 연결(접속) 정보 객체_ DB 서버와 <★전화선을 연결>하는 역할
		PreparedStatement pstmt = null;	// SQL 쿼리를 실행 객체_ 전화선을 통해 실제로 <★메시지를 보내는 사람>
		ResultSet rs = null; 						// SELECT 문 결과인 <★표>를 가진 객체

		try {
			// [4] 드라이버 로드_ JDBC(자바 데이터베이스 연결성)
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			out.print("드라이버 로드 성공");
			
			// [5] 오라클에 접속
			con = DriverManager.getConnection(url, user, pass);
			if(con == null) {
				out.print("접속 실패<br>");
			}else {
				out.print("접속 성공<br>");
				
				// [6] SQL 준비_  삭제(DELETE), 추가(INSERT), 수정(UPDATE) 도 가능
				String sql= "select * from member order by member_id asc"; // 오름차순
				pstmt = con.prepareStatement(sql);		// 쿼리 수행 객체 생성
				
				// [7] SQL 실행 (SELECT → executeQuery)
				// DML인 경우 executeUpdate()였지만, select문 인 경우 원격지 서버의 레코드(표)를 네트워크로
				// 가져와야 하므로, 그 표 결과를 그대로 반영할 객체가 필요한데, 이 객체를 가리켜 ResultSet이라 한다.
				// rs를 그냥 표 그자체로 생각해도 무방.. 하지만, rs 내에 존재하는 레코드들을 접근하기 위해서는
				// 레코드를 가리키는 포인터 역할을 해주는 커서를 제어해야 한다.. 이 커서는 rs가 생성되자 마자
				// 즉 생성 즉시엔 어떠한 레코드도 가리키지 않은 상태이므로, 개발자가 첫 번째 레코드로 접근하려면 포인터를
				// 한칸 내려야 한다.
				rs = pstmt.executeQuery(); 
								
				// [8] HTML 테이블 태그 준비
				// 반복문으로 모든 레코드를 출력하세요
				StringBuffer tag = new StringBuffer();
				tag.append("<table width=\"100%\" border=\"1px\">");
				tag.append("<thead>");
				tag.append("<tr>");
				tag.append("<th>id</th>");
				tag.append("<th>pwd</th>");
				tag.append("<th>name</th>");
				tag.append("<th>email</th>");
				tag.append("</tr>");
				tag.append("</thead>");
				tag.append("</tbody>");
				
				// [9] 결과 집합 반복 처리 , 서를 한 칸 내려서 레코드 접근
				while(rs.next()) { // 기존커서의 위치보다 한칸 전진, 
					tag.append("<tr>");
					tag.append("<td>"+rs.getInt("member_id")+"</td>");
					tag.append("<td>"+rs.getString("id")+"</td>");
					tag.append("<td>"+rs.getString("pwd")+"</td>");
					tag.append("<td>"+rs.getString("name")+"</td>");
					tag.append("<td>"+rs.getString("regdate")+"</td>");
					tag.append("<td>"+rs.getString("email")+"</td>");
					// 현재 커서가 위치한 한줄내에서 컬럼명이 id 인 컬럼의 값을 반환
					// System.out.println(rs.getString(("id")+","+rs.getString(("pwd")+rs.getString(("name")+rs.getString(("email"));
					tag.append("</tr>");
					out.print(false);
				}
				tag.append("</tbody>");
				tag.append("</table>");
				
				 // [10] 브라우저로 결과 출력
				out.print(tag.toString());
				out.print("<a href='/member/join.html'>가입하기</a>");
			}	
			
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패<br>");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			
			// [11] 자원 해제 (역순으로 닫기)
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}