package com.ch.memberapp.member;

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

import com.ch.memberapp.util.ShaManager;

/*회원 가입 요청을 처리하는 서블릿*/
public class RegistServlet extends HttpServlet {
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";

	Connection con;
	PreparedStatement pstmt;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// 넘겨 받은 파라미터 중 비밀번호의 경우, 평문이 아닌 암호화 시켜서 DB insert
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");

		PrintWriter out = response.getWriter();

		out.print("아이디는 " + id + "<br>");
		out.print("비밀번호는 " + ShaManager.getHash(pwd) + "<br>");
		out.print("이름은 " + name + "<br>");

		try {
			// 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 접속
			con = DriverManager.getConnection(url, user, password);
			if(con== null) {
				out.println("접속 실패");
			}else {
				out.println("접속 성공");
				
			
				// 쿼리문 수행 Insert into Member(id,pwd,name)_DynValueStub(?,?,?)
				String sql = "insert into member(id, pwd, name) values(?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, ShaManager.getHash(pwd));
				pstmt.setString(3, name);
				
				int result = pstmt.executeUpdate();
				
				StringBuffer tag=new StringBuffer();
			
				tag.append("<script>");
				if (result < 1) {
					tag.append("alert('가입 실패');");
				} else {
					tag.append("alert('가입 성공');");
					tag.append("location.href='/member/login.jsp';");
				}
				tag.append("</script>");
				
				// 스트림에 스크립트 담아놓기, 추후 고양이가 이 스트림을 보고 코딩한다. html 컨텐츠 생성 후 전송
				out.print(tag.toString());
				
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("오류 발생: " + e.getMessage());
			
		} finally {
			try {
	            if (pstmt != null) { 
	            	pstmt.close();
	            }
	            if (con != null) {
	            	con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}

