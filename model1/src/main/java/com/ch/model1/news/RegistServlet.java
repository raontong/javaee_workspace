package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

/* 
클라이언트의 요청을 받아서 응답을 관리하고, DAO를 호출해서 실제 DB 작업을 위임
1 파라미터 추출 (request.getParameter("title") 등)
2 DTO 객체 생성 (News news = new News();)
3 DAO 호출 (newsDAO.insert(news)) → DB에 저장
4 결과에 따라 응답 처리
	- 성공: location.href='/news/list.jsp'로 목록 페이지 이동
	- 실패: history.back()으로 이전 페이지로 돌아가기
*/
// 사용자가 글을 등록하면 어떤 흐름으로 처리하고 어디로 보내줄까?
public class RegistServlet extends HttpServlet{
	NewsDAO newsDAO =new NewsDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8"); // 파라미터 안꺠지게
		
		// 클라이언트가 동기방식으로파라미터를 받아서 데이터베이스(DAO 이용하여 간접적으로 시키자) 
		//  (글 동록을 하면 다른화면으로 목록을 봐야해서)
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");;
		String content=request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		out.print("title="+title);
		out.print("writer="+writer);
		out.print("content="+content);
		
		// DAO 에게 일시키기
		News news= new News();
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);
		
		// DAO 에게 일시키기
		int result=newsDAO.insert(news);
		
		// 클라이언트가 동기 방식의 요청을 했기 떄문에 서버는 화면 전환을 염두에 두고, 
		// 순수 데이터보다는 페이지 전환 처리가 요규됨
		// 글등록후, 클라이언트의 브라우저로 하여금 다시 목록 페이지를 재요청하도록 만들자!!
		// response.sendRedirect(""); // 이코드 대신 location.href="" 사용해도 동일효과
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("<script>");
		// 결과를 출력 성공(목록보이게) or 실패(뒤로가기)
		if(result<1) {
			sb.append("alert('등록 실패');");
			sb.append("history.back();");
			
		}else {
			sb.append("alert('등록 성공');");
			sb.append("location.href='/news/list.jsp';");
		}
		sb.append("</script>");
		
		out.print(sb.toString());
		
	}
}
