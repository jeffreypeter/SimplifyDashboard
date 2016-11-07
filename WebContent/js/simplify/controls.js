$(".selectAllCheckbox").click(function () {
	if($(this).is(":checked")) {
		$("input[class='checkbox']").prop("checked",true);
	} else {
		$("input[class='checkbox']").prop("checked",false);
	}
});



$(document).ready(function(){
    $(window).resize(function() {

        ellipses1 = $("#bc1 :nth-child(2)");
        if ($("#bc1 a:hidden").length >0) {ellipses1.show()} else {ellipses1.hide()}
        
        ellipses2 = $("#bc2 :nth-child(2)");
        if ($("#bc2 a:hidden").length >0) {ellipses2.show()} else {ellipses2.hide()}
        
    });
    
});