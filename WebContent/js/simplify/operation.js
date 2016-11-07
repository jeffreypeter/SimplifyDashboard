var refresh = false;
$(document).ready(function() {
	var data;
	
	console.log("operation.js is ready");
	$("#expand").off().on('click', function() {
		console.log("toggle");
		$("#page-wrapper").toggleClass("toggle");
		$("#sidebar").toggleClass("toggle");
	});
	$('#export').off().on('click',function() {
		console.log("Utility - Export");
		var operation = "export-xls";
//		console.log("data:: "+JSON.stringify(data));
		$.ajax({
			url:"SimplifyDashboardController?action=utility",
			type:"post",
			datatype:"text",
			data:{operation:operation,data:JSON.stringify(data)},
			success:function(responseJson) {
				console.log("status::"+responseJson.status);
				console.log("reportName::"+responseJson.reportName);
				window.open("SimplifyDashboardController?action=download&report="+responseJson.reportName+"&workdir="+responseJson.workdir);
			}
		});
	});
	$("#load-data").off().on('click', function() {
		var envi = $("#envi").val();
		var operation = $("#operation").val();
		console.log("IN:: load-data - " + "envi: "+envi+"; operation: "+operation);
		$('#'+operation+'-table').parent().block({ message: null });
		$.ajax({
			url:"SimplifyDashboardController?action=viewDetails",
			type:"post",
			datatype:"json",
			data:{envi:envi,operation:operation},
			success:function(responseJson) {
				console.log("responseJson:: "+responseJson.msg);
//				if(responseJson.status ==='success') 
				{
					data = responseJson.table;
					var key = responseJson.table.key;
					var value = responseJson.table.value;
					var col = value.columnNameLst;
					var recordLst = value.recordLst;
//					console.log("key:: "+key);
					renderTable(key, col, recordLst);
					$('#export').prop('disabled', false);
					$('#'+key+'-table').parent().unblock();
					$("#function-handle-error-table").dataTable( {
							"bDestroy": true
					});
					refresh = true;
				}
				
				
			}
		});
	});
});
