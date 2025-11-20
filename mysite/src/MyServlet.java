package src;
/*
JAVAEE 기반으로 웹애플리케이션에서 실행 될 수 있는 특수한 클래스를 사용해야하는데,
이러한 서버에서만 해석 및 실행되어지는 클래스를 가리켜 서블릿(Serviet)이라한다..
그리고 javaEE 기반의 웹애플리케이션의 구성 디렉토리는 javaEE 스팩으로 정해져 있기 때문에
반드시 정해진 디렉토레에 .class / .jar등을 위치시켜야한다.

/WEB-INF : 웹브라우저를 통해서는 절대로 접근하지 못하는 보안된 디렉토리..
    - classes : 컴파일된 클래스 들...
    - lib : .jar 들이 위치
*/ 

// 아래의 클래스가 javaEE 서버에서 실행되려면 반드시 서블릿 클래스를 상속받아야 한다.
// HttpServiet 서버에 들어있다
import javax.servlet.http.HttpServlet;

class MyServlet extends HttpServlet{
        
    String name="puppy";
}