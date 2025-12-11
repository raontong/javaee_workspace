package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

// 게시물 1건 요청을 처리하는 하위 컨트롤러
public class DetailController implements Controller{
	BoardDAO boardDAO = new BoardDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// select * from board where board_id=5
		
		// 3단계 
		String board_id=request.getParameter("board_id");
		boardDAO.select(0);
		Board board=boardDAO.select(Integer.parseInt(board_id));
		
		System.out.println("게시물 들어있는 DTO"+board);
		
		// board 를 결과 페이지인 (MVC중 View )까지 살려서 가져가려면,
		// request 에 담고
		// 포워딩 해야한다.
		
		request.setAttribute("board", board);
		// request는 죽으면 안되므로, 응답을 해서는 안되고 포워딩이 필요하다.
	}

	@Override
	public String getViewName() {
		return "/board/detail/result"; // board/detail.jsp 직접적으면 안됨~ servlet-mapping.txt 로 ....
	}

	@Override
	public boolean isForward() {
		return true; //포워딩 하겟다
	}
}
