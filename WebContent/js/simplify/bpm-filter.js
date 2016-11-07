function bpdParam() {
	console.log("IN bpdParam");
//	var bpdParams = $('.bpd-Param');
	var data = new Object();
	var envi = $('#envi').val();
	$('.bpd-param').each(function() {
		if($(this).val().trim()!="") {
			data[$(this).attr("name")] = $(this).val();
		}
	});
	console.log("data: "+JSON.stringify(data) +" "+"Envi: "+envi);
	$('#data').val(JSON.stringify(data));
	/*$.post('BPMController?action=filter',{envi:envi,data:JSON.stringify(data)},function(output) {
		
	});*/
	return true;
} 