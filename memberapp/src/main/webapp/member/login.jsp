<%@ page  contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for all buttons */
button {
  background-color: #04AA6D;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}

button:hover {
  opacity:1;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and signup buttons and add an equal width */
.cancelbtn, .signupbtn {
  float: left;
  width: 50%;
}

/* Add padding to container elements */
.container {
  padding: 16px;
}

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

/* Change styles for cancel button and signup button on extra small screens */
@media screen and (max-width: 300px) {
  .cancelbtn, .signupbtn {
     width: 100%;
  }
}
</style>
<!-- 이자리는 외부로 저장해 놓고, 실행타임에 동ㅈ거으로 코드가 들어오도록 처리하는 include -->
<%@ include file="../inc/header_link.jsp" %>
<script>
	$(function(){
		// .clearfix라는 클래스 안에 들어있는 두개의 버튼을 중 두번쨰 버튼에 click이벤트 연결하기
		$($(".clearfix button")[1]).click(function(){
			//  유저가 작성한 폼을 전송하자
			$("#form1").attr({
				action:"/member/login", // 서블릿매핑
				method:"post"
			});
			$("#form1").submit();
				
		}); 
	});
</script>


<body>

<form style="border:1px solid #ccc" id="form1">
  <div class="container">
    <h1>Login</h1>
    <hr>
    <label for="ID"><b>ID</b></label>
    <input type="text" placeholder="Your ID" name="id" >

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="pwd"  required>

    <label for="psw-repeat"><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="pwd2" required>
    
    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
    </label>

    <div class="clearfix">
      <button type="button" class="cancelbtn">Sign Up</button> 
      <button type="button" class="signupbtn">Login</button>
    </div>
  </div>
</form>

</body>
</html>







