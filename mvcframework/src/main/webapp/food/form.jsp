<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<script>
	function request(){
		document.querySelector("form").action="/food.do";	
		document.querySelector("form").method="post";	
		document.querySelector("form").submit();	
	} 
	
	addEventListener("load", function(){
		document.querySelector("button").addEventListener("click", ()=>{
			request();
		});
	});

</script>

<body>
	
	<form>
		<select name="food">
			<option value="부대찌게">부대찌게</option>
			<option value="돈까스">돈까스</option>
			<option value="에그드랍">에그드랍</option>
			<option value="초밥">초밥</option>
		</select>	
		<button type="button">피드백 요청</button>
	</form>
	
</body>
</html>