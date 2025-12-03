<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="java.util.List"%>
<%!
	Member2DAO dao=new Member2DAO();
%>
<%
	//Tomcat 로그에 출력되지만, 우리의 경우 이클립스 내부 톰켓이므로, 이클립스 콘솔에 출력..
	// System.out.println("클라이언트의 요청감지"); 
	
	// 파라미터 받기
	request.setCharacterEncoding("utf-8"); // 파라미터가 깨지지 않도록 인코딩 지정
	String id=request.getParameter("id");
	String name=request.getParameter("name");
	String email=request.getParameter("email");
	
	System.out.println("id="+ id);  
	System.out.println("name="+ name);  
	System.out.println("email="+ email);  
	
	// DTO에 모으기
	Member2 dto= new Member2();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	
	int result=dao.insert(dto);
	System.out.println(result);
	
	// 입력성공 후 페이지 보여주기
	// 아래와 같이 비동기 요청에 대해, 응답 정보로서 페이지 접속을 일으키는 코드를 작성하게되면
	//클라이언의 브라우저가 지정한  URL로 재접속을 시도하기 떄문에, 그 재접속의 결과인 html을 서버로 부터 받게ㅐ되고
	//html을 전송받은 브라우저는 해당 html 을 화면에 랜더링 해버리므로, 새로그침 효과가 난다. 
	// 즉, 새로그침 없는 without reloading 기능이 상실..
	
	// 해결책  - 서버에서는 회원전체를 보내지 말고, 순수하게 목록 데이터만을 전송해주면, 
	// 클라이언트는 그 데이터를 js로 동적 처리
	
	// <핵심>게시물 목록 가져오기!!
	List<Member2> list=dao.selectAll();
	
	// 클라이언트에게 목록 데이터 보내기
	// out.print(list);
	/*
	클라이언트에게 응답 정보를 보낼때, 어자피 모두 문자열로 밖에 방법이 없다.
	하지만, 이 문자열을 넘겨받은 클라이언트의 브라우저의 자바 스크립트는 아래와 같은 문자열로 구성되어 있을경우
	원하는 데이터를 추출하기가 많이 불편하다.
	참고로, 아래와 같은 형식은 강의 편의상, 전송한 문자아ㅕㄹ의 예를 보여주기 위함 이었기 때문에, 또 다른 개발자들에 의해서
	저 아래의 데이터 형식은 임의로 바뀔  수 있다.
	문제점) 앞으로 우리는 REST API 다룰 것이므로, 추후 REST 서버를 구축하여ㅏ, 우리의 서버에 요청을 시도하는 다양한 종류의
			클라이언트(스마트폰, 웹브라우저, 자동차, 로봇) 등에게 데이터를 제공해 줄 예정인데, 
			이때 사용할 데이터 형식은 전세계적으로  XML 또는 JSON이 압도적이다.
			
	해결책? 전세계 개발자들이 주로 사용하는 표준형식의 데이터를 사용하자 (추천-JSON) 
	
	JSON이란? 문자열을 내의 데이터가 유달리 자파스크립트의 객체 리터럴 정의 기법을 따르는 경우,
					JASON문자열이라 한다.			
	*/
	
	// 아래의 json문자열은 말 그대로 문자열 이므로, java는 그냥 String 으로 처리한다. 
	StringBuffer data = new StringBuffer();
	data.append("{");
	data.append("\"name\":\"지노\",");
	data.append("\"email\":\"google\" ");
	data.append("}");
	
	out.print(data.toString()); //  클라이언트인 웹브라우저에게 보내기
	
	
	// out.print("id=tiger, name=호랑이, email=daum");
	
	
%>
