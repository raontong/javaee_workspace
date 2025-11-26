package com.ch.notice.notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*글 한건  삭제 요청을 처리하는 서블릿 정의
	delete from notice where notice_id = 넘겨받은 파라미터값;
 	pk값은 내용이 길지 않으며, 보안상 중요 하지도 않기 때문에 get 방식으로 받자
  */
public class DeleteServlet extends HttpServlet {
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// jsp 에서의 page 지시 영역과 동일한 효과를 주기 윈한 코드
		response.setContentType("text/html;charset=utf-8"); // MINE 타입(브라우저가 이해하는 형식을 작성해야 함)
															// image/jpg, application/json....

		PrintWriter out = response.getWriter(); // 고양이가 응답시 참고할 문자열을 모아놓을 스트림

		// 클라이 언트가 요청을 시도하면서 함께 지참해온, notice_id 파라미터값을 받자
		String notice_id = request.getParameter("notice_id");
		System.out.println("넘겨받은 notice_id 는 " + notice_id);

		// 드라이버 로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 접속
			con = DriverManager.getConnection(url, user, password);

			// 쿼리문 날리기 delete from notice where notice_id=
			String sql = "delete from notice where notice_id=" + notice_id;
			pstmt = con.prepareStatement(sql);

			// 실행
			int result = pstmt.executeUpdate(); // Dml 수행 후, 영향을 받은 레코드 수
			
			out.print("<script>");
			if (result < 1) {
				// out.print()로 js를 출력할때는 반드시 종료 ; 찍어야함
				out.print("alert('삭제 실패');");
				out.print("history.back();"); // 웹브라우저의 뒤로가기 버튼을 누른 효과가 난다.
			} else {
				out.print("alert('삭제 성공');");
				out.print("location.href='/notice/list.jsp';"); // 목록으로 이동
			}
			out.print("</script>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally{
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
