<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.ResultSet" %>

<%! // !붙이면, 선언부이다.(선언부란? 이 jsp가 서블릿으로 변환되어 질때, 멤버변수와 멤버메서드가 정의 되는 영역)
	String url="jdbc:mysql://localhost:3306/java";
	String user="servlet";
	String password="1234";
	
	Connection con;
	PreparedStatement pstmt; //커리 수행 객체
	ResultSet rs; // select 수행후 , 표를 담아 제어할 수 잇는 객체
	
%>
<%
	// jsp는 서블릿으므로, 이 영역(스크립틀릿)에서 개발자가 코드를 작성하면, 이 jsp가 Tomcat에 의해 서블릿으로 변환되어 질때
	//생명주기(init, service, destroy) 중 service() 메서드 영역에 코드를 작성한 것으로 처리
	// 따라서 클라이언트의 요청을 처리하는 메서드인 service() 에서 mysql 의 데이터를 가져와 화면에 출력!!
	// 주의) 서블릿으로도 가능은 하지만, 수많은 코드 라인마다 out.print() 출력해야 하므로, 디자인 작업시 효율성이 떨어진다.
	Class.forName("com.mysql.cj.jdbc.Driver"); //  mysql드라이버
	 
	//접속하기
	con=DriverManager.getConnection(url, user, password); // 접속하기
	String sql="select * from notice";
	
	// TYPE_SCROLL_SENSITIVE :  스크롤이 가능한 옵션
	// CONCUR_READ_ONLY : 오직 읽기 전용으로만..
	pstmt= con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리수행 객체 생성
	
	//DML 이 아닌 select이기 떄문에, 메서드 exccuteQuert() 를 써야함
	rs=pstmt.executeQuery(); 
	
%>

<!DOCTYPE html>
<html>
<head>
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
/*링크 밑줄제거*/
a {text-decoration: none;}

</style>
</head>
<body>

	<h2>Zebra Striped Table</h2>
	<p>For zebra-striped tables, use the nth-child() selector and add a
		background-color to all even (or odd) table rows:</p>

	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<% 
			rs.last(); // 커리문 select*from notice;에서  , 커서를 ResultSet 의 제일 마지막 행으로 이동 
			out.print("현재 테이블의 총 레코드 수는 "+ rs.getRow());
			// rs의 기본 속성은 ResultSet.TYPE_FORWARD_ONLY 로 되어 있음
			//TYPE_FORWARD_ONLY 상수로 지정되면, 커서가 오직 전방향으로 한칸씩만 이동가능함
			// PrepareStatment 생성 시 상수를 지정해야함.
			
			
			// 마지막 행으로 이동한 rs의 커서를 다시 원상복귀 시킴
			rs.beforeFirst();
			
		%>
		<%while(rs.next()){ // 레코드 수만큼.. next() 메서드가 true를 반환하는 동안만..%> 
						
		
		<tr>
			<td>0</td>
			<td><a href="/notice/detail.jsp?notice_id=<%= rs.getInt("notice_id") %>"><%=rs.getString("title")%></a></td>
			<td><%=rs.getString("writer")%></td>
			<td><%=rs.getString("regdate")%></td>
			<td><%=rs.getInt("hit")%></td>
		</tr>
		<%} %>
		<tr>
			<td colspan="5">dd</td>
			<button onClick="location.href='/notice/regist.jsp'">글등록</button>
		</tr>
	</table>

</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>
