package simplify.utils.property;

import java.util.LinkedHashMap;

public class WorkflowPropertyReader extends XPropertyReader {
	
	private String envi; 
	private LinkedHashMap<String,String> connectionMap = null;
	
	public WorkflowPropertyReader(String envi) {
		this.envi = envi;
	}
	public WorkflowPropertyReader() {
		
	}
	public LinkedHashMap<String,String> getConnectionDetails() {
		String xPath = "workflow/envi-list/connection[envi='"+envi.toUpperCase()+"']";
		this.connectionMap = getChildMap(xPath);
		return connectionMap;
	}
	public LinkedHashMap<String,String> getItemDetails(String item) {
		String xPath = "workflow/service-list/service[item='"+item+"']";
		LinkedHashMap<String,String> itemDetails = getChildMap(xPath);
		return itemDetails;		
	}
	public LinkedHashMap<String,String> getItemDetailsMap(String page,String item) {
		LinkedHashMap<String,String> enviMap = null;
		String xPath = "workflow/portal/workflow-"+page+"/"+item+"-list/"+item;
		enviMap = getChildrenMap(xPath);
		return enviMap;
	}
	public static void main(String[] args) {
		WorkflowPropertyReader reader = new WorkflowPropertyReader("dev");
		System.out.println(reader.getItemDetails("handle-error-count"));
	}

}
