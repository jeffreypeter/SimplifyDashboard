package simplify.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLoader {

	private static ApplicationContext getContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext("simplify-beans.xml");
		return context;
	}
	public static Object getBean(String id) {
		return getContext().getBean(id);		
	}
}
