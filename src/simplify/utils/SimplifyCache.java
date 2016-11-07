package simplify.utils;

import java.util.*;

import org.json.*;

import simplify.utils.property.AdminPropertyReader;

public class SimplifyCache {
	
	private static SimplifyCache instance;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private HashMap<String,JSONObject> dashboardInfo ;
	private HashMap<String,JSONObject> chartInfo ;
	private HashMap<String,Object> analytics;
	private HashMap<String,String> errorReport;
	private HashMap<String, ArrayList<String>> roleRestriction;
	private HashMap<String, JSONObject> tableInfo ;
	private HashMap<String,HashSet<String>> authorizationMap;
	private SimplifyCache() {
		dashboardInfo = new HashMap<String, JSONObject>();
		chartInfo = new HashMap<String, JSONObject>();
		analytics =new HashMap<String, Object>();
		errorReport = new HashMap<String, String>();
		roleRestriction = new HashMap<String, ArrayList<String>>();
		tableInfo = new HashMap<String, JSONObject>();
		setAuthorizationMap(new  HashMap<String, HashSet<String>>());
	}
	public void setRoleRestriction(HashMap<String, ArrayList<String>> roleRestriction) {
		this.roleRestriction = roleRestriction;
	}
	public HashMap<String, ArrayList<String>> getRoleRestriction() { 
		return roleRestriction;
	}
	public static SimplifyCache getInstance() {
		
		if(instance == null ) {
			instance = new SimplifyCache();
		}
		return instance;
	}
	
	public JSONObject getInfo(String key) {
		logger.info("IN:: getInfo - "+key,className);
		return dashboardInfo.get(key);
		
	}
	public JSONObject getChartInfo(String key) {
		logger.info("IN:: getChartInfo - "+key,className);
		return chartInfo.get(key);
		
	}
	public JSONObject getTableInfo(String key) {
		logger.info("IN:: getTableInfo - "+key,className);
		return tableInfo.get(key);
		
	}
	
	public void addInfo(String key, JSONObject value) {		
		logger.info("IN:: addInfo - "+ key, className);
		dashboardInfo.put(key, value);
	}
	public void addChartInfo(String key, JSONObject value) {		
		logger.info("IN:: addChartInfo - "+ key, className);
		chartInfo.put(key, value);
	}
	public void addTableInfo(String key, JSONObject value) {		
		logger.info("IN:: addTableInfo - "+ key, className);
		tableInfo.put(key, value);
	}
	public HashMap<String, JSONObject>getDashboardMap() {
		return dashboardInfo;
	}
	public HashMap<String, JSONObject>getChartMap() {
		return chartInfo;
	}
	public HashMap<String, JSONObject>getTableInfoMap() {
		return tableInfo;
	}
	
	public void loadAuthorizationDtls(){
		authorizationMap = AdminPropertyReader.loadAuthorizationDtls();
	}
	public HashMap<String,HashSet<String>> getAuthorizationMap() {
		return authorizationMap;
	}
	public void setAuthorizationMap(HashMap<String,HashSet<String>> authorizationMap) {
		this.authorizationMap = authorizationMap;
	}
}
