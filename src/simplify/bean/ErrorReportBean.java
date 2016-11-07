package simplify.bean;

import java.util.HashMap;

public class ErrorReportBean {

	private String fileName;
	private HashMap<String,Integer> errorTypeMap;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public HashMap<String, Integer> getErrorTypeMap() {
		return errorTypeMap;
	}
	public void setErrorTypeMap(HashMap<String, Integer> errorTypeMap) {
		this.errorTypeMap = errorTypeMap;
	}
	
}
