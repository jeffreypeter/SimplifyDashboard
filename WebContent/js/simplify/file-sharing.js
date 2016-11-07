$(document).ready(function(){
	console.log("File-sharing loaded");	
	loadFiles();
	var myDropzone = new Dropzone("#shareFileDropzone");
	Dropzone.autoDiscover = false;
	  myDropzone.on("complete", function(file) {
		  loadFiles();
	  });
	$("#load").click(function() {
		loadFiles();
		myDropzone.removeAllFiles();
	});
	$(document).on('click','.delete-file',function() {
		console.log("IN:: delete-file method");
		var fileName =this.getAttribute("value");
		$.get("/SimplifyDashboard/action.do/file-sharing/delete?fileName="+fileName,function(response){
			console.log((response));
			loadFiles();
		});
		
	});
	function loadFiles() {
		console.log("Click:: Load")
		$.ajax({
			url:"/SimplifyDashboard/action.do/file-sharing/list",
			type:"GET",		
			success:function (responseJson){
				var status = JSON.parse(responseJson).status;				
				var data = $.parseJSON(responseJson).data;
				console.log(data)
				var i=0;
//				$.each( $.parseJSON(data),function() {
//					console.log(i++);
//					var j=0;
//					$.each(this,function(k,v){
//						console.log(k+":"+v);
//					});
//				});				
				renderSharedFileTable("share-file",["name"],$.parseJSON(data),"absolutePath");
				$("#share-file-table").dataTable( {		
					"bDestroy": true					
			    } );
			}
		});
	}
	function renderSharedFileTable(key, col, recordLst,checkBox) {
		console.log("IN:: renderTable");		
		var id = key+"-table";
		console.log("id:: "+id);
		console.log("col:: "+col);
		console.log("recordLst:: "+recordLst);
		$('table#'+id+' > thead').remove();
		$('table#'+id+' > tbody').remove();
		$('table#'+id).append("<thead></thead>");
		$('table#'+id).append("<tbody></tbody>");		
		
		var colVal;
//		var colVal='<th><input name="select_all" value="1" id="example-select-all" type="checkbox" /></th>';
		$.each(col, function(k,v){
			colVal = colVal + ("<th>"+v+"</th>");
		});
		colVal = colVal + '<th>Action</th>'
		var colFinalVal = "<tr>"+colVal.toUpperCase()+"</tr>";	
		
		$('table#'+id+' > thead').append(colFinalVal);
		var rowFinalVal = '';
		$.each(recordLst, function(k,v) {
			var rowVal =	
//							'<td><input type="checkbox" name="'+v.name+'" value="'+v.absolutePath+'"></td>'+
							'<td><a href="/SimplifyDashboard/action.do/file-sharing/download?fileName=' +v.name+'">'+v.name+'</a></td>' +
							'<td><a class="delete-file" value="'+v.name+'"><i class="fa fa-trash"></i></a></td>';
							
			
			rowFinalVal = rowFinalVal + ("<tr>"+rowVal+"</tr>");
		});
		$('table#'+id+' > tbody').append(rowFinalVal);
	}
});