package com.ch.notice.notice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.notice.domain.NoticeDTO;
import com.ch.notice.repository.NoticeDAO;
import com.mysql.cj.protocol.x.Notice;

// html로 부터 글쓰기 요청을 받는 서블릿 정의
// jsp도 서블릿이므로, 현재 이 서블릿의 역할을 대신할 수도 잇다..
// 하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기 위해 나온 기술이므로,
// 현재 이 서블릿에서는 디자인이 필요 없기에, 굳이 jsp를 사용할 필요가 없다.
public class RegistServlet extends HttpServlet{
	NoticeDAO noticeDAO=new NoticeDAO(); // 다른 로직은 포함되어 있지 않고, 오직 DB 와 관련 CRUD 만을 담당하는 중립적 객체를 ~~~
	
	
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
		
		NoticeDTO notice = new NoticeDTO(); // empty, 빈상태
		notice.setTitle(title);
		notice.setWriter(writer);
		notice.setContent(content);
		
		// 모두 채워졌으므로, 아래의 메서드로 insert완료  단 반환값에 따라 성공, 실패 여부를 처리해야 한다.
		
		int result= noticeDAO.regist(notice);
		
		out.print("<script>");
		if(result<1) {
			out.print("alert('등록성공');");
			out.print("history.back();");
			
		} else {
			out.print("alert('등록성공');");
			out.print("location.href='/notice/list.jsp';");
					
		}
		out.print("</script>");

	}
}
