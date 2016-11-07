package simplify.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;
import simplify.utils.property.AdminPropertyReader;
import simplify.utils.property.DatabasePropertyReader;

public class SimplifyInitializer implements ServletContextListener {

	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private AdminPropertyReader reader = new AdminPropertyReader();
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
//		System.out.println("Simplify Stopped");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Simplify Initiated");
		logger.info("IN:: contextInitialized", className);
		SimplifyCache cache = SimplifyCache.getInstance();
		cache.loadAuthorizationDtls();
	}

	
}
