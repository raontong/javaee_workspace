package com.ch.mvcframework.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 엔터프라이즈급의 규모가 큰 애플리케이션에서 클라이언트의 수많은 요청마다 1:1대응하는 서플릿을 선언하고 
 * 매핑한다면, 매핑규모가 너무나 방대해지고 유지보수성이 오히려 저해된다.
 * 해결책) 앞으로 요청에 대한 매핑은 오직 하나의 진입점으로 몰아서 관리하자!! (예 - 대기업의 고색센터와 흡사)
 * */
public class DispatcherServlet extends HttpServlet{
	// 결국  if 문을 커맨드 패턴과 팩토리 패턴을 이용하여 대체하기 위한 준비물들...
	FileInputStream fis;
	Properties props;
	
	
	// 	아래의 init 은 서블릿이 인스턴스가 생성돠어진 직후에 호출되는 서블릿 초기화 목적의 메서드이다
	//	init() 메서드 안에 명시된 매개변수인 ServletcConfig 은 단어에서 이미 느껴지듯.. 이 서블릿과 관련된 환경 정보를 갖고 있는 객체이다
	public void init(ServletConfig config) {
		try {
			/*
			request 의 자료형? HttpSerbletRequest
			session의 자료형? HttpSession
			application의 자료형? ServletContext
			*/
			
			// ★★★서블릿의 환경 정보를 가진 객체인 ServletConfig를 활용하여 현재 애플리케이션을 정보를 가진 ServletContext 를 얻기
			ServletContext application=config.getServletContext();
			
			// 현재 웹애플리케이션이 이클립스 내우 톰켓으로 실ㄹ행될지, 아니면 실제 서버에서 실행될지 개발자가 알필요가 없이 현재 애플리케이션을 
			// 기준으로 파일명만 명시하면, 리눅스건, 맥이건, 윈도우건 상황에ㅐ 맞게 알아서 경로를 반환..
			String paramValue=config.getInitParameter("contextConfigLocation");
			System.out.println(paramValue);
			String realPath=application.getRealPath(paramValue);
			
			System.out.println(realPath);
			
			// C:\\javaee_workspace\\mvcframework\\src\\main\\webapp\\WEB-INF\\servlet-mapping.txt
			// 고정된(하드코드) 사용금지 >> 외부환경으로 뺴서 유지보수
			fis = new FileInputStream(realPath);
			props = new Properties();
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		 // 프로퍼스티가 사용할 스트림 로드
			e.printStackTrace();
		}
	}
	
	// 음식, 영화, 블로그 , 음악 등등의 모든 요청을 이 클래스에서 받아야 함. 이때 요청시 메서드가 Get, Post, Put=, Delete 모든 종류의
	// 요청을 다 받을 수 있어야함.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("클라이언트의 요청감지");
		doRequest(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("클라이언트의 요청감지");
		doRequest(request, response);
	}
	// 클라이언트의 요청 방식이 다양하므로, 어떤 요청방식으로 들어오더라도, 아래의 메서드 하나로 몰아넣으면,  
	// 코드는 메서드마다 재작성할 필요가 없다
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("클라이언트의 요청감지");
		// 한번에 한글로 전환
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		/*
		★★★ 모든 컨트롤러의 5대 업무★★★
		1) 요청을 받는다
		2) 요청을 분석한다
		3) 알맞는 로직 객체에 일시킨다
		4) 결과는 뷰에서 보여줘야 하므로, 뷰페이지로 가져갈 결과 저장(세션 아닌 request 에)
		5) 결과페이지 보여주기		 
		*/
		
		// 요청분석 : 음식, 영화 등 현재 클라이언트가 요청한 유형이 무엇인지부터 파악
		// 손님이 알려줘야한다.. 사장님에게 
		// 클라이언트가 요청 시 사용한 주소 표현식인 URI 가 곧 클라이언트가 원하는게 무엇인지에 대한 구분값이기도 한다
		String uri= request.getRequestURI();
		System.out.println("클라이언트가 요청시 사용한 uri" + uri);
		
		/*
		아래의 코드에서 클라이언트의 모든 요청 마다 1:1 대응하는 if 문으로 요청을 처리하면 
		역시나 요청의 수가 방대해질 경우 유지보수성이 떨어짐..
		해결책? 
		각 요청을 조건문이 아닌 객체로 처리해야 함 = GOF (디자인 패턴 저자들) 는 Command Pattern + Factory Pattern 을 이용함
		Factory Pattern 이란? 객체의 생성방법에 대해서는 감추어 놓고, 사용자로 하여금 객체의 인스턴스를 얻어갈 수 있는 객체 정의 기법 
		*/
		//if(uri.equals("/movie.do")) {// 클라이언트가 영화에 대한 조언을 구함
			// 영화 전담 컨트롤러에게 요청 전달~~~
			//MovieController controller = new MovieController();
			//controller.execute(request, response);
			//String controllerPath=props.getProperty(uri);
			//System.out.println("영화에 동작할 하위 전문 컨트롤러는 "+ controllerPath);
		//} else if(uri.equals("/food.do")) { // 클라이언트가 음식에 대한 조언을 구함
			// 음식 전담 컨트롤러에게 요청 전달~~~
			//FoodController controller = new FoodController();
			//controller.handle(request, response);
		//}
		String controllerPath=props.getProperty(uri); // uri 클라이언트가 요청 시 사용한 주소
		System.out.println(uri+"에 동작할 하위 전문 컨트롤러는 "+ controllerPath);
		
		// 여기 까지는 하위 컨트롤러의 이름만을 추출한 상태이고 실재 동작하는 클래스 및 인스턴스는 아니다.
		// 개발자가 직접 메모리에 static 영역으로 올리는 방법( 동적으로)
		try {
			// 클래스에 대한 정보를 가진 클래스.. 현재 이클래스가 보유한 메서드명, 생성자, 속성들..
			Class clazz=Class.forName(controllerPath); // 동적으로 클래스가 로드된다(static== method 영역에)
																			// static 영역에 동적으로 클래스의 코드 올리기
			//clazz.newInstance();// deprecated... 추후 버전에서는 사용안된다
			Object obj= clazz.getConstructor().newInstance(); // new 연산자만이 인스턴스를 만들수 있는 것은 아니다.
			
			// 메모리에 올라온 하위 컨트롤러 객체의 메서드 호출. 
			// 자식으로 내려가야함 근데 food , movie 컨트롤러가 존재해서 추상적인 그냥 부모 만들어서 컨트롤러 사용
			// 메모리에 올라온 객체가 MovieController or FoodController 인지 알수 없기 때문에, 
			// 이들의 최상위 객체인  Controller 로 형변환한다!!
			Controller controller=(Controller)obj;
			// 아래의 메서드 호출의 경우, 분명 부모 형인 Controller 형의 변수로 메서드를 호출하고는 있으나, 
			// 자바의 문법 규칙상 자식이 부모의 메서드를 오버라이드 한 경우(업그레이드 한 것으로 간주하여) 자식의 메서드를 호출한다
			// 즉 자료형은 부모형이지만, 동작은 자식 자료형으로 할 경우 현실의 생물의 다양성을 반영하였다고 하여ㅏ 다형성(=polymorphism) 이라한다.
			controller.execute(request, response); // 메서드 호출
			
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	// 서블릿의 생명주기 메서드중, 서블릿이 소멸할떄 호출되는 메서드인 destory() 재정의
	// 반드시 닫아야할 자원등을 해제할때 중요하게 사용..
	public void destory() {
		if(fis!=null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}










