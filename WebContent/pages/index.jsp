<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Dashboard</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">                
                <div class="col-lg-3 col-md-6">
                
                    <div class="panel panel-primary floater" id="today-app-count-div"> 
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-buysellads fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="today-app-count">0</div>
                                    <div id="today-app-count-date">&nbsp</div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <div><strong>Application Submitted</strong></div>
                                </div>
                            </div>
                        </div>
                       		 
                        <a href="#" onclick="refreshDashboard('today-app-count','db','prod')">
                            <div class="panel-footer">
                                <span class="pull-left">Refresh Count</span>
                                <span class="pull-right"><i class="fa fa-refresh"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green floater" id="today-policy-count-div">
                        <div class="panel-heading">                            
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-user fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="today-policy-count">0</div>
                                    <div id="today-policy-count-date">&nbsp</div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <div><strong>Policies Converted</strong></div>
                                </div>
                            </div>
                        </div>
                        <a href="#" onclick="refreshDashboard('today-policy-count','db','prod')">
                            <div class="panel-footer">
                                <span class="pull-left">Refresh Count</span>
                                <span class="pull-right"><i class="fa fa-refresh"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow floater" id="handle-error-count-div">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-inr fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="handle-error-count">0</div>
                                    <div id="handle-error-count-date">&nbsp</div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <div><strong>Handle Error</strong></div>
                                </div>
                            </div>
                        </div>
                       
                            <div class="panel-footer">
                                <a href="SimplifyDashboardController?action=viewDetails&operation=function-handle-error&envi=prod"><span class="pull-left">View Details</span></a>
                                 <a href="#" onclick="refreshDashboard('handle-error-count','bpd','prod')"><span class="pull-right"><i class="fa fa-refresh"></i></span></a>
                                <div class="clearfix"></div>
                            </div>
                    </div>
                </div>                
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red floater" id="today-app-failed-count-div">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-times fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="today-app-failed-count">0</div>
                                    <div id="today-app-failed-count-date">&nbsp</div>                                    
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <div><strong>Failed Applications</strong></div>
                                </div>
                            </div>
                        </div>
                        <a href="#"  onclick="refreshDashboard('today-app-failed-count','db','prod')">
                            <div class="panel-footer">
                                <span class="pull-left">Refresh Count</span>
                                <span class="pull-right"><i class="fa fa-refresh"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                    
                </div>
                
                
            </div>
            <!-- /.row -->
            <div class="row">
	            <div class="col-lg-8">
		                    <div class="panel panel-success" id="chart-appsub-div">
		                        <div class="panel-heading">
		                            <i class="fa fa-bar-chart-o fa-fw"></i> Info Chart
		                            <div class="pull-right">
		                                <div class="btn-group">
		                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
		                                        Actions
		                                        <span class="caret"></span>
		                                    </button>
		                                    <ul class="dropdown-menu pull-right" role="menu">
		                                        <li><a href="#" onclick="populateChart('chart-appsub','today')">Today</a>
		                                        </li>
		                                        <!-- <li><a href="#" onclick="populateChart('chart-appsub','month')">This Month</a>
		                                        </li> -->
		                                        <li class="divider"></li>
		                                        <li><a href="#">Separated link</a>
		                                        </li>
		                                    </ul>
		                                </div>
		                            </div>
		                        </div>
		                        <!-- /.panel-heading -->
		                        <div class="panel-body">
		                           <ul class="nav nav-tabs">
		                                <li class="active"><a href="#home" data-toggle="tab" aria-expanded="true">Today's Applicaiton</a></li>
		                                <li class=""><a href="#settings" data-toggle="tab" aria-expanded="false">Settings</a></li>
	                           		</ul>
	                           		<div class="tab-content">	
		                                <div class="tab-pane fade active in" id="home">
		                                    <div id="chart-appsub"></div>
		                                </div>
	                                <div class="tab-pane fade" id="settings">
	                                    <h4>Settings Tab</h4>
	                                    <p>Under Construction</p>
	                                </div>
	                            </div>
		                        </div>
		                        <!-- /.panel-body -->
		                    </div>
		                </div>
	                
	                
	                <!-- /.col-lg-8 -->
	                <div class="col-lg-4">
	                <!--
	                	<div class="info-box bd-green" id="today-payment-count-div">
	                		<span class="info-box-icon bg-green"><i class="fa fa-times fa-5x"></i></span>
	                		<div class="info-box-content">
	                			<span class="info-box-text">Test</span>
	                			<span class="info-box-number">111</span>
	                		</div>
	                	</div> -->
	                	
	                    <!-- <div class="panel panel-red floater" id="today-payment-count-div">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-times fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge" id="today-payment-count">0</div>
	                                    <div id="today-payment-count-date">&nbsp</div>                                    
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-xs-12 text-right">
	                                    <div><strong>Payment count</strong></div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="#"  onclick="refreshDashboard('today-payment-count','db','prod')">
	                            <div class="panel-footer">
	                                <span class="pull-left">Refresh Count</span>
	                                <span class="pull-right"><i class="fa fa-refresh"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                    
	                
	                    <div class="panel panel-red floater" id="today-edit-count-div">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-times fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge" id="today-edit-count">0</div>
	                                    <div id="today-edit-count-date">&nbsp</div>                                    
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-xs-12 text-right">
	                                    <div><strong>Application Edited</strong></div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="#"  onclick="refreshDashboard('today-edit-count','db','prod')">
	                            <div class="panel-footer">
	                                <span class="pull-left">Refresh Count</span>
	                                <span class="pull-right"><i class="fa fa-refresh"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                    
	               -->
	                    <div class="panel panel-warning">
	                        <div class="panel-heading">
	                            <i class="fa fa-server fa-fw"></i> SOA Server Status
	                        </div>
	                        
	                        <div class="panel-body">
	                            <div class="list-group" id="serverStatus">
	                                <a href="#" class="list-group-item" id="server-dev">
	                                    <i class="fa fa-star-o fa-fw"></i> DEV
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a>
	                                <a href="#" class="list-group-item" id="server-bsit">
	                                    <i class="fa fa-star-half-o fa-fw"></i> BSIT
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a>
	                                <a href="#" class="list-group-item" id="server-msit">
	                                    <i class="fa fa-star-half-o fa-fw"></i> MSIT
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a> 
	                                <a href="#" class="list-group-item" id="server-uat">
	                                    <i class="fa fa-star fa-fw"></i> UAT
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a>
	                                <a href="#" class="list-group-item" id="server-prod_169">
	                                    <i class="fa fa-star fa-fw"></i> PROD - 169
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a>
	                                <a href="#" class="list-group-item" id="server-prod_173">
	                                    <i class="fa fa-star fa-fw"></i> PROD - 173
	                                    <span class="pull-right text-muted small"><em></em>
	                                    </span>
	                                </a>
	                                                                                              
	                            </div>
	                            
	                            <a href="#" class="btn btn-default btn-block" onclick=checkAllServerStatus()>Check All Servers</a>
	                        </div>
	                        
	                    </div>
	                    
	                    
	                </div> 
	                <!-- /.col-lg-4 -->
            </div>
            <div class="row">
				<div class="col-lg-12">
					<div class="panel panel-danger">
						<div class="panel-heading">
							<i class="fa fa-server fa-fw"></i> Excepiton Summary Report<span class="pull-right" id="report-exception" title="Refresh"><i class="fa fa-refresh"></i> </span>
						</div>
						<div class="panel-body">
							<table class="table table-striped table-bordered table-hover" id="report-exception-table" style="font-size: 12px;"></table>
						</div>
					</div>
				</div>
       		 </div>
       		 
       		 <div class="row">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">
							<i class="fa fa-server fa-fw"></i> Communication Report<span class="pull-right" title="Copy">
							<a data-toggle="collapse"  href="#div-report-communication"> <i class="fa fa-chevron-right"></i> </a></span>
						</div>
						<div class="panel-body">
							<div id="div-report-communication" class="panel-collapse collapse">
								<table class="table table-striped table-bordered table-hover" id="report-communication" style="font-size: 12px;">
									<thead>
										<tr>
											<th>Scenario</th>
											<th>Type</th>
											<th>Total Count</th>
											<th>Difference</th>											
										</tr>
									</thead>
									<tbody>
										<tr>
											<td rowspan="6">Application</td>
											<td>Submission Complete</td>
											<td id="report-communication-1-1"> - </td>
											<td id="report-communication-1-2"> - </td>											
										</tr>
										<tr>
											<td>Payment Made</td>
											<td id="report-communication-2-1"> - </td>
											<td id="report-communication-2-2"> - </td>
										</tr>
										<tr>
											<td>Cheque Bounce</td>
											<td id="report-communication-3-1"> - </td>
											<td id="report-communication-3-2"> - </td>
										</tr>
										<tr>
											<td>policynotdelivered</td>
											<td id="report-communication-4-1"> - </td>
											<td id="report-communication-4-2"> - </td>
										</tr>
										<tr>
											<td>policydispatched</td>
											<td id="report-communication-5-1"> - </td>
											<td id="report-communication-5-2"> - </td>
										</tr>
										<tr>
											<td>policyissuance</td>
											<td id="report-communication-6-1"> - </td>
											<td id="report-communication-6-2"> - </td>
										</tr>
										<tr>
											<td rowspan="2">Medicalscheduling_c</td>
											<td>dc_change+appointment confirmed (Email & SMS), Reschedule (SMS)</td>
											<td id="report-communication-7-1"> - </td>
											<td id="report-communication-7-2"> - </td>
										</tr>
										<tr>
											<td>No Show</td>
											<td id="report-communication-8-1"> - </td>
											<td id="report-communication-8-2"> - </td>
										</tr>
										<tr>
											<td>Preverification</td>
											<td>PCVCSuccess</td>
											<td id="report-communication-9-1"> - </td>
											<td id="report-communication-9-2"> - </td>
										</tr>
										<tr>
											<td rowspan="3">Requirement</td>
											<td>makepayment</td>
											<td id="report-communication-10-1"> - </td>
											<td id="report-communication-10-2"> - </td>
										</tr>
										<tr>
											<td>Non Medical Followup</td>
											<td id="report-communication-11-1"> - </td>
											<td id="report-communication-11-2"> - </td>
										</tr>
										<tr>
											<td>furtherrequirement</td>
											<td id="report-communication-12-1"> - </td>
											<td id="report-communication-12-2"> - </td>
										</tr>
										<tr>
											<td rowspan="3">Underwriting Decision</td>
											<td>declined</td>	
											<td id="report-communication-13-1"> - </td>
											<td id="report-communication-13-2"> - </td>								
										</tr>
										<tr>
											<td>rateup</td>
											<td id="report-communication-14-1"> - </td>
											<td id="report-communication-14-2"> - </td>
										</tr>
										<tr>
											<td>Postponed</td>
											<td id="report-communication-15-1"> - </td>
											<td id="report-communication-15-2"> - </td>
										</tr>
									</tbody>
								</table>
								<a class="btn btn-default btn-lg btn-block" onclick="refreshReport('report-communication',15,2)" id="report-error-button"> Refresh Table</a>
							</div>
						</div>
					</div>
				</div>
			</div> 
		</div>
		<%@ include file="/pages/common/import-js.jsp" %>
    </div>
</body>
</html>           