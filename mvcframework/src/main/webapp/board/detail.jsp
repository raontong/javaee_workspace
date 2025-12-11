<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ch.mvcframework.dto.Board" %>
<%
// DatailController 가 reqest에 저장해준 Board를 꺼내자!!
// 바로 detail 로
Board board=(Board)request.getAttribute("board");
%>

<!DOCTYPE html>
<meta charset="UTF-8">
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
 <!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
  
  <script>
  	$(()=>{
  		$('#summernote').summernote();
  		$('#summernote').summernote("code", "<%=board.getContent()%>");
  		$("#bt_edit").click(()=> {
  			if(confirm("수정하시겠어요?")){
  				$("#form1").attr({
  					action:"/board/edit.do",
  					method:"post"
  				});
				// update board set title=?,writer=?, content=? where=board+id  				
  				$("#form1").submit();
  			};
  		});
  		
  		$("#bt_del").click(()=> {
  			if(confirm("삭제하시겠어요?")){
  				// delete from board where board_id=3;
  				location.href="/board/delete.do?board_id=<%=board.getBoard_id()%>";
  			}
  		});
  		
  		$("#bt_list").click(()=> {
  			location.href="/board/list.do";
  		});
  	});
  			
  </script>
</head>
<body>
<div class="container">
  <h2>Stacked form</h2>
  <form id="form1" action="/action_page.php">
  	<input type="hidden" name="board_id" value="<%=board.getBoard_id() %>">
    <div class="form-group">
      <label for="title">제목:</label>
      <input type="text" class="form-control" id="title" value="<%=board.getTitle() %>" name="title">
    </div>
    <div class="form-group">
      <label for="writer">작성자:</label>
      <input type=""text"" class="form-control" id="writer" value="<%=board.getWriter() %>" name="writer">
    </div>
        <div class="form-group">
      <label for="content">내용:</label>
      <textarea type="text" class="form-control" id="summernote" name="content"></textarea>
    </div>
    <div class="form-group form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox" name="remember"> Remember me
      </label>
    </div>
    <button type="button" id="bt_edit" class="btn btn-primary">수정</button>
    <button type="button" id="bt_del" class="btn btn-primary">삭제</button>
    <button type="button" id="bt_list" class="btn btn-primary">목록</button>
  </form>
</div>
</body>
</html>
