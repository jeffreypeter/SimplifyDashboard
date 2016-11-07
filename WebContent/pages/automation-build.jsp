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
            <div class="navbar-default sidebar" role="navigation">
                <%@ include file="/pages/common/navigation-sidebar.jsp" %>
            </div>
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Build Automation</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">Build Ear</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-6">
										<div class="form-group">
												<label>Select Project</label> 
												<select multiple class="form-control" name="project">
												</select>
												<p class="help-block">Please enter the value of the Process Id.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
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
	<%-- <script src="${pageContext.request.contextPath}/js/simplify/common.js"></script> --%>
	<%-- <%@ include file="/pages/common/import-js.jsp" %> --%>

</body>

</html>
    