<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.ch.mybatisapp.dto.Board"%>
<%@ page import="com.ch.mybatisapp.repository.BoardDAO"%>
<%
	//  파라미터를 넘겨받아 게시풀 1건 등록 
	request.setCharacterEncoding("utf-8");

	String title=request.getParameter("title");
	String writer=request.getParameter("writer");
	String content=request.getParameter("content");
	
	Board board = new Board();
    board.setTitle(title);
    board.setWriter(writer);
    board.setContent(content);

	BoardDAO boardDAO = new BoardDAO();
	int result = boardDAO.insert(board);
	if(result<1) {
		out.print("등록 실패");
	} else {
		out.print("등록 성공");
	}
%>


