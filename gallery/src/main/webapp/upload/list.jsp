<%@page import="java.security.interfaces.RSAKey"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>

<%
// 출력 스트림에 클라이언트에게 보여줄 태그를 넣어두자
// 서블릿은 서버에서 실행되는 .javaEE 기반의 클래스로서, 서블릿없이는 javaEE 개발 자체가 불가능함
// 하지만, 디자인 페이지 작성시 너무 비효율적이다.
// 따라서 servlet과 동일한 목적의 기술인 php, asp, asp.net 등에 비해 경쟁력이 떨어진다.
// 해결책) servlet을 보완한 서비측 스크립트 기술로 jsp지원
%>
 
<%!
 	// 선언 부는 서블릿으로 변환될때 자동으로 멤버영역으로 자리 잡는다.
 	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pass="1234";
	
    %>
<%
	// page지식 영역에서 contentType("text"/html;charset=utf-8")
	// 오라클 연동하기
	
	// 아래의 코드는 원래 순수 java 클래스에서 작성할 경우, 예외처리가 강제 되지만, 
	// 현재 우리의 jsp 영역은 실행 직전 tocat에 의해 서블릿으로 변환되어지며 특히 스크립틀릿 영역은 
	// service() 매서드로 코드가 작성되고, 이때 tmcat이 예외처리까지 해버렸으므로, 
	// jsp에서는 예외처리할 것을 강요당하지 않음.
	
	// 1단계_ 드라이버 로드
	Class.forName("oracle.jdbc.driver.OracleDriver");

	// 2단계_ 접속
	
	con = DriverManager.getConnection(url,user,pass); 
	
	
	// 3단계_ 쿼리실행
	String sql="select * from gallery";
	pstmt= con.prepareStatement(sql); // 쿼리 수행 객체 생성
	
	// 쿼리문이 select인경우, 그 결과표를 받는 객체가 ResultSet
	rs=pstmt.executeQuery(); // 쿼리 수행 후 그 결과를 rs로 받자!!
	


%>
<%!
// jsp는 사실 상 서블릿이다.
// 결론: jsp의 개발목적 - 서블릿이 디자인을 표현하는데 너무나 비효율적, 개발자 대신 디자인 컨텐츠를 
// 시스템인 웹컨테이너가 대시신 작성해 주기 위한 스크립트 언어

//선언부라 한다..
int x=7; // 맴버변수

public int getX(){ // 맴버 메서드
		return x;
} %>
	
<%
	// 이 영역을 스크립틀릿이라 하며, 추후 고양이에 의해 이 jsp가 서블릿으로 변호나되어질떄
	// 이 영역에 작성한 코드는 service() 안에 작성한 것과 같아진다
  	// 선언한 적도 없는 레퍼런스를 변수를 사용할 수 있는 이유?
	// jsp는 총 9가지 정도의 내장객체를 지원함(Built-in Object)
	// 문자 기반의 출력스트림 객체를 미리 변수명까지 지정해놓은 out라한다
   	out.print(getX());
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
</style>
</head>
<body>
<pre>
1) Jsp란 ? Java Server Page(자바 기반의 서버에서 실행되는 페이지)
	오직 javaEE 기반의 서버에서만 해석 및 실행됨
	장점 - 서블릿과 달리 HTML을 혼영하여 사용가능(서블릿 디자인 표현의 취약점 보완하기 위한 기술)

2) JSP의 코드 작성 영역
 - jsp는 다음의 3가지 영역에 코드를 작성할 수 있다.
 (1) 지시 영역_ @ 붙은 영역을 의미
 		현재 jsp 페이지의 인코딩, 파일 유형, 다른 클래스의 import 등을 위한 영역 
 (2) 선언부_ ! 가 붙은 영역
 				맴버 영역(멤버 변수나 매서드를 선언 할수 있는 영역) 
 (3) 실행영역_ 스크립틀릿
 				실직적으로	 로직을 작성하는 영역
 				
</pre>

<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>
  
 <% // re 객체의 next() 메서드를 호출할 떄마다, 커서가 밑으로 한칸씩 전진하는데, 이때
 // 커서가 위치한 행의 레코드가 존재할 경우는 true를 반환하지만, 존재하지 않으면 false를 반환
 // 따라서 모든 레코ㄷ만큼 반복문을 수행하려면 next()가 참인 동안 반복하면 된다.
 %>
<% while(rs.next()){ %>
  <tr>
    <td><%out.print(rs.getInt("gallery_id")); %></td>
    <td><%out.print(rs.getString("title")); %></td>
    <td><%out.print(rs.getString("filename")); %></td>
  </tr>
<% } %>
  
  </tr>
</table>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>

