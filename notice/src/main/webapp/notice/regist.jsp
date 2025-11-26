<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// 위의 페이지 지시 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩 되어 질때
	// response.seContentType("text/htm");
	// charset=UTF-8 response.setCharacterEncoding("utf-8");
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
	background-color: #04AA6D;
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

</script>
</head>
<body>

	<div class="container">
		<form id="form1">
			<input type="text" name="title" placeholder="제목 입력..."> 
			<input type="text" name="writer" placeholder="작성자 입력.."> 
			<textarea id="subject" name="content" placeholder="내용을 입력.." style="height: 200px"></textarea>
			<input type="button" value="Submit" onClick="regist()">
		</form>
	</div>

</body>
</html>
