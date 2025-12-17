<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sh.shop.dto.TopCategory" %>

<%
	List<TopCategory> topList=(List)request.getAttribute("topList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>AdminLTE 3 | Dashboard</title>

	<%@ include file="../inc/head_link.jsp" %>  
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Preloader -->
	<%@ include file="../inc/preloader.jsp" %>

  <!-- Navbar -->
	<%@ include file="../inc/navbar.jsp" %>	
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
	<%@ include file="../inc/sidebar.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">상품등록</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">상품관리</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <!-- 메인 컨텐츠 시작 -->
        <div class="row">
        	<div class="col-md-12">
        	
	            <div class="card card-info">
	              <div class="card-header">
	                <h3 class="card-title">Porduct Registration</h3>
	              </div>
	              <!-- /.card-header -->
	              <!-- form start -->
	              <form>
	                <div class="card-body">
	                
						<div class="form-group row">
						  <div class="col-md-6">
						    <select class="form-control" name="topCategory">
						    </select>
						  </div>
						</div>

	                	
						<div class="form-group row">
						  <div class="col-md-6">
						    <select class="form-control" name="topCategory">
						    </select>
						  </div>
						</div>

	                	
	                	
	                </div>
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="product_name" placeholder="상풍명">
	                  </div>
	                  	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="brand" placeholder="브랜드">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control" name="price" placeholder="가격(숫자로 입력">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="number" class="form-control" name="discount" placeholder="할인가">
	                  </div>
	                  
	                  <div class="form-group">
	                    <input type="text" class="form-control" name="introduce" placeholder="간단소개">
	                  </div>
	                  
	                  <div class="form-group">
	                    <textarea id="summernote" class="form-control" name="detail" placeholder="간단소개"></textarea>
	                  </div>
	                  

	                  <div class="form-group">
	                    <label for="exampleInputFile">File input</label>
	                    <div class="input-group">
	                      <div class="custom-file">
	                        <input type="file" class="custom-file-input" id="exampleInputFile">
	                        <label class="custom-file-label" for="exampleInputFile">Choose file</label>
	                      </div>
	                      <div class="input-group-append">
	                        <span class="input-group-text">Upload</span>
	                      </div>
	                    </div>
	                  </div>
	                  
	                  
	                </div>
	                <!-- /.card-body -->
	
	                <div class="card-footer">
	                  <button type="submit" class="btn btn-info">Submit</button>
	                </div>
	              </form>
	            </div>
        	
        	</div>
        </div>
        <!-- 메인 컨텐츠 끝 -->
      </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
	<%@ include file="../inc/footer.jsp" %>
	
  <!-- Control Sidebar -->
	<%@ include file="../inc/control_sidebar.jsp" %>  
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
	<%@ include file="../inc/footer_link.jsp" %>
	
	<script>
  
		$(()=>{
			$("#summernote").summernote()
		});
	
	</script>
	
</body>
</html>