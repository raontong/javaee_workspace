package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ResponseCache;
import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 마지막..클라이언트가 전송한 파라미터들을 받아서, 오라클에 넣기!
// 클라이언트의 요청이 웹브라우저 이므로, 즉 웹상의 요청을 받을 수 있고,
// 오직 서버에서만 실행 될수 있는 클래스인 서블릿으로 정의 하자!
public class JoinController extends HttpServlet{
		// doxx 형 메서드중 post방식을 처리하기 위한 doPost메서드 오버라이드 하자
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 나의 이름이 , 웹브라우저에 출력되게 끔..
		//web.xml에서 서블릿 매핑도 해야함..
		request.setCharacterEncoding("utf-8"); // 요청 정보를 가진 객체인 request에게 인코딩 지정
		response.setContentType("text/html;"); // 브라우저에게 전송할 데이터가 html 문서임을 알려줌
		response.setCharacterEncoding("utf-8"); // 이 html이 지원하는 인코딩 타입을 지정(한글 깨지지않기 위해)
		PrintWriter out = response.getWriter(); // 응답 객체가 보유한 출력스트림 얻기
		//★★★주의할점 아래의 코드에 의해, 클라이언트의 브라우저에 곧바로 데이터가 전송되는
		//추후 층답이 마무리하는 시점에 Tomcat과같은 컨테이너 서버가, out.print()에 의해 누적되어 있는 
		// 문자열을 이용하여, 새로운 html문서를 작성할때 사용됨..
		out.print("<h1>장영권</h1>");
		
		// JDBC를 오라클에 insert
		// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar파일을 클래스패스에 등록하자!
		// 하지만, 현재 사용중인 IDE가 이클립스라면, 굳이 환경변수까지 등록할 필요없고, 이클립스에 등록하면된다
		
		Connection con = null; // finally 에서 닫기 위해 try 문 밖으로 꺼내기
        PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버 로드 성공");
			
			// 오라클에 접속
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="servlet";
			String password="1234";
			
			// 접속후, 접속이 성공했는지 알기 위해선는, Connection 인터페이스가 null 인지 여부를 판단한다.
			
			con= DriverManager.getConnection(url,user,password);
			
	        if(con == null) {
	            out.print("접속실패");
	        }else {
	            out.print("접속성공");
	        }
	        
	        // 쿼리 수행 PreparedStatement 인터페이스가 담당
	        // JDBC 는 데이터베이스 제ㅐ품의 종류가 무엇이든 상관없이 DB를 제어할 수 있는 코드가 동일함..(일관성 유지 가능)
	        // 가능한 이유? 사실 JDBC 드라이버를 제작하는 주체는 벤더사이기때문에.. 모든 벤더사는 java언어를 제작한 오라클사에서 제시한 
	        // JDBC 기준 스펙을 따르기 떄문에 가능하다..  참고로 우리가 javaEE 시간에 별도로 개발툴킷을 설치할 필요가 없었던 이유는??
	        // 오라클사는 javaEE  에 대한 스펙만을 명시하고, 실제 서버는 개발하지 않는다. 결국 javaEE스펙을 따라 서버를 개발하는 벤더사를 
	        // 모두가 각자 고유의 기술로 서버는 개발하지만, 반드시 javaEE에서 명시된 객체명을 즉 api 명을 유지해야 하므로, 
	        // java개발자들은 어떤 종류의 서버던 상관없이 그 코드가 언제나 유지됨..
	        // 웹로직, JBoss, 제우스((국내), 레진
	        
	        String sql= "insert into member(member_id, id,pwd,name,email)";
	        sql+=" values(seq_member.nextval,?,?,?,?)"; // ★바인드변수
	        
	        pstmt =con.prepareStatement(sql);
	        
	        
	        
	        // 바인드 변수를 사용하게 되면, 물음표의 값이 무엇인지 개발자가 prepareStatement에게 알려줘야함
	        // 클라이언트가 전송한 파라미터 받기
	        // 네트워크로 전송된 모든 파라미터는 모두 문자로 전송됨
	        // 전송 파라미터의 인코딩을 지정해야, 한글등이 깨지지 않는다
	        
	        String id= 		request.getParameter("id");
	        String pwd=	request.getParameter("pwd");
	        String name=	request.getParameter("name");
	        String email=	request.getParameter("email");
	       
	        pstmt.setString(1,id);
	        pstmt.setString(2,pwd);
	        pstmt.setString(3,name);
	        pstmt.setString(4,email);
	        
	        //쿼리문 실행
	        int result=pstmt.executeUpdate();
	        
	        // DML 수행시 사용하는 메서드
	        // executeUㅔdage()는 반환값이 int, 그리고 이 int 의 의미느 ㄴ현재 쿼리문에 의해 영향을 받은
	        //레코드의 수를 반환..예) insert후 1건이 반영되므로 1이 반환, update,delete는 ndl qksghks
	        // 0이 반환되면 한건도 반영된 레코드가 ㅇ벗다는 의미로, 쿼리 반영실패를 의미
	        if(result !=0){
	        	out.print("가입성공");
	        }else {
	        	out.print("가입실패");
	        }
	        
	        
			
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con !=null) { //접속이 존재할떄만..
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


