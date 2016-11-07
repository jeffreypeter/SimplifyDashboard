<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="simplify.bean.DBClientBean"%>
<%@page import="simplify.bean.TaskBean" %>
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
           <%-- <div class="navbar-default sidebar" role="navigation">
                <%@ include file="/pages/common/navigation-sidebar.jsp" %>
                <!-- /.sidebar-collapse -->
            </div>  --%>
            <!-- /.navbar-static-side -->
        </nav>

        <!-- Page Content -->
        <div id="">
            <div class="container-fluid">
                 <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            Parameter Search
                        </div>
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
									<%
										ArrayList<TaskBean> beanList = (ArrayList<TaskBean>) request.getAttribute("beanList");
										String envi = (String) request.getAttribute("envi");
										String paramName = (String) request.getAttribute("paramName");
										String paramValue = (String) request.getAttribute("paramValue");
										HashMap<String,String> statusMap = new HashMap<String,String>();
										String name = "";
										String disabled = "";
										String hidden="";
										HashMap<String,String> piMap = new HashMap<String,String>();
										for (TaskBean bean : beanList) {
											piMap.put(bean.getProId(),bean.getProName());
											statusMap.put(bean.getProId(),bean.getProStatus());			
										}	
									%>
									<div class="toolpanel">
											<div class="form-group"><label><%=paramName%> :: </label><span class="text-warning"> <%=paramValue%></span>
											<label>Status :: </label>
											
											<% if(piMap.size()!=1) {
												disabled="disabled='disabled'";
												hidden ="hidden='hidden'";
											%>											
											<%
											for(String piid:statusMap.keySet()) {
												String status=statusMap.get(piid);
											%>
												<span class ="text-warning" id="status-<%=piid%>" <%=hidden%>><%=status%></span> 
											<% } %>
											
											<% }%>
											</div>
											<form id="reload"><span id="homeButton" onclick="location.href='ParamController';"></span>
											<span id="reloadButton"></span></form>
											<input type="hidden" name="envi" value="<%=envi%>">
											<input type="hidden" name="paramName" value="<%=paramName%>">
											<input type="hidden" name="paramValue" value="<%=paramValue%>">
											<select id="selectProcess" class="form-control form-control-width toolpanel-inputleft">
											<% if(piMap.size()!=1) {
												disabled="disabled='disabled'";
												hidden ="hidden='hidden'";
											%>
												<option value="all">All</option>
											<% }%>
											<%
											for(String piid:piMap.keySet()) {			
												
											%>
												<option value="<%=piid %>"><%=piMap.get(piid) %></option>
											<%
											}
											%>
											</select>		
											<input type="button" class="btn btn-primary toolpanel-inputleft" id="checkTimer" value="CheckTimer" <%=disabled%>>
											<input id="detailsButton" class="btn btn-primary toolpanel-inputleft" type="button" value="Details"  <%=disabled%>>
												
											<!-- <input type="button" class="btn btn-primary toolpanel-inputright" value="Assign Task" onclick="assignTask()"> -->
									</div>
									<table class="table table-striped table-bordered table-hover" id="dataTables-example" style="font-size: 10px;">
											<thead>
												<tr style="font-size: 14px">
													<th>Process Name</th>
													<th>Task Name</th>
													<th>Task Status</th>
													<th>Assigned To</th>
													<th>Start Date</th>
													<th>Closed Date</th>
												</tr>
											</thead>
											<tbody id="data">
												<%
													for (TaskBean bean : beanList) {
												%>
												<% String status = bean.getTaskStatus();
													String className ="";
													String rowClassName="";
													String proStatus = bean.getProStatus();
												 	if("Received".equalsIgnoreCase(status) || "New".equalsIgnoreCase(status)) {
												 		className = "class='bpm-received'";
												 	} 
												 	/* if() {
												 		
												 	} */
												%>
												<tr class="<%=bean.getProId().toString()%>" id="<%=bean.getTaskId()%>" >
													<td><%=bean.getProName() %></td>
													<td ><b style="font-size: medium;" class="getDetails" value="<%=bean.getProId().toString()%>"><%=bean.getTaskName()%></b>:<span class="getTaskDetails"><%=bean.getTaskId()%></span></td>
													
													<td <%=className%>><%=status%></td>					
													<td><b class="assignTask"><%=bean.getAssignedTo()%></b></td>
													<td><%=bean.getStartDate()%></td>
													<td><%=(bean.getDueDate()==null ? "" : bean.getDueDate())%></td>
												</tr>
												<%
													}
												%>
											</tbody>
									</table>
                            </div>
                            <!-- /.table-responsive -->                           
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
				</div>
							<!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
	<div id="popup">
		<div class="popup-container">
			<div class="panel panel-info">
	             <div class="panel-heading">
	                <div class="popup-title"><span id="popup-title-content"></span> <span id="popup-close" title="close"><i class="fa fa-arrows-alt"></i></span></div>
	             </div>
	             <div class="panel-body" id="popup-body">
	             </div>
	         </div>
        </div>
	</div>
   <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="${pageContext.request.contextPath}/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/jquery.blockUI.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/json-formatter.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/popup.js"></script>
    <script src="${pageContext.request.contextPath}/js/simplify/bpm-results.js"></script>
    
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
        	"lengthMenu": [[-1],["All"]],
        	responsive: true
        });
    });
    </script>
</body>

</html>
    