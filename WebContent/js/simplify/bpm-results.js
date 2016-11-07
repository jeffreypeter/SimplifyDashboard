var block_div=".panel-body";
$("#selectProcess").change(function() {
	$("#data").children().hide();
	var value = $("#selectProcess").val();
	if (value === "all") {
		$("#data").children().show();
		$("#detailsButton").attr('disabled', 'disabled');
		$("#deleteButton").attr('disabled', 'disabled');
		$("#terminateButton").attr('disabled', 'disabled');
		$("span[id^='status-']").attr('hidden', 'hidden');
		$("#checkTimer").attr('disabled', 'disabled');
	} else {
		$("." + value).show();
		$("span[id^='status-']").attr('hidden', 'hidden');
		$("#status-" + value).removeAttr('hidden');
		$("#detailsButton").removeAttr('disabled');
		$("#deleteButton").removeAttr('disabled');
		$("#terminateButton").removeAttr('disabled');
		$("#checkTimer").removeAttr('disabled');
	}
});
$("#detailsButton").click(
		function() {
			console.log("in detailsButton");
			var envi = $("input[name='envi']").val();
			var piid = $("#selectProcess").val();
			$.post('BPMController?action=instanceDetails&instanceId=' + piid + '&envi=' + envi, function(responseJson) {
				var jsonObj = responseJson.data.data;
				var responseStr = (jsonObj);
				var details = responseStr.replace(/\\/g, "").replace(/"{/g, "{").replace(/}"/g, "}");
				popup_show("Details","<textarea wrap='off' readonly>"+jsonParser(details)+"</textarea>");
			});
		});
$(".getDetails").click(
		function() {
			console.log("in getDetails");
			var piid = $(this).attr('value');
			var envi = $("input[name='envi']").val();
			console.log("In .getDetails - piid:: "+piid);
			$.ajax({
				url : 'BPMController?action=instanceDetails&instanceId='+ piid + '&envi=' + envi,
				type : "POST",
				datatype : "json",
				success : function(responseJson) {
					var jsonObj = responseJson.data.variables;
					var responseStr = JSON.stringify(jsonObj);		
					console.log(responseStr );
					var details = responseStr.replace(/\\/g, "").replace(/"{/g, "{").replace(/}"/g, "}");
					popup_show("Details","<textarea wrap='off' readonly>"+jsonParser(details)+"</textarea>");
				}
			});
		});

$("#checkTimer").click(function () {
	var envi = $("input[name='envi']").val();
	var piid = $("#selectProcess").val();
	console.log("TaskId:: "+$(this).html());	
	$.ajax({
		url:'BPMController?action=taskDetails',
		type:"POST",
		datatype:"json",
		data:{envi:envi,piid:piid},
		success:function(responseJson) {	
			var jsonObj = jQuery.parseJSON(responseJson);
			console.log(responseJson);
			console.log(jsonObj.status);
			if(jsonObj.status==='success') {
				var timerlist = jsonObj.timerlist;
				var htmlStr = "";
				for(var i=0;i<timerlist.length;i++) {
					var task = timerlist[i];
					var timerdtl="";
					for(var j=0;j<task.timerdtl.length;j++) {
						var timerName = task.timerdtl[j].replace(/ /g,'').split("--");
						console.log(timerdtl[j]);
						timerdtl=timerdtl + " -- <span id="+piid+"--"+timerName[1]+" class='timer text-warning'>"+timerName[0]+"</span>";
					}
					htmlStr = htmlStr +"<div class='timerDtls'>"+task.taskname+": "+timerdtl+"</div>";
				}
				popup_show("Timer Details",htmlStr);
			} else {
				$("#timerBody").html();
				popup_show("Timer Details","<span class='timerMsg'>"+jsonObj.status+"</span>");
			}
		}
	});
});
$(document).ajaxStart(function() {
	$(block_div).block({ message: null }); 
}).ajaxStop(function() {
	$(block_div).unblock();
});
$("#selectProcess").change(function() {
	$("#data").children().hide();
	var value = $("#selectProcess").val();
	if(value === "all") {
		$("#data").children().show();
		$("#detailsButton").attr('disabled','disabled');
		$("#deleteButton").attr('disabled','disabled');
		$("#terminateButton").attr('disabled','disabled');
		$("span[id^='status-']").attr('hidden','hidden');
		$("#checkTimer").attr('disabled','disabled');
	} else {
		$("."+value).show();		
		$("span[id^='status-']").attr('hidden','hidden');
		$("#status-"+value).removeAttr('hidden');
		$("#detailsButton").removeAttr('disabled');
		$("#deleteButton").removeAttr('disabled');
		$("#terminateButton").removeAttr('disabled');
		$("#checkTimer").removeAttr('disabled');
	}			
});
$(document).on('click',".timer",function() {
	var id = $(this).attr('id').split("--"); 
	var envi = $("input[name='envi']").val();
	$.ajax({
		url:'BPMController?action=triggerTimer',
		type:"POST",
		datatype:"json",
		data:{envi:envi,instanceId:id[0],tokenId:id[1]},				
		success:function(responseJson) {
			sleep(2000);
			reload();
		}
	});
});
function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
}
function reload() {
	var envi = $("input[name='envi']").val();
	var paramName = $("input[name='paramName']").val();
	var paramValue = $("input[name='paramValue']").val();
	document.getElementById("reload").action = 'BPMController?action=search&envi=' + envi+'&paramName='+ paramName + '&paramValue=' + paramValue;
	document.getElementById("reload").method = "post";
	document.getElementById("reload").submit();	
}