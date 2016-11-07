$(document).ready(function (){
	console.log("adminConfig.js Loaded");
	$("#reload").click(reloadPropertyFile);
	function reloadPropertyFile() {
		console.log("reloadPropertyFile method");
		$.ajax({
			url:"/SimplifyDashboard/action.do/settings/reload",
			type:"POST",
			datatype:"json",
			success:function(responseJSON) {
				console.log("response::"+responseJSON);
				var msg = $.parseJSON(responseJSON).msg;
				
				console.log(msg);
				$.notify({
					icon: 'fa fa-exclamation-circle',
					message: msg
				},{
					type: 'info',
					delay:2000,
					animate: {
						enter: 'animated fadeInDown',
						exit: 'animated fadeOutUp'
					}	
				});
			}		
		})
	}		
});
