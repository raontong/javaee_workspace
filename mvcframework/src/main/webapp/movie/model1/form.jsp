<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.ch.mvcframework.movie.model.MovieManager"%>
<%! MovieManager manager=new MovieManager();%>

<%
	/*  
	하나의 페이지로 모든 기능과 디자인을 합쳐놓은 프로그램의 장 단점
	장점) 개발시간이 단축
	단점) 디자인과 로직이 뒤섞여 있으므로, 디자인을 버릴 경우 로직도 함께 버려야 함
	*/

	// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메시지 만들기 
    request.setCharacterEncoding("UTF-8");
    String movie=request.getParameter("movie");
    out.print(movie);

    // 각 영화에 대한 판단을 해주는 코드가 별도의 모델 객체로 분리되었다!! 
    // 분리 시킨이유? 웹뿐만 아니라, 다른 플랫폼에서도 재사용하기 위해, 재사용=시간=돈
    // 재사용 대상(모델!!)
   	String msg=manager.getAdvice(movie);
    
    

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<script>
	function request(){
		document.querySelector("form").action="/movie/script/form.jsp";	
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
	
	<!-- 
			이 프로그램에 대해 유지보수성을 고려할 필요가 없을 정도로 간단한 기능으로 판단된다면
			굳이 유지 보수성을 염두해 둔 java 클래스까지 도입할 필요가 없다
			따라서 스크립트만으로 해결해보자 - 이러한 개발 방식을 가리켜 이름조차 없는 막개발 방식 이라 함.. 스크립트 위주의 개발방식
			아주 간단한 분야에 유횽함
	  -->
	<h3>
		선택할 결과 <br>
		<span style="color:red">
			<%=msg%>
		</span>
	</h3>
	
</body>
</html>