package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;


// 수정요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	BoardDAO boardDAO = new BoardDAO(); //jsp 선언언부와 동일
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 4개를 넘겨받아 쿼리를 직접 실행하지 말고, DAO 에게 시키자!!
		
		response.setContentType("text/html;charset=utf-8"); // 응답 페이지에 대한 인코딩
		request.setCharacterEncoding("utf-8"); //  파라미ㅏ터 값에 대한 인코딩
		PrintWriter out=response.getWriter();
		
		String board_id = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		System.out.println(board_id); // 콘솔출력
		System.out.println(title); // 콘솔출력
		System.out.println(writer); // 콘솔출력
		System.out.println(content); // 콘솔출력
		
		// Update boardUpdate set title=?, writer=?, content=? where board_id=? 
		
		// 매개 변수로 낱개의 파라미터로 보내지 말고, Board DTO 모아서 전달하자
		
		Board board = new Board();
		board.setBoard_id(Integer.parseInt(board_id));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result=boardDAO.update(board);
		
		out.print("<script>");
		if(result>0) {
			out.print("alert('수정 성공');");
			out.print("location.href='/board/detail.jsp?board_id="+board_id+"';"); //변수 넣는방법 !! 중요
		} else {
			out.print("alert('수정 실패');");
			out.print("history.back();");
			
		}
		out.print("</script>");
		
	}
}



