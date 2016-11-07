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
                        	    Task Filter
                      	  </div>
                     	   <div class="panel-body">
								<div class="row">
									<div class="col-lg-12">
										<form role="form" method="post" onsubmit="return bpdParam()" action="BPMController">
										<div class="row">
												<div class="col-lg-6">
												<%
													
												LinkedHashMap<String, String> enviMap = (LinkedHashMap<String, String>) request.getAttribute("enviMap");
												%>
													<input type="hidden" name="data" id="data">
													<input type="hidden" value="filter" id="" name="action" >
														<div class="form-group">
																<label>Process Id</label> <input class="form-control bpd-param" name="proId" >
																<p class="help-block">Please enter the value of the Process Id.</p>
														</div>																					
														<div class="form-group">
															<label>Task Name</label> <input class="form-control bpd-param" name="taskName" >
															<p class="help-block">Please enter the value of the task Name. (ex. "Action on Feedback")</p>
														</div>
														<div class="form-group">
															<label>Task Status</label> <input class="form-control bpd-param" name="taskStatus" >
															<p class="help-block">Please enter the value of the task Status. (ex. "Received")</p>
														</div>
														<div class="form-group">
															<label>Owner</label> <input class="form-control bpd-param" name="owner" >
															<p class="help-block">Please enter the value of the owner. (ex. "HOMedicalTeam" or "1404")</p>
														</div>
													<div class="form-group">
			                                            <label>Environment</label>
			                                            <select class="form-control" name="envi" id="envi">
			                                                <%
																for (String envi :enviMap.keySet()) {
															%>													
															<option value="<%=envi%>"><%=enviMap.get(envi)%></option>
															<%
																}
															%>
			                                            	</select>
			                                        </div>
												</div>
												
												<div class="col-lg-6">
													<div class="form-group">
														<p class="help-block">&nbsp</p>
														<p class="help-block">&nbsp</p>
														<p class="help-block">&nbsp</p>
													</div>
													<div class="form-group">
														<label>Task Id</label> <input class="form-control bpd-param" name="taskId" >
														<p class="help-block">Please enter the value of the Task Id.</p>
													</div>
													<div class="form-group">
														<label>Process Status</label> <input class="form-control bpd-param" name="proStatus" >
														<p class="help-block">Please enter the value of the Process Status.</p>
													</div>
													<div class="form-group">
														<label>SnapShot</label> <input class="form-control bpd-param" name="snapShotName" >
														<p class="help-block">Please enter the value of the owner. (ex. "CMS_29Sep14")</p>
													</div>
													<div class="form-group">
			                                            <label>User</label>
			                                            <select class="form-control bpd-param" name="ownerType">
			                                            	<option value='group'>Group</option>
			                                            	<option value='user'>User</option>
			                                            </select>
			                                        </div>
												</div>
											</div>
											<div class="row">
												<div class="col-lg-6 col-centered"  >
													<div class="form-group">
														<button class="btn btn-primary btn-lg btn-block" type="submit" >Submit</button>
													</div>
													 <div class="form-group">
														<button class="btn btn-default btn-lg btn-block" type="reset">Reset</button>
													</div>
												</div>
											</div>
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
	<script src="${pageContext.request.contextPath}/js/simplify/bpm-view.js"></script>
	<script src="${pageContext.request.contextPath}/js/simplify/bpm-filter.js"></script>
	<%-- <%@ include file="/pages/common/import-js.jsp" %> --%>

</body>

</html>
    