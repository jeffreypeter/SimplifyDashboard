package simplify.utils.property;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

public class XPropertyReader {
	
	protected static CombinedConfiguration config =null;
	
	static {
		createConfig();
	}
	private static void createConfig() {
		try {
			/** Simple Configuration Load 
			 * XMLConfiguration config = new XMLConfiguration(resource);
			 */
			
			/** For Combined Configuration loader 
			 * First Builder is required
			 * CombinedConfiguration object
			 * **/
			loadPropertyFile();
		} catch(Exception e) {
//			System.out.println("Exception:: XPropertyReader while Configuring - "+ e.getLocalizedMessage());
		}
			
	}
	public static void loadPropertyFile() throws ConfigurationException {
		String resource ="/simplify/props/simplify-configuration.xml";
		
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		builder.setDelimiter('^');
		builder.load(resource);
		
		config = builder.getConfiguration(true); 
		config.setExpressionEngine(new XPathExpressionEngine());			
		
		System.out.println("XPropertyReader:: Config Successful");
		
	}
	public static CombinedConfiguration getConfig() {
		return config; 
	}
	public static String getValue(String xPath) {
		return config.getString(xPath);		
	}
	
	public static LinkedHashMap<String,String> getChildMap(String xPath) {
		LinkedHashMap<String, String> childMap = new LinkedHashMap<String, String>();
		HierarchicalConfiguration child = config.configurationAt(xPath);
		Iterator<String> keyItr = child.getKeys();
		while(keyItr.hasNext()) {
			String key= keyItr.next();
			childMap.put(key, child.getString(key));
		}
		return childMap;
	}
	public static LinkedHashMap<String, String> getChildrenMap(String xPath) {
		LinkedHashMap<String, String> childMap = new LinkedHashMap<String, String>();
		List<HierarchicalConfiguration> children = config.configurationsAt(xPath);
		for (HierarchicalConfiguration child:children) {
			childMap.put(child.getString("key"), child.getString("value"));
		}
		return childMap;
	}
	/*public static LinkedHashMap<String,LinkedHashMap<String, String>> getComplexChildrenMap(String xPath) {
		LinkedHashMap<String,LinkedHashMap<String, String>> complexChilderMap = new LinkedHashMap<String,LinkedHashMap<String, String>>(); 
		List<HierarchicalConfiguration> children = config.configurationsAt(xPath);
		for(HierarchicalConfiguration child: children) {
			Iterator keysItr = child.getKeys();
			LinkedHashMap<String, String> childMap = new LinkedHashMap<String, String>();
			while(keysItr.hasNext()) {
				String key = keysItr.next();
				childMap.put(key, child.)
			}
			
		}
		System.out.println(children.size());
		return complexChilderMap;
	}*/
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		
//		System.out.println("Map::" +getChildMap("d"));
//		System.out.println("total-app-count:: "+config.getString("filename"));
		System.out.println(getValue("database/work-dir"));
		
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		
	    String s = bufferRead.readLine();
	    loadPropertyFile();
	    System.out.println(s);
	    
	}
}
