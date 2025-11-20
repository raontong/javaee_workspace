package board;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter; //문자기반의 출력 스트림
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

public class ListServlet extends HttpServlet{
    //웹브라우저로 접근하는 유저들에게 메시지 출력하기
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // javaSE 의 스트림 객체를 사용한다
        PrintWriter out = response.getWriter(); //응답 객체로 부터 스트림을 얻는다. 왜? 클라이언트에게 물자열 출력하려고

        out.print("this is my response data");
    }
    
}