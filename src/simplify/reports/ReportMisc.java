package simplify.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simplify.bean.DBClientBean;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;



import bpm.rest.client.BPMClientException;
import bpm.rest.client.authentication.AuthenticationTokenHandlerException;


public class ReportMisc {
	private static PropertyReader reader = new PropertyReader();
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	/*private JSONArray getItems(String envi,String serviceName,JSONObject input) throws BPMClientException, AuthenticationTokenHandlerException, JSONException {
		BpdController bpdController = new BpdController(envi);
		
		JSONObject output = new JSONObject();
		output = bpdController.runService(serviceName, input);			
		JSONArray items = output.getJSONObject("data").getJSONObject("data").getJSONObject("InstanceDetails").getJSONArray("items");
		logger.info("items:: "+items.toString(), className);
		return items;
	}*/
	public ArrayList<String> parseItems(JSONArray items) throws JSONException {
		ArrayList<String> applst = new ArrayList<String>();
		for(int i=0;i<items.length();i++) {
			JSONObject element = items.getJSONObject(i);
			applst.add(element.getString("applicationNo"));
		}
		return applst;
	}
	public ResultSet executeListParam(Connection conn,String envi,String report,ArrayList<String> list) throws SQLException {
		ResultSet rs = null;
		String listString ="'"+StringUtils.join( list.toArray(),',').replaceAll(",", "','")+"'";
		String queryProps = reader.getValue(report).replaceAll("%DB_SCHEMA%", reader.getValue("schema_"+envi));
		
		logger.info("listString:: "+listString, className);
		String finalQry = queryProps.replaceAll("@",listString);
		logger.info("finalQry:: "+finalQry, className);
		PreparedStatement ps = conn.prepareStatement(finalQry);		
		rs = ps.executeQuery();
		logger.info("Query Excecuted", className);
		return rs;
	}
	/*public void printReportMisc(String report,DBClientBean bean,String param,String envi) {
		logger.info("IN: printReportMisc ", className);
		String msg="";
		DBClient dbClient = new DBClient(envi);
		Connection conn = dbClient.getNonXAConnection();
		try {
			if("underwriting".equalsIgnoreCase(report)) {
				JSONObject input = new JSONObject();
				input.put("taskName", "Suspend");
				JSONArray items = getItems(envi,"TEBTNB@GetTaskInstances",input);
				ArrayList<String> applst = parseItems(items);			
				logger.info("applst:: "+applst, className);
				ResultSet rs = executeListParam(conn, envi, report, applst);
				ResultSetMetaData rsMD = rs.getMetaData();
				dbClient.printDBBean(bean, rs, rsMD,report);
			} else if ("requirementawaitedadditional".equalsIgnoreCase(report)) {
				HashSet<String> appSet = new HashSet<String>();
				String queryProps = reader.getValue(report+"_ini").replaceAll("%DB_SCHEMA%", reader.getValue("schema_"+envi));
				logger.info("queryProps:: "+queryProps, className);
				PreparedStatement ps = conn.prepareStatement(queryProps);
				ResultSet rsIni = ps.executeQuery();
				while(rsIni.next()) {
					appSet.add(rsIni.getString(1));
				}
				JSONObject input = new JSONObject();
				input.put("taskName", "Wait for Document");
				JSONArray items = getItems(envi,"TEBTNB@GetTaskInstances",input);
				ArrayList<String> applst = parseItems(items);			
				logger.info("applst:: "+applst, className);
				appSet.addAll(applst);
				logger.info("appSet:: "+appSet, className);
				ResultSet rs = executeListParam(conn, envi, report+"_fin", new ArrayList<String>(appSet));
				ResultSetMetaData rsMD = rs.getMetaData();
				dbClient.printDBBean(bean, rs, rsMD,report);				
			}				
			msg="success";
			logger.info("OUT:: printReportMisc", className);
		} catch (Exception e) {
			logger.error("IN Exception : printReportMisc: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}
		finally {		
			try {
				conn.close();	
				logger.info("Connection closed successfully", className);
			} catch (SQLException e) {
				logger.error("IN Exception : generateReportMisc: Not POSSIBLE"+e.getLocalizedMessage(), className);
			}
		}
	}*/
	/*public String generateReportMisc(String report,DBClientBean bean,String param,String envi) {
		logger.info("IN: generateReportMisc ", className);
		String msg="";
		DBClient dbClient = new DBClient(envi);
		Connection conn = dbClient.getNonXAConnection();
		try {
			if("underwriting".equalsIgnoreCase(report)) {
				JSONObject input = new JSONObject();
				input.put("taskName", "Suspend");
				JSONArray items = getItems(envi,"TEBTNB@GetTaskInstances",input);
				ArrayList<String> applst = parseItems(items);			
				logger.info("applst:: "+applst, className);
				ResultSet rs = executeListParam(conn, envi, report, applst);
				ResultSetMetaData rsMD = rs.getMetaData();
				dbClient.setDBBean(bean, rs, rsMD);
			} else if ("requirementawaitedadditional".equalsIgnoreCase(report)) {
				HashSet<String> appSet = new HashSet<String>();
				String queryProps = reader.getValue(report+"_ini").replaceAll("%DB_SCHEMA%", reader.getValue("schema_"+envi));
				logger.info("queryProps:: "+queryProps, className);
				PreparedStatement ps = conn.prepareStatement(queryProps);
				ResultSet rsIni = ps.executeQuery();
				while(rsIni.next()) {
					appSet.add(rsIni.getString(1));
				}
				JSONObject input = new JSONObject();
				input.put("taskName", "Wait for Document");
				JSONArray items = getItems(envi,"TEBTNB@GetTaskInstances",input);
				ArrayList<String> applst = parseItems(items);			
				logger.info("applst:: "+applst, className);
				appSet.addAll(applst);
				logger.info("appSet:: "+appSet, className);
				ResultSet rs = executeListParam(conn, envi, report+"_fin", new ArrayList<String>(appSet));
				ResultSetMetaData rsMD = rs.getMetaData();
				dbClient.setDBBean(bean, rs, rsMD);				
			}				
			msg="success";
			logger.info("OUT:: generateReportMisc", className);
		} catch (Exception e) {
			logger.error("IN Exception : generateReportMisc: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}
		finally {		
			try {
				conn.close();	
				logger.info("Connection closed successfully", className);
			} catch (SQLException e) {
				logger.error("IN Exception : generateReportMisc: Not POSSIBLE"+e.getLocalizedMessage(), className);
			}
		}
		return msg;
	}*/
}
