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
                        	    Bulk Reports
                      	  </div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<%
											ArrayList<String> reportLst = (ArrayList<String>) request.getAttribute("reportLst");
										%>
										<form role="form" method="post" action="DataController">
											<input type="hidden" name="envi" value="db"> <label>Report</label>
											<div class="table-responsive">
												<table class="table table-hover">
													<thead>
														<th><input type="checkbox" class="selectAllCheckbox"></th>
														<th>Report Name</th>
														<th>Action</th>
													</thead>
													<tbody>
														<%
														for (String option : reportLst) {
														%>
														<tr class="info-table-row"> <td><input type="checkbox" value=<%=option.toLowerCase()%> class="checkbox"></td>
														<td><%=option%></td>
														<td></td>
														</tr>
														<%
															}
														%>
													</tbody>
												</table> 
											</div>
											<button class="btn btn-outline btn-info" type="submit">Print</button>
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

</body>

</html>
    