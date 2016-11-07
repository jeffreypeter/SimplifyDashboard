<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Jeffrey">
    <title>Simplify</title>
	<%@ include file="/pages/common/import-css.jsp" %>	
	<style type="text/css">
	.full-screen{
	    z-index: 9999; 
	    width: 100%; 
	    height: 100%; 
	    position: fixed; 
	    top: 0; 
	    left: 0; 
 	}

#myDiv{background:#cc0000; width:500px; height:400px;}
	</style>
</head>

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

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Handle Error</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row" >
                	<div class="col-lg-12">
                		<div  class="panel panel-default">
							<div class ="panel-body">
								<div class="row">
									<div class="col-lg-12">
										<h4>Tools</h4>
										<form role="form-group">
											<input type="hidden" name="envi" id="envi" value="<%= request.getAttribute("envi")%>" >
											<input type="hidden" name="operation" id="operation" value="<%= request.getAttribute("operation")%>">
											<input type="hidden" name="action" id="action" value="<%= request.getAttribute("action")%>">
											<button type="button" class="btn btn-default" onclick="window.history.back()" data-toggle="tooltip" data-placement="top" title="Back" data-original-title="Back"><i class="fa fa-chevron-left"></i></button>
											<button type="button" class="btn btn-default" id="expand" data-toggle="tooltip" data-placement="bottom" title="Expand Table" data-original-title="Expand Table"><i class="fa fa-expand"></i></button>
											<button type="button" class="btn btn-default" id="load-data" data-toggle="tooltip" data-placement="bottom" title="Load Data" data-original-title="Load Data"><i class="fa fa-refresh"></i></button>											
											<button type="button" class="btn btn-default" id="export" disabled="disabled" data-toggle="tooltip" data-placement="bottom" title="Export Report" data-original-title="Export Report"><i class="fa fa-cloud-download"></i></button>
																						
										</form>
									</div>    
								</div>
								</div>
								<div class="row">
									<div class="col-lg-12" >
										<div  class="panel" style="border:none">
											<div class ="panel-body">
												<div class="dataTable_wrapper" style="overflow-x: scroll;" id ="dataTable_wrapper" >
													<table class="table table-striped table-bordered table-hover" id="function-handle-error-table" style="font-size: 10px;"></table>
												</div>
											</div>											
										</div>
									</div>
								</div>
                		</div>
                	</div>
                </div>
                <div class="row">
                	<div class="col-lg-12">
                		
                	</div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div> 
    
    <script src="${pageContext.request.contextPath}/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	<script src="${pageContext.request.contextPath}/bower_components/morrisjs/morris.min.js"></script>
	<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
	<script src="${pageContext.request.contextPath}/js/simplify/common.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/simplify/jquery.blockUI.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/json-formatter.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/popup.js"></script>    
    <script src="${pageContext.request.contextPath}/plugins/DataTables/js/jquery.dataTables.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/DataTables/extra/dataTables.buttons.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/simplify/operation.js"></script>
	<script>
	$(document).ready(function() {		
		console.log("Inline Script");
		$(document).ajaxStop(function() {
			console.log("refresh:: "+refresh);
			var content = ($("#function-handle-error-table").html()).trim().length;
			if( content !=0 && refresh == false ) {
				console.log("Table Loaded:: "+content);
				$("#function-handle-error-table").dataTable( {		
					"bDestroy": true
				});
			}
		});
	});
	</script>
	
</body>

</html>
    