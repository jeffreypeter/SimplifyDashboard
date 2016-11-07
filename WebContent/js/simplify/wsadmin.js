$(document).ready(function() {
	console.log("wsadmin.js loaded");		
	$('#qdepthtable').DataTable({
      	"lengthMenu": [ 50, 75, 100],
      	"order": [[ 1, "desc" ]]
      });
});

function getQdepth() {
	
	var envi = $("#envi").val();
	console.log("IN getQdepth-- "+envi);
	$('.table-responsive').block({ message: '',
		css:{
			width:'100px',border: 'none', backgroundColor: 'rgba(0,0,0,0)'
		} });
	$('.blockOverlay').css({'border-radius':'5px'});
	$('#refresh').attr('disabled','disabled');
	$('#refresh').find('i').addClass("fa-spin");
	$.ajax({
		url:'WasController?action=qdepth&envi='+envi,
		type:"POST",
		datatype:"json",
		success:function(responseJson) {
			
			console.log(responseJson);
			var status= responseJson.status;
			var columnStr = responseJson.column;
			var dataStr= responseJson.data;
			var table = $('#qdepthtable').DataTable();
			console.log("Status:: "+status);			
			if(status==="success") {
				$('#qdepthtable').find('tbody').empty(); // have to change
				$('.table-responsive').unblock();
				table.rows().remove();
				var tr='';
				var columns = columnStr.split(',');
				console.log(columns);				
				dataArr = dataStr.split('!!!');
				$.each(dataArr,function(i,items) {
					var itemList = new Array();
					if(items.trim()!=="") {
						console.log(items);
						itemList = items.split('---');
						table.row.add(itemList).draw();
					}
				});
			}
			$('#refresh').removeAttr('disabled');
			$('#refresh').find('i').removeClass("fa-spin");
		}
	});
	
}