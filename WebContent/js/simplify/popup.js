var divId = "#popup";
var _title = '#popup-title-content';
var _body = '#popup-body';
var _close = "#popup-close";
var _hide_div=".dataTable_wrapper";

function popup_hide() {
	$(divId).hide();
}

function popup_show(title,body) {
	$(_title).html(title);
	$(_body).html(body);
	$(divId).show();
	$(_hide_div).block({ message: null });
//	center(divId);
}

function popup_cleanSlate() {
	$(_title).empty();
	$(_body).empty();
}

$(_close).click(function() {
	$(_hide_div).unblock();
	popup_close();	
});

function popup_close() {
	popup_cleanSlate();
	popup_hide();
}

function center(divId) {
    $(divId).css("position","absolute"); 
    console.log($(window).height() );
    $(divId).css("top", (($(window).height()/2 -  $(divId).height())/2) + "px");
    $(divId).css("left", (($(window).width()/2 -  $(divId).width())/2)  +"px");
}
