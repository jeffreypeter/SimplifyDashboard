package simplify.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class PropertyReader {
	
	private CustomLogger logger = new CustomLogger();
	private String classname = this.getClass().getSimpleName();
	private Properties prop;
	public PropertyReader() {
		this.prop = new Properties();
		String resourceName = "simplify/props/enviprops.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader.getResourceAsStream(resourceName);		
		try {			
			prop.load(resourceStream);			
		} catch (FileNotFoundException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		} catch (IOException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		}
	}
	public PropertyReader(String file) {
		this.prop = new Properties();
		String resourceName = "simplify/props/"+file+".properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader.getResourceAsStream(resourceName);		
		try {			
			prop.load(resourceStream);			
		} catch (FileNotFoundException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		} catch (IOException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		}
	}
	public String getValue(String file,String key) {
		String resourceName = "simplify/props/"+file+".properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader.getResourceAsStream(resourceName);
		Properties currentProp = new Properties();
		try {			
			currentProp.load(resourceStream);
		} catch (FileNotFoundException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		} catch (IOException e) {
			logger.info("IN:: PropertiesReader Exception"+e.getLocalizedMessage(),"PropertyReader");
		}
		return currentProp.getProperty(key);
	}
	public String getValue(String key) {
		return prop.getProperty(key);
	}
	/*public void loadPropertyFile(HashMap<String,String> propsMap,String[] keys) {		
			logger.info("Property File Loaded","PropertyReader");
			for (String key: keys) {
				propsMap.put(key, prop.getProperty(key));
			}
	}*/
}
