package simplify.interceptor;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import simplify.bean.User;
import simplify.utils.BeanLoader;
import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;
public class AuthenticationInterceptor extends HandlerInterceptorAdapter  {
	
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		CustomLogger logger = new CustomLogger();
		String className = this.getClass().getSimpleName();
		logger.info("IN:: preHandle", className);
		System.out.println("IN:: preHandle");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		System.out.println("Path:: "+request.getContextPath());
		StringBuffer url = request.getRequestURL();
		System.out.println("Url:: "+url);
		String placeHolder = "/action.do/";
		String resource=url.substring(url.lastIndexOf(placeHolder) + placeHolder.length());
		System.out.println("resource:: "+ resource);
		HashMap<String,HashSet<String>> authorizationMap = SimplifyCache.getInstance().getAuthorizationMap();
		boolean redirectFlag = true;
		if(authorizationMap.containsKey(resource)) {
			if(user !=null) {
				String role = user.getRole();
				System.out.println("role:: "+role);
				HashSet<String> accessSet = authorizationMap.get(resource);
				System.out.println("accessSet:: "+accessSet);
				if(accessSet != null && !accessSet.isEmpty()) {
					if(accessSet.contains(role)) {
						System.out.println("Access Granted");
						redirectFlag = false;
					} 
				} 
			} else {
				user = (User) BeanLoader.getBean("user");
			}
			logger.info("redirectFlag:: "+redirectFlag, className);
			if(redirectFlag) {
				RequestDispatcher dis = request.getRequestDispatcher("login");
				request.setAttribute("redirect", resource);
				session.setAttribute("redirect", resource);
				dis.forward(request, response);
			}
		}
		
        return true;
    }

}
