package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.movie.model.MovieManager;
/*
 * MVC란? Model, View, Controller 를 의미하는 디자인 패턴 중 하나를 의미
 * MVC는 다운로드 ㅂ다거나, 눈에 보이는 파일이나 소스가 아니라, 그냥 전산분야에서 예전부터 선배들로 내려오는 개발 방법이론일 뿐이다.
 * 
 * MVC 주요내용?
 * 디자인 영역과 로직(모델) 영역은 완전히 분리시켜야, 유지보수성이 좋아진다
 * 
 * Model2 란?
 * javaEE 분야에서 구현한 MVC 패턴을 가리킴
 * 즉, javaEE 분야(웹)에서 애플리케이션을 개발할때 디자인과 로직을 분리시키기 위해 사용하여ㅏ아 할 클래스 유형은 아래와 같다
 * 
 * 1) M - 중립적인 모델이므로 java 클래스로 작성
 * 2) V -  웹상의 디자인을 표현해야 하므로, Html, jsp로 작성
 * 3) C - 클라이언트 요청을 받아야 하고, 오직 javaEE서버에서만 실행될 수 있어야 하므로 서블릿임
 *		 	주의) jsp 로 사실 서블릿이므로  Controller 역할을 수행할 수는 있지만, jsp가 주로 디자인에 사용되므로, 
 *		 	컨트롤러서의 역할은 주로 서블릿으로 구현함
 **/
public class MovieController implements Controller{
										/* is a 의 의미*/
	MovieManager manager=new MovieManager();
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메시지 만들기 
		
		request.setCharacterEncoding("UTF-8");
		String movie=request.getParameter("movie");
		// 이 클래스의 목적은 컨트롤러이므로, 더이상 영역을 침해해서는 안됨, 즉 여기서 out.print() 를 시도한다는것은
		// MVC 중 View 영역을 침범하게 됨(월권행위)
		// out.print(movie);

		// 각 영화에 대한 판단을 해주는 코드가 별도의 모델 객체로 분리되었다!! 
		// 분리 시킨이유? 웹뿐만 아니라, 다른 플랫폼에서도 재사용하기 위해, 재사용=시간=돈
		// 재사용 대상(모델!!)
		String msg=manager.getAdvice(movie);
		
		// 이때 이요청과 연관된 세션이 드디어 생ㅅ성되면서, 자동으롸session Id가 발급된
		// 또한 응답 정보 생성시 클라이언에게 쿠키로 session ID 함께 전송됨
		// 쿠키 - 영구적(Persistence cookie=하드), 세션쿠기(메모리)
		// HttpSession session=request.getSession();
		
		// 영화에 대한 판단 결과는, 세션이 죽을때까지 함께 생존할수 있으므로,
		// 이요청이 종료되어도, 그 값을 유지할 수 있다
		// session 메모리 낭비
		// session.setAttribute("msg",  msg);

		// 세션 말고도, 데이터를 전달하는 또다른 방법이 있는 포워딩.
		// 현재 들어온 요청에 대해 응답을 하지 않은 상태로,  또 다른 서블릿에게 요청을 전달함
		// 이떄 지정된 result.jsp의 서블릿으 serive() 메서드가 호출!
		request.setAttribute("msg", msg); // 세션과 생명유지 시간만 틀릴뿐 사용방법은 같다!
		
		// 현재 서블릿에서 응답을 처리하지 않았기 떄문에 request 는 죽지않고 result.jsp의 서블릿까지 생명이 유지됨..
		RequestDispatcher dis=request.getRequestDispatcher("/movie/model2/result.jsp"); // 포워딩하고 싶은 자원의 URL
		dis.forward(request, response);
		
		// 아래의 코드는 응답을 하면서 브라우저로 하여ㅏ금 재접속하라는 명령이다. 따라서 응답하게됨 .. 사용금지
		// 위의 판단결과를 여기서 출력하면 MVC  위배됨.. 따라서 판단결과를 별도의 디자인 영역에서 보여줘야한다.!!
		// response.sendRedirect("/movie/modelk2/result.jsp"); // <scrip>location.href=url</script> 같다
	}
	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}
}











