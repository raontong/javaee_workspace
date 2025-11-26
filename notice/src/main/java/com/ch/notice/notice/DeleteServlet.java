package com.ch.notice.notice;

import java.io.IOException;
import java.net.Authenticator.RequestorType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*글 한건  삭제 요청을 처리하는 서블릿 정의
	delete from notice where notice_id = 넘겨받은 파라미터값;
 	pk값은 내용이 길지 않으며, 보안상 주용하지도 않기 때문에 get 방식으로 받자
 
  */

public class DeleteServlet extends HttpServlet{
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
	String notice_id=request.getParameter("notice_id");
	System.out.println("넘겨받은 notice_id 는 " + notice_id);
	
	}
}
