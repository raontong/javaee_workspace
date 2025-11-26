<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Driver"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	String url="jdbc:mysql://localhost:3306/java";
	String user="servlet"; 
	String password="1234";

	Connection con; 						// DB와의 연결(접속) 정보 객체_ DB 서버와 <★전화선을 연결>하는 역할
	PreparedStatement pstmt; 		// SQL 쿼리를 실행 객체_ 전화선을 통해 실제로 <★메시지를 보내는 사람>
	ResultSet rs; 							// SELECT 문 결과인 <★표>를 가진 객체
%>

<%
	// 위의 페이지 지시 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩 되어 질때
	// response.seContentType("text/htm");
	// charset=UTF-8 response.setCharacterEncoding("utf-8");
	
	//  select * from notice where notice_id=2;  쿼리를 수행하여 레코드를 화면에 보여주기
	// 웹브라우저의 목록을 클릭시 전돨되어온 pk를 notice_id 를 수행하여 레코드를 화면에 보여주기
	// HTTP 통신에서 주고 받는 파라미터는 모두 문자열로 인식!! 예 1=> "1"
	String notice_id= request.getParameter("notice_id"); //request란? 서블릿의 service(요청객체, 응답객체) 중 HttpServletRequest 인터페이스를
			// 가리키는 내장 객체, 그러다 보니 개발자가 변수명을 정한 것이 아니라 이미 jsp문법에서 정해진 이름
			
	out.print("select * from notice where notice_id="+notice_id);
	
	// 드라이버 로드
	Class.forName("com.mysql.cj.jdbc.Driver");
		
	// 접속
	con = DriverManager.getConnection(url, user, password);
	
	// 쿼리문 날리기
	// 오라클  "select * from member order by member_id asc";
	String sql="select * from notice where notice_id="+notice_id;
	pstmt = con.prepareStatement(sql);
	rs=pstmt.executeQuery();
	
	rs.next();
	
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=button] {
	background-color: red;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<script>
	// 사용자가 입력한 폼의 내용을 서버로 전송하자
	// JavaScript  언어는 DB 에 직접 적으로 통신가능??
	// JS는 클라이언트 측(Front영역)이기 때문에 원본 소스가 그냥 노출되어 있다.
	function regist(){
		// js는 DB와의 통신 자체가 막혀 있기 때문에 , 직접 DB에 쿼리문을 날리는 것이 아니라, 
		// Tomcat 과 같은 웹컨테이너(서버)에게 부탁을 한다! 즉 요청한다.
		
		// 
		let form1=document.getElementById("form1");
		form1.action="/notice/regist"; // 서블릿 주소
		
		/* Get / Post ,ㅡ HTTP 프로토콜은 머리와 몸으로 데이터를 구성하여 통신을 하는 규약
		이때 서버로 데이터가 양이 많거나, 노출되지 않으려면 편지지에 해당하는 Post방식을 쓴다
		반면, 서로 데이터의 양이 적거나, 노출되어도 상관없을 경우 편지 봉투에 해당하는 Get방식을 쓴다.
		- header (편지봉투) get 노출, 내용양이 적다 
        - body (편지지) post 
        - ex_ naver 뉴스 .. 주소 ? 다음은 중요하지 않아서 get방식사용
		*/
		form1.method="post"; 
		form1.submit(); // 전송이 발생
	}
	function edit(){
		if(confirm("수정하실래요?")){
			// 작성된 폼 양식을 서버로 전송!!
			let form1=document.getElementById("form1");
			form1.action="/notice/edit"; // 서버의 url << 서블릿
			form1.method="post";
			form1.submit();''
		}
	}
	
	function del(){
	    // confirm("삭제하시겠어요?");
		// console.log("유저의 대답은?", res);
		if(confirm("삭제하시겠어요?")){
			location.href='/notice/delete?notice_id=<%=rs.getInt("notice_id")%>';
		}
	}
	

</script>
</head>
<body>

	<div class="container">
		<form id="form1">
			<!-- 파라미터중 굳이 일반 유저에게 노출될 필요가 없는 경우, 존재는 하나 눈에 보이지 않게 하는 목적으로 사용 
			ex) 신용카드 결제 시스템 등 개발 시 많이 사용..-->
			<input type="hidden" name="notice_id" value="<%= rs.getInt("notice_id") %>" style="background:yellow">
			
			<input type="text" name="title" value="<%= rs.getString("title") %>">
			<input type="text" name="writer" value="<%= rs.getString("writer") %>"> 
			<textarea name="content"  style="height: 200px"><%=rs.getString("content")%></textarea>
			<input type="button" value="수정" onClick="edit()">
			<input type="button" value="삭제"  onClick="del()">
						<!-- js에서 링크를 표현한 내장객체를 location  -->
			<input type="button" value="목록" onClick="location.href='/notice/list.jsp'">
		</form>
	</div>

</body>
</html>
<% // DB관련 객체 닫기
	rs.close();
	pstmt.close();
	con.close();
%>
