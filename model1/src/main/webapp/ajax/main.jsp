<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@page import="java.util.List"%>

<%!
	Member2DAO dao= new Member2DAO();

%>
<%
	List<Member2> memberList=dao.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .container{
        width: 650px;
        height: 500px;
        background-color: aliceblue;
        margin: auto;
    }
    .aside{
        width: 150px;
        height: 100%;
        background-color: rgb(40, 77, 109);
        float: left;
    }
    .aside input{
        width: 90%;
    }
    .aside button{
        width: 50%;
    }
    .content{
        width: 500px;
        height: 100%;
        background-color: aliceblue;
        float: left;
    }
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script>
	// 지금까지는 동기방식으로 서버에 요청을 시도 했고  그결과로 HTML을 가져ㅏ와서 브라우저 화면에 출력함으로써
	// 유저가 보기엔 새로고침 현상이 발생하게 됨.. 따라서 현재 페이지는 그대로 유지하고, 백그라운드에서
	// 크롬과 같은 웹브라우저가 대신 서버와의 통신을 담당하고, 그 시간동안은 자바스크립트는 원래 하고자 했던 로직을 그대로 수행하게 됨
	// 추후, 그 서버로 부터 응답이 오면, 크롬 부라우저는 자바스크립트에게 보고를 하게 되면, 이때 서버로 부터 가져온 HTML이 아닌 순수한 데이터를 
	// 자바스크립트에게 전달한다.. 그러면 이 데이터를 자바스크립트는 순수 데이터를 이용하면 화면에 동적으로 출력한다.
	// 새로고침 NO
	function sendAsync(){
		// 비동기방식의 핵심이 되는 자바스크립트 객체가 바로 XMLHttpResquest 이다.
		// 주의 이 객체가 서버로 요청을 더나는것이 아니라, 크롬 브라우저가 요청을 시도하러 감.
		let xhttp=new XMLHttpRequest(); 
		
		// 크롬등의 브라우저가 서버로 부터 응답을 받을때 발생하는 이벤트를 처리하는 속성!!
		// 브라우저가 서버로부터 응답을 받으면 onload에 지정한 콜백함수를 자동으로 호출하게 됨(이때 호출 주체는 js)
		xhttp.onload=function(){
			// 서버가 보내온 데이터를 담고 있는 속성인 responseText를 이용해보자
			
	 		// 서버가 보내온 데이터는 무조건 문자열이기 때문에 아래의 형식이 마치 자바스크립트의 개체 리터럴로 착각될 수 없다.
	 		// 론론은 객체 아니다!!
	 		// 객체가 아니므로 속성이라는 것도 존재할 수 없다. 즉 객체에 . 점찍고 접근불가!!
	 		// 
	 		
	 		//해결책? 어던 물자열이 json 표기법을 준수하여 작성되어 있따면, 자바스크립트는 내장 객체인 json내장객체를 이용하여 
			// 			문자열을 해석 실재 자바스크립트 객체 리터럴로 전환해줄수 있다.	 					
			JSON.parse();
						
			// 서버로 부터 전송되어온 문자열을 대상으로 원하는 값 추출하기
			let obj= con.selectAll(xhttp.responseText); // 문자열을 해석하여 json구분 형식에 맞을 경우만, 객체 리터럴로 전환해준다!!
			// 정말로 obj가 자바스크립트의 인스턴스라면, 객체 속성을 접근 할수 있어야한다.
			// 따라서 검증해보자
			console.log("email은", obj.email);
						
			// console.log("서버로 부터 받은 응답받은 정보는"+ xhttp.responseText);
		}
		
		// 어떤 서버의 주소에 요청을 시도하고, 어떤 HTTP 메서드로 요청을 시도할지 결정하는 메서드
		xhttp.open("POST", "/ajax/async_regist.jsp"); 
		
		// HTTP 메서드가 post 인 경우 헤더값을 다음과 같이 설정해야 한다.(평소엔 웹브라우저가 대신 해줌..)
		// 해더에 대한 설정은 반드시 open 이후에 작성해야한다.
		// 서버야 이 요청은 post야! 
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		
		//브라우저에게 요청
		xhttp.send("id="+$("input[name='id']").val()+"&name="+$("input[name='name']").val()+"&email="+$("input[name='email']").val()); // 요청시작
		
	}

	/*
    문서가 로드가 되면, 두개의 버튼에 대해 이벤트 연결
    화살표 함수 - 기존 함수정의 기법을 줄여서 표현..
    */ 
    $(()=>{
        // 동기 버튼에 클릭 이벤트 연결
        $($("form button")[0]).click(()=>{
            // alert("동기방식의 요청시도");
            $("form").attr({
                // regist.jsp 로 간다.
                action:"/ajax/regist.jsp",
                method:"post"
                
            });
            $("form").submit();
        });
        // 비동기방식 요청 시도(버튼에 클릭 이벤트 연결)
        $($("form button")[1]).click(()=>{
            // alert("비동기방식의 요청시도");
        	sendAsync();
        });
    });

</script>
</head>
<body>
	<div class="container">
        <div class="aside">
            <form>
                <input type="text" placeholder="Your ID..." name="id">
                <input type="text" placeholder="Your name..." name="name">
                <input type="text" placeholder="Your email..." name="email">
                <button type="button">sync</button>
                <button type="button">async</button>
            </form>
        </div>
        <div class="content">
        	<table width="100%" border="1px">
        		<thead>
        			<th>ID</th>
        			<th>NAME</th>
        			<th>EMAIL</th>
        		</thead>
        		<tbody>
        			<%for(int i=0; i<memberList.size(); i++){ %>
        			<%
        				Member2 dto=memberList.get(i);
        			%>
        			<tr>
        				<td><%=dto.getId() %></td>
        				<td><%=dto.getName() %></td>
        				<td><%=dto.getEmail() %></td>
        			</tr>
        			<%} %>
        		</tbody>
        	</table>
        </div>
    </div>
</body>
</html>