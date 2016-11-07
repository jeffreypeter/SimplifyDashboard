<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Simplify</title>
	<%@ include file="/pages/common/import-css.jsp" %>

</head>

<body>

    <div id="wrapper">
            <%@ include file="/pages/common/navigation-top.jsp" %>
            <!-- /.navbar-top-links -->
            <div class="navbar-default sidebar" role="navigation">
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
						<h1 class="page-header">WAS Monitoring</h1>
						<div class="panel panel-default">
							<div class="panel-heading">Queue Depth</div>
							<div class="panel-body">
							<%
							 ArrayList<String> eviList = (ArrayList<String>) request.getAttribute("eviList");
							%>
								<div class="row">
									<!-- <div class="col-lg-6">
										<div class="form-group">
											<div class="form-group">
												<label>Queue</label><br> <select id="queue"
													name="queue" class="form-control">
													<option value="SCA.SYSTEM.hslpabsoa2Cell01.Bus">SCA.SYSTEM.hslpabsoa2Cell01.Bus</option>
												</select>
											</div>

										</div>
									</div> -->
									<div class="col-lg-4">
									<div class="form-group">
										<label>Environment</label><br> 
										<select id="envi" name="envi" class="form-control">
											<%for (String envi:eviList) {%>
											<option value=<%=envi.toLowerCase() %>><%=envi %></option>
											<%}%>
										</select>
										</div>
									</div>
									<div class="col-lg-2">
										<label>&nbsp</label><br>
										<button class="btn btn-default" onclick="getQdepth()" id="refresh"><i class="fa fa-refresh fa-1x fa-fw margin-bottom"></i></button>
									</div>
																		
								</div>
								<div class="col-lg-12">
								<div class="dataTable_wrapper">
									<div class="table-responsive" style="min-height: 150px; overflow-x: hidden">
										<table class="table table-striped table-bordered table-hover" id="qdepthtable">
											<thead>
											<tr>
												<th>Name</th>
												<th>Depth</th>
											</tr>
											
											</thead>
											<tbody>
											
											</tbody>
										</table>
									</div>
								</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>   
	<script src="${pageContext.request.contextPath}/bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/jquery.blockUI.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/wsadmin.js"></script>	
</body>
 
</html>
    