<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.ch.model1.repository.NewsDAO"%>
<%@ page import="com.ch.model1.util.PagingUtil"%>
<%@ page import="com.ch.model1.dto.News"%>
<%@ page import="java.util.List"%>

<%!
	// 목록 가져오기
	NewsDAO newsDAO = new NewsDAO();
	PagingUtil pgUtil = new PagingUtil(); // 페이징 처리 객체
%>
<%
	List<News> newsList = newsDAO.selectAll();
	pgUtil.init(newsList, request); // 페이징 처리객체가 이 시점부터 알아서 계산
	
	out.print("총 레코드 수는 " + pgUtil.getTotalRecord());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>

<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>No</th>
    <th>제목</th>
    <th>작성자</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
   	<%
   		int curPos=pgUtil.getCurPos(); // 페이지당 시작 리스트 내의 인덱스
   		int num=pgUtil.getNum(); // 페이지당 시작 번호(언제나 1이상 이어야 함)
   	%>	
  	<%for(int i=0; i < pgUtil.getPageSize(); i++){ %>
  	<%if(num<1) break;%>
  	<%
  		//레코드 뿌리기 1----- 0 / 2-------1 ..... 
 		News news=newsList.get(curPos++); 		 		
  	%>
  <tr>
  	
    <td><%=num-- %></td>
    <td>
    	<a href="/news/content.jsp?news_id=<%=news.getNews_id() %>"><%=news.getTitle() %></a>
    	<%if(news.getCnt()<0){ // 댓글이 존재한다면 %>
    		<%=news.getCnt()%>
    	<%} %>
    </td>
    <td><%=news.getWriter() %></td>
    <td><%=news.getRegdate() %></td>
	<td><%=news.getHit() %></td>
  </tr>
  <%} %>
  
  <tr>
      <td colspan="5">
        <button onClick="location.href='/news/content.jsp';">글등록</button>
      </td>
      <td colspan="4">

      </td>
   </tr>
</table>

</body>
</html>

