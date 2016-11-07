<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div class="sidebar-nav navbar-collapse">
     <ul class="nav" id="side-menu">                        
         <li>
             <a href="/SimplifyDashboard/SimplifyDashboardController"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
         </li>
         <li>
             <a href="#"><i class="fa  fa-database fa-fw"></i> Reports<span class="fa arrow"></span></a>
             <ul class="nav nav-second-level">
            	 <li>
                     <a href="/SimplifyDashboard/DataController?action=report-debug"><i class="fa fa-table"></i> Debug</a>
                 </li>
            	 <li>
                     <a href="/SimplifyDashboard/AnalyticsController?action=report-mobility"><i class="fa fa-file-excel-o"></i> Mobility</a>
                 </li>
             </ul>
             <!-- /.nav-second-level -->
         </li>
         <li>
             <a href="#"><i class="fa fa-gears fa-fw"></i> Workflow<span class="fa arrow"></a>
             <ul class="nav nav-second-level">
             	<li>
             		 <a href="/SimplifyDashboard/BPMController?action=workflow-lookup"><i class="fa  fa-binoculars fa-fw"></i> Param Search</a>
             	</li>
             	<li>
             		<a href="/SimplifyDashboard/BPMController?action=workflow-filter"><i class="fa  fa-filter fa-fw"></i> Task Filter</a>
             	</li>
             </ul>
         </li>        
         <li>
             <a href="#"><i class="fa fa-wrench fa-fw"></i> Log Monitor<span class="fa arrow"></span></a>
             <ul class="nav nav-second-level">
            	 <li>
                     <a href="/SimplifyDashboard/LinuxController?action=logsearch"> Log Search</a>
                 </li>
             </ul>             
         </li>
         <li>
             <a href="#"><i class="fa fa-dropbox fa-fw"></i>Build Automation<span class="fa arrow"></span></a>
             <ul class="nav nav-second-level">
            	 <li>
                     <a href="/SimplifyDashboard/BuildAutomationController"> Build Ear</a>
                 </li>
             </ul>             
         </li>
         <li>
         	 <a href="#"><i class="fa fa-dropbox fa-fw"></i> WAS Monitoring<span class="fa arrow"></span></a>
             <ul class="nav nav-second-level">
            	 <li>
                     <a href="/SimplifyDashboard/WasController?action=qdepth"> Queue Depth</a>
                 </li>
             </ul>
         </li>
         
     </ul>
 </div>