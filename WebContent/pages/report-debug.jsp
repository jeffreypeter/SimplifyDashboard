<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>

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
                        <h1 class="page-header">Reports</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                	 <div class="col-lg-12">
                	 	<div class="panel panel-default">
                	 		<div class="panel-heading">
                        	    Debug
                      	  </div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<%
											LinkedHashMap<String, String> enviMap = (LinkedHashMap<String, String>) request.getAttribute("enviMap");
								    		LinkedHashMap<String, String> reportMap = (LinkedHashMap<String, String>) request.getAttribute("reportMap");
										%>
										
										<form role="form" method="post" action="DataController">
											<div class="form-group">
												<label>Data</label> <input class="form-control" type="text" name="data" placeholder="Enter value" autofocus>
												<p class="help-block">Please enter the value of the Data. (ex. 12345678)</p>
											</div>
											<div class="form-group">
											<label>Report </label>
												<select name="report" id="report" class="form-control" required>
													<option></option>
													<%
														for (String report :reportMap.keySet()) {
													%>													
													<option value="<%=report%>"><%=reportMap.get(report)%></option>
													<%
														}
													%>
												</select>
												<p class="help-block">Please choose a Report and provide data as mentioned.</p>
											</div>
											<div class="form-group">
											<label>Envi </label>
												<select class="form-control" name="envi">
													<%
														for (String envi :enviMap.keySet()) {
													%>													
													<option value="<%=envi%>"><%=enviMap.get(envi)%></option>
													<%
														}
													%>
												</select>												
											</div>
											<div class="form-group">
												<label>Action </label> <label class="radio-inline">
													<input type="radio" value="view" id="" name="action" checked>View
												</label> <label class="radio-inline"> <input
													type="radio" value="print" id="" name="action">Print
												</label>
											</div>
											<button class="btn btn-default" type="submit">Submit</button>
											<button class="btn btn-default" type="reset">Reset</button>
										</form>
									</div>
								</div>
							</div>
						</div>
                	 </div>
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
	<script src="${pageContext.request.contextPath}/bower_components/metisMenu	/dist/metisMenu.min.js"></script>
	<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/simplify/common.js"></script> --%>
	<script  type="text/javascript">
	 $(document).ready(
			 $("#report").select2({
				 placeholder: "Select a Report",
				 allowClear: true
			 }) 	 
	 );
	</script>
	

</body>

</html>
    