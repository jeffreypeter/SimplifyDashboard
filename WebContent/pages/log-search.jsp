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
                        <h1 class="page-header">Log Monitor</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                
                <div class="row">
                	 <div class="col-lg-12">
                	 	<div class="panel panel-default">
                	 		<div class="panel-heading">
                        	    Log Search
                      	  </div>
                      	  
							<div class="panel-body">
								 
								<div class="row">
									<div class="col-lg-6">
										<%
											ArrayList<String> soaLogFilesLst = (ArrayList<String>) request.getAttribute("soaLogFilesLst");
											ArrayList<String> eviList = (ArrayList<String>) request.getAttribute("eviList");
											ArrayList<String> teamList = (ArrayList<String>) request.getAttribute("teamList");
										%>
										<form role="form" method="post" action="DataController">
											<div class="form-group">
											<label>Environment</label><br>
											<input type="hidden" name="team" value="">
											<select id="envi" name="envi" class="form-control">
												<%
													for (String envi : eviList) {
												%>
												<option value="<%=envi.toLowerCase()%>"><%=envi%></option>
												<%
													}
												%>
											</select>
											 
											</div>
											<div class="form-group">
											<label>Log</label><br>
													<select name="log" id="log" class="form-control" required>
														<option></option>
														<%for(String logFile:soaLogFilesLst) { %>
														<option value="<%=logFile%>"><%=logFile%></option>
														<%} %>															
													</select>
													<p class="help-block">Please choose a Module.</p>														
											</div>
											<button class="btn btn-outline btn-info" type="submit">Search</button>
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
	<script src="${pageContext.request.contextPath}/js/simplify/controls.js"></script>
	<%-- <%@ include file="/pages/common/import-js.jsp" %> --%>	
	<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/simplify/common.js"></script> --%>
	<script  type="text/javascript">
	 $(document).ready(
			 $("#log").select2({
				 placeholder: "Select Module",
				 allowClear: true
			 }) 	 
	 );
	</script>
</body>

</html>
    