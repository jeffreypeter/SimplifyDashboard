function jsonParser(jsonIp) {	
	var jsonFinal="";
	var currentChar=null;
	var indentLevel = 0;	
	for(var i=0;i<jsonIp.length;i++) {
		currentChar = jsonIp.charAt(i);
		switch (currentChar) {
		case ',':
			jsonFinal=jsonFinal+",\n"+jsonIndentLevel(indentLevel);
			break;
		case '{':
			jsonFinal+="{\n"+jsonIndentLevel(++indentLevel);			
			break;
		case '}':
			indentLevel--;
			jsonFinal+="\n"+jsonIndentLevel(indentLevel)+'}';
			break;
		case ':':
			jsonFinal+=": ";
			break;
		case '[':
			jsonFinal+="[\n"+jsonIndentLevel(++indentLevel);
			break;
		case ']':
			indentLevel--;
			jsonFinal+="\n"+jsonIndentLevel(indentLevel)+']';
			break;
		default:
			jsonFinal+=currentChar;
		}
		
	}
	return jsonFinal;		
}
function jsonIndentLevel(indentLevel) {
	var tab='\t';
	var finalTab="";
	for(var i=0;i<indentLevel;i++) {
		finalTab+=tab;
	}
	return finalTab;
	
}