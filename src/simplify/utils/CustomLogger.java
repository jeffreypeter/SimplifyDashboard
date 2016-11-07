package simplify.utils;

import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class CustomLogger {
		private Logger logger = null;
		
		static {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream resourceStream = loader.getResourceAsStream("simplify/props/log4j.properties");
			PropertyConfigurator.configure(resourceStream);
			System.out.println("CustomLogger Initiated");
		}
		public void info(String message,String className) {
			logger = Logger.getLogger(className);
			logger.info(message);
		}
		public void error(String message,String className) {
			logger = Logger.getLogger(className);
			logger.error(message);
		}
}
