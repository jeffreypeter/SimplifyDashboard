package simplify.utils.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import simplify.bean.User;

public class AdminPropertyReader extends XPropertyReader {
	
	public AdminPropertyReader () {
		
	}
	public static HashMap<String , HashSet<String>> loadAuthorizationDtls() {
		String xPath = "authorization/resource-list/resource/url";
		HashMap<String , HashSet<String>> roleRestrictionMap = new LinkedHashMap<String, HashSet<String>>();
		List<Object> roleLst = config.getList(xPath);
		
		for(Object role: roleLst) {
			List<Object> roleRestrictionLst = config.getList("authorization/resource-list/resource[url='"+(String)role+"']/access/role");
			HashSet<String> roleRestrictionFinalLst = new HashSet<String>();
			for(Object roleRestriction:roleRestrictionLst) {
				roleRestrictionFinalLst.add((String)roleRestriction);				
			}
			roleRestrictionMap.put((String) role, roleRestrictionFinalLst);
		}
		return roleRestrictionMap;		
	}	
	public static String authenticateUser(User user) {
		String passwordXPath = "authentication/user-list/user[id='"+user.getUserName()+"']/password";
		String password = getValue(passwordXPath);
		String role = "";
		if(user.getPassword().equals(password)) {
			String roleXPath =  "authentication/user-list/user[id='"+user.getUserName()+"']/role";		
			role = getValue(roleXPath);			
		} 
		return role;		
		
	}
	public static void main(String[] args) {
//		System.out.println("Password:: "+authenticateUser("admisn"));
		User user = new User();
		user.setUserName("admin");
		authenticateUser(user);
		System.out.println(user.toString());
	}
}
