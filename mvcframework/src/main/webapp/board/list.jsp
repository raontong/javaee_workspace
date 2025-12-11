<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ch.mvcframework.dto.Board" %>
<%
	//4단계  .do로 들어가지 않고 바로 list로 접속하면 안되는 이유 ? null 이다. 컨트롤러로 들어가야한다   
	List<Board> list = (List)request.getAttribute("list");
	out.print("게시물이 담긴 리스트는 " + list.size());
%>
<!DOCTYPE html>
<meta charset="UTF-8">
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<script>
	$(()=>{
		$("button").click(()=>{
			location.href="/board/write.jsp";
		});
	});
</script>
<body>

<div class="container">
  <h2>Basic Table</h2>
  <p>The .table class adds basic styling (light padding and horizontal dividers) to a table:</p>            
  <table class="table">
    <thead>
      <tr>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
      	<% for(int i=0; i < list.size(); i++) { %>
      	<%Board board=list.get(i); %>
      <tr>
      	<td>
      		<!--
      			MVC 프레임웍상에서 데이터 베이스 연동이 필요한 기능에서 jsp를 바로 호출하면 안되는 이유?
      			반드시 컨트롤러를 거쳐야만, 모델에게 일을 시키고 그 결과를 컨트롤러가 저장해주므로 만일 jsp를 바로 호출하면
      			컨트롤러를 만나자 
      		  -->
      		<a href="/board/detail.do?board_id=<%=board.getBoard_id()%>"><%=board.getTitle() %></a>
		<td><%=board.getWriter() %></td>
		<td><%=board.getContent() %></td>
      	</td>
      </tr>
      <%} %>
 	<tr>
  		<td colspan="3">
    		<button class="btn btn-primary">글등록</button>
  		</td>
	</tr>
    </tbody>
  </table>
</div>

</body>
</html>
