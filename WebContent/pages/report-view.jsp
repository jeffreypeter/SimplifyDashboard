<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="simplify.bean.DBClientBean"%>
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
          <!-- <div class="row">
								 <div class="col-lg-12">
							        <div class="btn-group btn-breadcrumb">
							            <a href="#" class="btn btn-default"><i class="glyphicon glyphicon-home"></i></a>
							            <a href="#" class="btn btn-default">Snippets</a>
							            <a href="#" class="btn btn-default">Breadcrumbs</a>
							            <a href="#" class="btn btn-default">Default</a>
							        </div>
							        </div>
								</div> -->  	
                 <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Database Report
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
	                        <div>
	                      	  <%
										String msg= (String) request.getAttribute("msg");
										if("success".equals(msg)) {
										String report = (String) request.getAttribute("report");
										String param = (String) request.getAttribute("param");
										String envi = (String) request.getAttribute("envi");
										DBClientBean bean = (DBClientBean) request.getAttribute("reportData");
										ArrayList<String> columnNameList = (ArrayList<String>) bean.getColumnNameList();
										ArrayList<LinkedHashMap<String, String>> dataList = (ArrayList<LinkedHashMap<String, String>>) bean.getDataList();
										int colCount = columnNameList.size();
								%>
								<label>Report :: </label><span class="text-warning"> <%=report%></span>
								<%if(param!=null && !param.isEmpty()) {%>
								<label>Param :: </label><span class="text-warning"> <%=param%></span>
								<%}%>							
								<label>Envi :: </label><span class="text-warning"> <%=envi%></span>		
							</div>
                            <div class="dataTable_wrapper" style="overflow-x: scroll;">
									
									<table class="table table-striped table-bordered table-hover" id="dataTables-example" style="font-size: 10px;">

										<thead>
											<tr>
												<%
													for (String columnName : columnNameList) {
												%>
												<th><%=columnName%></th>
												<%
													}
												%>
											
										</thead>
										<tbody>
											<%
												for (LinkedHashMap<String, String> data : dataList) {
											%>


											<tr>
												<%
													for (int i = 0; i < colCount; i++) {
												%>
												<td><%=data.get(columnNameList.get(i))%></td>
												<%
													}
												%>
											</tr>
											<%
												}
											%>
										</tbody>
									</table>
                            		<%} %>
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

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({        	
        	"lengthMenu": [ 25, 50, 75, 100]
        });
    });
    </script>
</body>

</html>
    