<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Simplify WorkFlow</title>
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
                        <h1 class="page-header">WorkFlow</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
                	 <div class="col-lg-12">
                	 	<div class="panel panel-default">
                	 		<div class="panel-heading">
                        	    Parameter Search
                      	  </div>
                     	   <div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
									<%
										LinkedHashMap<String, String> enviMap = (LinkedHashMap<String, String>) request.getAttribute("enviMap");
						    			LinkedHashMap<String, String> lookUpMap = (LinkedHashMap<String, String>) request.getAttribute("lookUpMap");
									%>
										<form role="form" method="post" action="BPMController">
											<input type="hidden" value="search" id="" name="action" >
												<label>Parameter</label><div class="form-group">
													<select class="form-control" name="paramName">
	                                               <%
														for (String lookUp :lookUpMap.keySet()) {
													%>													
													<option value="<%=lookUp%>"><%=lookUpMap.get(lookUp)%></option>
													<%
														}
													%>
	                                            	</select>
													<p class="help-block">Please choose a Parameter</p>
												</div>										
												<div class="form-group">
													<label>Value</label> <input class="form-control" name="paramValue">
													<p class="help-block">Please enter the value of the Parameter. (ex. 12345678)</p>
												</div>
											<div class="form-group">
	                                            <label>Environment</label>
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
	                                        <button class="btn btn-default" type="submit">Submit</button> <button class="btn btn-default" type="reset">Reset</button>
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
	<script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- Metis Menu Plugin JavaScript -->
	<script src="${pageContext.request.contextPath}/bower_components/metisMenu	/dist/metisMenu.min.js"></script>
	<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/simplify/common.js"></script> --%>
	<%-- <%@ include file="/pages/common/import-js.jsp" %> --%>

</body>

</html>
    