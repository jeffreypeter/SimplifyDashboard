package simplify.utils.property;

import java.util.HashMap;
import java.util.Map;

import oracle.net.aso.q;

import org.python.modules.newmodule;

public class QueuePropertyReader extends XPropertyReader{

	private String envi = null;
	Map<String,String> mqpropertyMap = null;
	
	public QueuePropertyReader(){
		super();
	}
	
	public QueuePropertyReader(String envi){
		super();
		this.envi = envi;
	}
	
	public Map<String,String> getMQConnectionProperties() {
		String xPath = "mq/envi-list/connection[envi='"+envi.toUpperCase()+"']";
		this.mqpropertyMap = getChildMap(xPath);
		return mqpropertyMap;
	}
	
	public static void main(String args[]){
		
		System.out.println("IN MAIN");
		QueuePropertyReader queuePropertyReader = new QueuePropertyReader("DEV");
		Map<String,String>queueMap = queuePropertyReader.getMQConnectionProperties();
		
		for(String keyString : queueMap.keySet()) {
			System.out.println("Key :"+keyString+" :: Value:"+queueMap.get(keyString));
		}
	}
}
