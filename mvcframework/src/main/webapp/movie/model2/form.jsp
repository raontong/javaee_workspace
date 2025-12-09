<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<script>
	function request(){
		document.querySelector("form").action="/movie";	
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
		<select name="movie">
			<option value="귀멸의칼날">귀멸의칼날</option>
			<option value="미션임파서블">미션임파서블</option>
			<option value="에어리언">에어리언</option>
			<option value="주토피아">주토피아</option>
		</select>	
		<button type="button">피드백 요청</button>
	</form>
	
</body>
</html>