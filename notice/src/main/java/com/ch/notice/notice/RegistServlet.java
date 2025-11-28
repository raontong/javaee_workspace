package com.ch.notice.notice;

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

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

// html로 부터 글쓰기 요청을 받는 서블릿 정의
// jsp도 서블릿이므로, 현재 이 서블릿의 역할을 대신할 수도 잇다..
// 하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기 위해 나온 기술이므로,
// 현재 이 서블릿에서는 디자인이 필요 없기에, 굳이 jsp를 사용할 필요가 없다.
public class RegistServlet extends HttpServlet{
	// 오라클의 경우 jdbc:mysql://localhost:~~~
	String url="jdbc:mysql://localhost:3306/java";
	String user="servlet";
	String password="1234";
	
	Connection con; //접속을 시도하는 객체가 아니라, 접속이 성공ㅎ된이휴ㅜ 그 정보를 가진 객체이므로, 접속을 끊을때 이용할 수 있다.1
								// 만일 이 객체가 메모리에 올라오지 못한 경우, 접속 실패로 본다.
	
	PreparedStatement pstmt; // 쿼리문을 수행하는 객체(접속이 되어야 쿼리를 실행할수 있기 떄문에 Connetion 객체로 부터 생성)
	
	@Override
	// 클라이언트의 요청이 Get 방식일 경우 , 아래의 메서드 동작 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("요청 감지"); // 자바의 톰켓 콘솔에 출력
		
		// 아래의 두줄을 jsp로 구현할 경우 page contentType="text/html";"utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8"); // tomcat이 응답정보를 이용하여 html 화 시킬때 한글에 대한 처리
		request.setCharacterEncoding("utf-8"); // 클라이언트가 전송한 데이터에 한글이 포함되어 잇을 경우, 깨지지 않도록 인코딩
		
		// 클라이언트가 전송한 파라미터를 받자
		
		String title= request.getParameter("title");
		String writer= request.getParameter("writer");
		String content= request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		out.print("클라이언트가 전송한 제목은"+title+"<br>");
		out.print("클라이언트가 전송한 작성자는"+writer+"<br>");
		out.print("클라이언트가 전송한 내용은"+content+"<br>");
		
		// mysql의 java db 안에  notice 에 insert!!
		// 앞으로 필요한 라이브러리(ㅓㅁㄱ)가 잇을 경우, 일일이 개발자가 손수 다운로드 받아
		// WEB-INF/LIB에 옴기지 말고, maven 빌드툴을 이용하자!!
		// Build - 실행할수 있는 상태로 구축하는 것을 말함
		// 			src/animal.Dog.java 작성 후 . bin/animal.Dog.class에 대해ㅐ
		// 			직접 javac -d경로로 대상

			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // 벤더 
				out.println("드라이버 로드 성공");
				
							//접속
				con = DriverManager.getConnection(url, user, password);
				if(con== null) {
					out.println("접속 실패");
				}else {
					out.println("접속 성공");
					 
					//접속 성공 되었으므로, 쿼리를 실행할 수 잇다. 의(insert, update,delete)
					//  바인드 변수개념은 요청이 있을 경우 설명
					String sql = "insert into notice(title, writer, content) values(?,?,?)";
					pstmt=con.prepareStatement(sql);
					
					//실행전에 먼저 바인드 변서 값부터 설정하자
					pstmt.setString(1,title);
					pstmt.setString(2,writer);
					pstmt.setString(3,content);
					
					// insert 실행 (DML일 경우 excuteUpdate() 호출, 반환값은? 쿼리에 의해 영향을 받은 레코드 수가 반환, insert 는 1반환
					int result=pstmt.executeUpdate(); // 쿼리가 실행되는 순간!!
					if (result<1) {
						out.println("등록 실패");
					}else {
						out.println("등록 성공");
					}
					//  브라우저로 하여금 지정한 url로 다시 재접속하도록 명령..
					response.sendRedirect("/notice/list.jsp"); 
				
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
						
					} catch (SQLException e ) {
						e.printStackTrace();
					}
				}
			}
			

	}
}
