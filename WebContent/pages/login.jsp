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
		<!-- <script src="${pageContext.request.contextPath}/js/angular.min.js"></script>-->
		<script src="${pageContext.request.contextPath}/js/angular/lib/angular.js"></script>
	</head>
	<body ng-app="login" ng-controller="adminController">
	    <div class="container">
	        <div class="row" >
	            <div class="col-md-4 col-md-offset-4">
	                <div class="login-panel panel panel-primary">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Please Sign In</h3>
	                    </div>
	                    <div class="panel-body">	                    
	                        <form role="form">
	                        	<input type="hidden" name="redirect" id="redirect" value="<%= request.getAttribute("redirect")%>" ng-model="redirect">
								<%-- <input type="hidden" name="parameter" value="<%= session.getAttribute("parameter")%>"> --%>
	                            <fieldset>
	                                <div class="form-group input-group">
	                                	<span class="input-group-addon"><i class="fa fa-user"></i></span>
	                                    <input class="form-control" placeholder="User" name="user" type="text" ng-model="user" autofocus required>
	                                </div>
	                                <div class="form-group input-group">
	                                	<span class="input-group-addon"><i class="fa fa-key"></i></span>
	                                    <input class="form-control" placeholder="Password" name="password" type="password" ng-model="password" value="" required>
	                                </div>
	                                <!-- <div class="checkbox">
	                                    <label>
	                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
	                                    </label>
	                                </div> -->
	                                <!-- Change this to a button or input when using this as a form -->
	                                <div class="form-group">                                
	                              	  <button type="button" class="btn btn-lg btn-block btn-primary" ng-click=authenticate()>Login</button>
	                                </div>
	                                <div class="form-group">
	                                	<button type="button" class="btn btn-outline btn-default btn-block" onclick='window.location="/SimplifyDashboard"'>Home</button>
	                                </div>
	                            </fieldset>
	                        </form>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <script src="${pageContext.request.contextPath}/bower_components/jquery/dist/jquery.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angular/controller/adminController.js"></script>
      	<script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
      	<script src="${pageContext.request.contextPath}/plugins/notify/bootstrap-notify.js"></script>
	</body>
</html>

    