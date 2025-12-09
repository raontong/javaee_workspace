package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*음식에 대한 판단 요청을 처리하는 컨트롤러
 * MVC - 개발이론, 방법론
 * Model2 - 그 이론을 javaee 기술로 구현해 놓은 모델
 * 				M - java 순수 클래스
 * 				v -jsp, HTML 
 * 				C - 1) 웹서버에서 실행 될수 있어야한다
 * 				     2) 클라이언트의 요청을 받을 수 있어야한다
 * 					     결론 - 서블릿 밖에 없음
 * 
 * 모델2의 컨트롤러의 요건 위 1, 2번
 * */
public class FoodController extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트의 요청 파라미터 받기
		request.setCharacterEncoding("utf-8");
		
		String food= request.getParameter("food");
		// 모델 객체에 일 시키기!!
		
		
	}

}
