package simplify.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DBClientBean {

	private ArrayList<String> columnNameList = new ArrayList<String>();
	private ArrayList<LinkedHashMap<String, String>> dataList = new ArrayList<LinkedHashMap<String, String>>();
	public ArrayList<String> getColumnNameList() {
		return columnNameList;
	}
	public void setColumnNameList(ArrayList<String> columnNameList) {
		this.columnNameList = columnNameList;
	}
	public ArrayList<LinkedHashMap<String, String>> getDataList() {
		return dataList;
	}
	public void setDataList(ArrayList<LinkedHashMap<String, String>> dataList) {
		this.dataList = dataList;
	}
	
}
