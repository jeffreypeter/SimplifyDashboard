package simplify.utils.property;

import java.util.LinkedHashMap;

public class DatabasePropertyReader extends XPropertyReader{
	
	private LinkedHashMap<String,String> connectionMap = null;
	private String envi = null;
	public DatabasePropertyReader(String envi) {
		super();
		this.envi = envi;
	}
	public DatabasePropertyReader() {
		super();
	}
	public LinkedHashMap<String,String> getConnectionDetails() {
		String xPath = "database/envi-list/connection[envi='"+envi.toUpperCase()+"']";
		this.connectionMap = getChildMap(xPath);
		return connectionMap;
	}	
	public String getWorkDir(){
		return getValue("database/work-dir");		
	}
	public String getQuery(String key) {
		return getValue("database/query-list/"+key.toLowerCase());
	}
	public LinkedHashMap<String,String> getItemDetailsMap(String page,String item) {
		LinkedHashMap<String,String> enviMap = null;
		String xPath = "database/portal/report-"+page+"/"+item+"-list/"+item;
		enviMap = getChildrenMap(xPath);
		return enviMap;
	}
	public static void main(String[] args) {
//		System.out.println("total-app-count:: "+getQuery("total-app-count"));
//		getEnviMap("debug","report");
	}
}
