package simplify.controller;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import simplify.bean.User;
import simplify.utils.BeanLoader;
import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;
import simplify.utils.property.AdminPropertyReader;

@Controller
public class AccessController {
	CustomLogger logger = new CustomLogger();
	String className = this.getClass().getSimpleName();
	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login"); 
	}
	@RequestMapping(value="login/access",method=RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String access( HttpServletRequest request,HttpServletResponse response) {
		
		logger.info("IN:: Access Method", className);
		JSONObject res = new JSONObject();
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }   
			logger.info("inputStr:: "+sb, className);
			JSONObject input = new JSONObject(sb.toString());
			logger.info("username:: "+input.getString("user")+ " password:: "+input.getString("password"), className);
			User user = (User) BeanLoader.getBean("user");
			user.setPassword(input.getString("password"));
			user.setUserName(input.getString("user"));
			String role = AdminPropertyReader.authenticateUser(user);
			String resource = input.getString("resource");
					
			logger.info("Role:: "+role, className);
			if (role != null && !role.isEmpty()) {
				user.setPassword("");
				user.setRole(role);
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				if(authorizeUser(role,resource)) {
					res.put("msg", "success");
				} else {
					res.put("msg", "User does not have the privilage to access the request resource");
				}
				
			} else {
				res.put("msg", "Invalid Username/Password");
			}
		} catch(Exception e) {
			logger.error("Exception access:: "+ e.getLocalizedMessage(), className);
		}
		logger.info("res:: "+res.toString(), className);
		return res.toString();
	}
	public boolean authorizeUser(String role,String resource){
		logger.info("IN:: authorizeUser", className);
		HashMap<String,HashSet<String>> authorizationMap = SimplifyCache.getInstance().getAuthorizationMap();
		boolean authorizeFlag = false;
		if(authorizationMap.containsKey(resource)) {
			HashSet<String> accessSet = authorizationMap.get(resource);
			if(accessSet != null && !accessSet.isEmpty()) {
				if(accessSet.contains(role)) {					
					authorizeFlag = true;
				}
			}
		}	
		logger.info("OUT:: authorizeUser:: "+authorizeFlag, className);
		return authorizeFlag;
	}
}
