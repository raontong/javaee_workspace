package com.ch.exgallery.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.exgallery.util.StringUtil;

import com.oreilly.servlet.MultipartRequest;


public class ExUploadServlet extends HttpServlet{
	String url="jdbc:orcle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	
	// 001 요청처리, doPost, 응답타입 text/html;charset=utf-8, PrintWrinter 응답
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//002 파일업로드 5mb , 객체생성, 저장경로, 인코딩
		int maxSize= 1024*1024*5;
		MultipartRequest multi = new MultipartRequest(request, "C:/upload", maxSize, "utf-8");
		
		//003 파라미터 추출
		String title=multi.getParameter("title");
		
		//004 현재시간 기준 새 파일명 준비
		long time = System.currentTimeMillis();
		String oriName = multi.getOriginalFileName("photo");
		String extend=StringUtil.getExtendsFrom(oriName); // 확장자 뽑아내는 매서드.. 
		String filename=time +"." +extend;
		
		// 005 업로드된 파일 이름 변경
		File file =multi.getFile("photo");
		
		
		
		
	}
}
