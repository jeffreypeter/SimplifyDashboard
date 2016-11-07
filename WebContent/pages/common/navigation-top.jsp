<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/SimplifyDashboard/SimplifyDashboardController">Simplify</a>
            </div>
			<ul class="nav navbar-top-links navbar-right">
			    <ul class="nav navbar-top-links navbar-right">
					<li class="dropdown" title="Dashboard">
					    <a href="/SimplifyDashboard/SimplifyDashboardController"><i class="fa fa-dashboard fa-fw"></i>
					    </a>                    
					</li>
					<li class="dropdown" title="Workflow">
					   <a href="/SimplifyDashboard/BPMController?action=workflow-lookup"><i class="fa fa-binoculars fa-fw"></i></a>              
					</li>	
					
					<li class="dropdown" title="Debug">
					    <a href="/SimplifyDashboard/DataController?action=report-debug"><i class="fa fa-table fa-fw"></i>
					    </a>                    
					</li>
					<li class="dropdown" title="File Sharing">
					    <a href="/SimplifyDashboard/action.do/file-sharing"><i class="fa fa-share-alt-square"></i>
					    </a>                    
					</li>
					<li class="dropdown" title="Settings">
						<a href="/SimplifyDashboard/action.do/settings"><i class="fa fa-wrench fa-fw"></i></a> 
					</li>
				</ul>
			</ul>