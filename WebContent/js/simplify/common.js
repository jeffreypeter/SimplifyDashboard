$(document).ready(function() {
	console.log("common.js is ready");
	$('[data-toggle="tooltip"]').tooltip();
	try {
		$.ajax({
			url:'SimplifyDashboardController?action=refreshDashboard',
			type:"POST",
			datatype:"json",
			success:function(responseJson) {
				console.log(responseJson);
				$.each(responseJson, function (index, data) {		        
			        var item = data.key;
			        var value = data.value;
			        var date = data.date;
			        $("#"+item).text(value);
					$("#"+item+"-date").text(date);	
			        console.log(item+" - "+value+" - "+date);
			        
			    });
			}
		});
		$.ajax({
			url:'SimplifyDashboardController?action=refreshTables',
			type:"POST",
			datatype:"json",
			success:function(responseJson) {
				console.log(responseJson);
				$.each(responseJson, function (index, data) {		        
						var key = data.key;
						var value = data.value;
						var col = value.columnNameLst;
						var recordLst = value.recordLst;
						renderTable(key, col, recordLst);
			    });
			}
		});
		$.ajax({
			url:'SimplifyDashboardController?action=refreshCharts',
			type:"POST",
			datatype:"json",
			success:function(responseJson) {
				console.log(responseJson);
				try {
				$.each(responseJson, function (index, data) {	
					
			        var item = data.key;
			        var value = data.value.recordLst;
			        var date = data.date;
			        console.log("item:: "+item);
			        var txt =JSON.stringify(value);
			        console.log(JSON.parse(txt));
			        console.log(value);
					    Morris.Area({
					    	 element: 'chart-appsub',
					        data:JSON.parse(txt),
					        xkey: 'TIME',
					        ykeys:['APP_COUNT'],
					        labels:['APP_COUNT'],
					        pointSize: 2,
					        hideHover: 'auto',
					        resize: true,
					        smooth:true
					    });
			    });
				} catch(e) {
					console.log("error:: "+e);
				}
			}
		});
	} catch(e) {
		console.log("error:: "+e);
	}
	$("[id^='server-']").click(function () {
		console.log($(this).attr("id"));
		var id= $(this).attr("id");
		serverStatus(id);
	});	
	$("#clipboard-table").on('click', function () {
	    $(this).zclip({
	        path: 'http://www.steamdev.com/zclip/js/ZeroClipboard.swf',
	        copy:function(){return $("#clipboard-report-error").html();}
	    });
	});
	
	$("#report-exception").on('click',function() {
		var item =$(this).attr("id");
		$('#'+item+'-table').parent().block({ message: null });
		console.log(item);
		refreshTable(item);		
	});
});

function refreshTable(item) {
	console.log("IN:: refreshTable - "+ item);
	$.ajax({
		url:"DataController?action=populateTable",
		type:"POST",
		data:{item:item},
		success:function(responseJson) {
			console.log(responseJson);
			if(responseJson.status ==='success') {
				var key = responseJson.key;
				var value = responseJson.value;
				var col = value.columnNameLst;
				var recordLst = value.recordLst;
				renderTable(key, col, recordLst);
			}
			$('#'+item+'-table').parent().unblock();
		}
	});
}
function checkAllServerStatus() {
	console.log("IN chechAllServerStatus");
	$('#serverStatus').parent().block({ message: 'Please Wait' });
	$('#serverStatus > a').map(function() {
       serverStatus(this.id);
    });
	$('#serverStatus').parent().unblock();
	
}
function serverStatus(id) {
	var idArr = $('#'+id).attr("id").split("-");
	console.log(id);
	var envi = idArr[1];
	$.ajax({
		url:"SimplifyDashboardController?action=checkServerStatus",
		type:"POST",
		data:{envi:envi},
		success:function(responseJson) {
			console.log(responseJson);
			if(responseJson.status ==='success') {
				console.log('service success');
				if(responseJson.serverStatus) {
					$('#'+id).find('em').html(responseJson.date);
					$('#'+id).css("background-color","#dff0d8");
				} else {
					$('#'+id).find('em').html(responseJson.date);
					$('#'+id).css("background-color","#f2dede");												
				}
			} else {
				console.log('service failed');
			}
		}
	});
}

function refreshDashboard(item,source,envi) {
	console.log("In:: refreshDashboard - "+item+" -source::"+source+" -schema::"+envi);
	var div ="#"+item+"-div";
	$(div).block({ message: null }); 
	if(source === 'db') {
		$.ajax({
			url:'DataController?action=refreshDashboard',
			type: "POST",
			datatype:"json",
			data:{item:item,envi:envi},
			success:function(responseJson) {
				var status = responseJson.msg;			
				console.log("Status:: "+status);
				if(status === "success") {
					var value = responseJson.output.value;
					console.log("output:: "+value);
					$("#"+item).text(value);
					$("#"+item+"-date").text(responseJson.output.date);
					$(div).unblock();
				}
			}
			});
	} else {
		console.log("Refresh bpm");
		$.ajax({
			url:'BPMController?action=refreshDashboard',
			type:'POST',
			datatype:"json",
			data:{item:item,envi:envi},
			success:function(responseJson) {
				var status = responseJson.msg;			
				console.log("Status:: "+status);
				if(status === "success") {
					var value = responseJson.output.value;
					console.log("output:: "+value);
					$("#"+item).text(value);
					$("#"+item+"-date").text(responseJson.output.date);
					$(div).unblock();
				}
			}
		});
	}
}
function invokeFunction(item,source,envi) {
}
function populateChart(item,period) {
	console.log("In:: populateChart");
	var input = item+'-'+period;//Selecting the correct template
	var div ="#"+item+"-div";
	$(div).block({ message: null }); 
	$.ajax({ 
		url:'DataController?action=populateChart',
		type: "POST",
		datatype:"json",
		data:{item:input},
		success:function(responseJson) {	
			console.log(responseJson.value.recordLst);			
			$("#"+item).empty();
			    Morris.Area({
			        element: 'chart-appsub',
			        data:responseJson.value.recordLst,
			        xkey: 'TIME',
			        ykeys:['APP_COUNT'],
			        labels:['APP_COUNT'],
			        pointSize: 2,
			        hideHover: 'auto',
			        resize: true,
			        smooth:true
			    });
			    $(div).unblock();
			    //localStorage.setItem(item, JSON.stringify(responseJson.value.recordLst));
		}
		});
}
function refreshReport(id,x,y) {
	console.log("IN:: refreshReport");
	var button= "#"+id+"-button";
//	$(button).addClass("disabled");
	for(var i=1;i<=x;i++) {
		for(var j=1;j<=y;j++) {
			var item = id+"-"+i+"-"+j;
			console.log("item:: "+item);
			$.ajax({ 
				url:'DataController?action=refreshDashboard',
				type: "POST",
				datatype:"json",
				data:{item:item},
				success:function(responseJson) {
					var status = responseJson.msg;			
					console.log("Status:: "+status);
					if(status === "success") {
						var value = responseJson.output.value;
						var key = responseJson.output.key;
						console.log(key+" :: "+value);
						$("#"+key).text(value);
					}
				}
			});
		}
	}
}
function renderTable(key, col, recordLst) {
	console.log("IN:: renderTable");		
	var id = key+"-table";
	console.log("id:: "+id);
	console.log("col:: "+col);
	console.log("recordLst:: "+recordLst);
	$('table#'+id+' > thead').remove();
	$('table#'+id+' > tbody').remove();
	$('table#'+id).append("<thead></thead>");
	$('table#'+id).append("<tbody></tbody>");		
	
	var colVal='';
	$.each(col, function(k,v){
		colVal = colVal + ("<th>"+v+"</th>");
	});
	var colFinalVal = "<tr>"+colVal+"</tr>";
	console.log("colFinalVal ::" +colFinalVal);
	
	$('table#'+id+' > thead').append(colFinalVal);
	var rowFinalVal = '';
	$.each(recordLst, function(k,v) {
		var rowVal = '';
		$.each(v, function(k,v) {
			rowVal = rowVal + ("<td>"+v+"</td>");
			
		});
		rowFinalVal = rowFinalVal + ("<tr>"+rowVal+"</tr>");
	});
	$('table#'+id+' > tbody').append(rowFinalVal);
}