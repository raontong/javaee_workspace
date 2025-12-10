<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// JSP는 서블릿에서 개발자가 직접 자료형을 이용하여 사용해야 할 객체들을 좀더 편리하게 사용할 수 있도록 
	// 내장객체를 지원함. 따라서 개발자는 자료형을 명시하지 낭ㅎ고 이미 전해진 객체의 변수명을 사용할 수 있다.
	// out. reqeust, response, session, application
	// 1) appliscation 내장객체 - 애플리케이션의 전역적 정보를 가진 객체(서블릿에서의 자료형은 ServletContext 이다.)		
	//						톰켓을 켤때부터 끌때까지 생명력을 갖는 강력한 객체임, 만일 이 안에 회원정보를 넣으면 톰켓과 생명을 같이함
	//					 	javaEE에서 데이터를 담을 수 있는 객체 중 가장 생명력이 길다.
	// 2) session 내장겍체 - 클라이언트의 세션쿠기가 유효한 동안, 서버에서 정해놓은 일장시간 동안 재요청이 없을때까지... 
	// 							로그인 인증에 많이 사용함
	// 3) request 내장객체 - 요청이 들어와서 응답이 처리도리때까지만 생명ㅇ을 유지할 수 있음

	application.setAttribute("born","서울");
	session.setAttribute("id","gun");
	request.setAttribute("hobby", "여행");
%>
<a href="/test/result.jsp">결과</a>