package com.ch.exmodel1.exboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 글입력 서블릿
public class ExRegistServlet extends HttpServlet{
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String title =request.getParameter("title");
		String write=request.getParameter("write");
		String content=request.getParameter("content");
		
		
		
		
		
		
		
		
	}
		
}
