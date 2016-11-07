<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simplify</title>
<%@ include file="/pages/common/import-css.jsp" %>	
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/dropzone/dropzone.css">
<style type="text/css">
.dropzone {
    border: 2px dashed #337ab7;
    border-radius: 5px;
    background: white;
}
</style></head>
<body>
	<div id="wrapper">
       
            <%@ include file="/pages/common/navigation-top.jsp" %>
            <!-- /.navbar-top-links -->
            <div class="navbar-default sidebar" role="navigation" id="sidebar">
                <%@ include file="/pages/common/navigation-sidebar.jsp" %>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
         <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Share File</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                
                <div class="row" >
                	<div class="col-lg-12">
                		<div  class="panel panel-default">
							<div class ="panel-body">
								<form action="/SimplifyDashboard/action.do/file-sharing/upload" method="post" class="dropzone" enctype="multipart/form-data" id="shareFileDropzone">
								<input type="hidden" value="public">
								  <div class="fallback">
								  	<div class="dz-default dz-message"><span>Drop files here to upload</span></div>
								  </div>								  
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<form role="form-group">							
							<button type="button" class="btn btn-default" onclick="window.history.back()" data-toggle="tooltip" data-placement="top" title="Back" data-original-title="Back"><i class="fa fa-chevron-left"></i></button>
							<button type="button" class="btn btn-default" id="expand" data-toggle="tooltip" data-placement="bottom" title="Expand Table" data-original-title="Expand Table"><i class="fa fa-expand"></i></button>
							<button type="button" class="btn btn-default" id="load" data-toggle="tooltip" data-placement="bottom" title="Load Data" data-original-title="Load Data"><i class="fa fa-refresh"></i></button>										
						</form>
					</div>    
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div  class="panel panel-default">
							<div class="panel-body">
								<table class="table table-striped table-bordered table-hover" id="share-file-table"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/morrisjs/morris.min.js"></script>
	<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/simplify/common.js"></script> --%>
	
	<script src="${pageContext.request.contextPath}/js/simplify/jquery.blockUI.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/json-formatter.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/popup.js"></script>    
    <script src="${pageContext.request.contextPath}/plugins/DataTables/js/jquery.dataTables.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/DataTables/extra/dataTables.buttons.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/simplify/common-lib.js"></script>
	<script src="${pageContext.request.contextPath}/js/simplify/file-sharing.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/dropzone/dropzone.js"></script>
	</body>
</html>