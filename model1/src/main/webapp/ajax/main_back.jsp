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
    /*  문서가 로드가 되면, 두개의 버튼에 대해 이벤트 연결
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
        // q비동기 버튼에 클릭 이벤트 연결
        $($("form button")[1]).click(()=>{
            alert("비동기방식의 요청시도");
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
                <button>sync</button>
                <button>async</button>
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
        			<tr>
        				<td>scott</td>
        				<td>스캇</td>
        				<td>naver</td>
        			</tr>
        		</tbody>
        	</table>
        </div>
    </div>
</body>
</html>