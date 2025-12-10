package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

/*
 * 글쓰기 요청을 처리하는 하위 컨트롤러
 * 1) 요청을 받는다(dispatchServlet)
 * 2) 요청을 분석(dispatchServlet)
 * 		하위 업무 3) 알맞는 로직 객체에 일시킴(하위 컨트롤러가) 
 * 		하위 업무 4) 결과 페이지에 가져갈 것이 있을경우 결과를 저장(request)
 * 5) 컨트롤러는 디자인에 관여하면 안되므로 알맞는 view 페이지를 보여주기
 * */
public class RegistController implements Controller{
	BoardDAO boardDAO=new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3단계 로직 객체에게 일시키기
		
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		Board board= new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result=boardDAO.insert(board);// 등록시키기
		
		// 등록후 성공시, 게시물 목록을 보여ㅏ줘야함..
		response.sendRedirect("/board/list.jsp");// 
		
		
		}
}





