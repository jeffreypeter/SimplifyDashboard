package simplify.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONException;
import org.json.JSONObject;

import simplify.bean.DBClientBean;
import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;
import simplify.utils.property.DatabasePropertyReader;

public class DBClient {
	
	private DatabasePropertyReader reader = null;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	HashMap<String,String> configMap = null;
	public DBClient(String envi) {
		reader = new DatabasePropertyReader(envi);
		configMap = reader.getConnectionDetails();
	}	
	private Connection getDBConnection() throws Exception{	
	logger.info("in  getDBConnection", className);
	Connection conn = null;	
		try {
			Class.forName(configMap.get("driver"));
			conn = DriverManager.getConnection(configMap.get("url"),configMap.get("user"),configMap.get("password"));
			logger.info("conn is successful", className);
		}catch (Exception e) {
			logger.error("IN getDBConnection exception :"+e.getLocalizedMessage(), className);
			throw e;
		} 
		return conn;
	}
	public void setDBBean(DBClientBean bean,ResultSet rs,ResultSetMetaData rsMD) throws SQLException {
		int colCount= rsMD.getColumnCount();			
		ArrayList<String> columnNameList = bean.getColumnNameList();
		ArrayList<LinkedHashMap<String, String>> dataList = bean.getDataList(); 
		for (int i=1;i<=colCount;i++) {
			columnNameList.add(rsMD.getColumnLabel(i));				
		}
		logger.info("CollistSize:: "+columnNameList.size(), className);
		while(rs.next()) {				
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (int i=0;i<colCount;i++) {
				map.put(columnNameList.get(i),rs.getString(columnNameList.get(i))); // Mismatch between Result Set and Hashmap indexes				
			}
			
			dataList.add(map);
		}		
	}
	public void printDBBean(DBClientBean bean,ResultSet rs,ResultSetMetaData rsMD,String report) throws SQLException, IOException {
		logger.info("IN:: printDBBean", className);
		int colCount= rsMD.getColumnCount();
		int rowCount=0;
//		ArrayList<String> columnNameList = bean.getColumnNameList();
//		ArrayList<LinkedHashMap<String, String>> dataList = bean.getDataList();
		
		HSSFWorkbook  book = new HSSFWorkbook();
		HSSFSheet  sheet = book.createSheet(report);	
		HSSFFont font = book.createFont();
		font.setBold(true);		
		HSSFCellStyle style = book.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font);
//		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
//		int totalCols =sheet.getRow(0).getPhysicalNumberOfCells();		
		HSSFRow  rowDefault = sheet.createRow(rowCount);
		for (int i=0;i<colCount;i++) {
			HSSFCell cell = rowDefault.createCell(i);
			cell.setCellValue(rsMD.getColumnLabel(i+1));
			cell.setCellStyle(style);
		}

		while(rs.next()) {		
			HSSFRow row = sheet.createRow(++rowCount);
			for (int i=0;i<colCount;i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(rs.getString(i+1));
			}				
		}
		for (int i=0;i<colCount;i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream file = new FileOutputStream(reader.getWorkDir()+report+".xls");
		book.write(file);
		file.close();
		book.close();
	}
 
	public PreparedStatement prepareQuery(String param,String queryProps,Connection conn) throws Exception{		
		PreparedStatement pdSt = null;
		logger.info("IN prepareQuery :: "+queryProps, className);// Next split the query		
		String[] queryInit = queryProps.split("--"); // Tail Statements of Query
		String queryStr = queryInit[0];	
		ArrayList<String> queryList = new ArrayList<String>(Arrays.asList(queryStr.split(";")));
		StringBuilder finalQuery = new StringBuilder();
		ArrayList<String> paramList = null;
		/**
		 * Adding the conditions only if the parameter is available
		 */
		if(param != null && !param.isEmpty()) {
			paramList = new ArrayList<String>(Arrays.asList(param.split(";")));
			for(int i=0;i<paramList.size();i++) {
//				logger.info("Params:: "+paramList.get(i), className);
				/**
				 * i+1 as size of ArrayList starts from 1 
				 */
				if((i+1)<=queryList.size() && !"*".equals(paramList.get(i))) { // Only if parameter is available conditions will be attached to the query 
					logger.info("Params:: "+paramList.get(i), className);
					finalQuery.append(queryList.get(i));					
				}
			}
		} else {
			logger.info("No Parameter", className);
			finalQuery.append(queryList.get(0)); // As query without parameters won't be seperated by ' ; ' So I am taking the first value of the list
		}
					
		if(queryInit.length==2) {
			finalQuery.append( queryInit[1]);
		}
		logger.info("FinalQuery:: "+finalQuery.toString(), className);
		pdSt = conn.prepareStatement(finalQuery.toString());
		/**
		 * qryParameterIndex - For setting parameter count in the prepared statement and index starts from 1
		 * Setting parameter depends on the qryParameterIndex 
		 */
		int qryParameterIndex = 1;
		if(paramList!=null) {
			for(int i=0;i<paramList.size();i++) { // To syn setString and get functions
				if((i+1)<=queryList.size() && !"*".equals(paramList.get(i)) ) {
					logger.info("Params:: "+paramList.get(i), className);
					logger.info("qryParameterIndex:: "+qryParameterIndex, className);
					pdSt.setString(qryParameterIndex, paramList.get(i).trim());
					
					qryParameterIndex++;
				}
			}
		}
		return pdSt;
	}
	public void printReport(String report,DBClientBean bean,String param,String envi) throws Exception {
		logger.info("IN:: printReport", className);
		Connection conn =  getDBConnection();
		PreparedStatement pdSt=null;
		String msg=null;
		try {
			String queryProps = reader.getQuery(report).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
			pdSt = prepareQuery(param,queryProps,conn);		
			ResultSet rs = pdSt.executeQuery();
			ResultSetMetaData rsMD = rs.getMetaData();
			printDBBean(bean,rs,rsMD,report);
		} catch (SQLException e) {
			logger.error("IN Exception : DBclient: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}catch (Exception e) {
			logger.error("IN Exception : DBclient: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}
		finally {
			try {
				conn.close();				
			} catch (SQLException e) {
				logger.error("IN Exception : DBclient: Not POSSIBLE"+e.getLocalizedMessage(), className);
			}
		}
	}
	public String viewReport(String report,DBClientBean bean,String param,String envi) throws Exception  {
		logger.info("IN:: viewReport", className);
		Connection conn =  getDBConnection();
		
		PreparedStatement pdSt=null;
		String msg=null;
		try {
								
			String queryProps = reader.getQuery(report).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
			pdSt = prepareQuery(param,queryProps,conn);		
			ResultSet rs = pdSt.executeQuery();
			ResultSetMetaData rsMD = rs.getMetaData();
			setDBBean(bean,rs,rsMD);
			msg = "success";
		} catch (SQLException e) {
			logger.error("IN Exception : DBclient: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}catch (Exception e) {
			logger.error("IN Exception : DBclient: "+e.getLocalizedMessage(), className);
			msg=e.getLocalizedMessage();			
		}
		finally {
			try {
				conn.close();				
			} catch (SQLException e) {
				logger.error("IN Exception : DBclient: Not POSSIBLE"+e.getLocalizedMessage(), className);
			}
		}
		return msg;
	}
	public JSONObject dashBoardRefresh(String item) throws Exception  {
		logger.info("IN: dashBoardRefresh", className);
		Connection conn =  getDBConnection();
		PreparedStatement pdSt=null;
		String outputStr=null;
		JSONObject value = new JSONObject();
		try {
			String queryProps = reader.getQuery(item).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
			logger.info("queryProps:: "+queryProps, className);
			pdSt = conn.prepareStatement(queryProps);
			ResultSet rs = pdSt.executeQuery();
			while(rs.next()) {
				outputStr = rs.getString(1);
			}
			SimplifyCache instance = SimplifyCache.getInstance();
			value.put("key", item);
			value.put("value", outputStr);
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm a");
			Date date = new Date();
			value.put("date", dateFormat.format(date));
			instance.addInfo(item, value);
			
		} catch (Exception e) {
			logger.error("IN Exception: "+e.getLocalizedMessage(), className);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("IN Exception: "+e.getLocalizedMessage(), className);
			}
		}
		return value;		
	}
	public JSONObject populateChart(String item) throws Exception  {
		logger.info("IN: populateChart", className);
		Connection conn =  getDBConnection();
		PreparedStatement pdSt=null;
		String outputStr=null;
		JSONObject value = new JSONObject();
		try {
			String queryProps = reader.getQuery(item).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
			logger.info("queryProps:: "+queryProps, className);
			pdSt = conn.prepareStatement(queryProps);
			ResultSet rs = pdSt.executeQuery();
			ResultSetMetaData rsMD = rs.getMetaData();
			JSONObject output = setJSON(rs, rsMD);
			SimplifyCache instance = SimplifyCache.getInstance();
			value.put("status", "success");
			value.put("key", item);
			value.put("value", output);
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm a");
			Date date = new Date();
			value.put("date", dateFormat.format(date));
			instance.addChartInfo(item, value);
			logger.info("InstanceChartMap :: "+instance.getChartMap(), className);
		} catch (Exception e) {
			logger.error("IN Exception: "+e.getLocalizedMessage(), className);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("IN Exception: "+e.getLocalizedMessage(), className);
			}
		}
		return value;		
	}
	public JSONObject populateTable(String item,String type,String inputParamter,boolean durable) throws Exception  {
		logger.info("IN: populateTable", className);
		Connection conn =  getDBConnection();
		PreparedStatement pdSt=null;
		JSONObject value = new JSONObject();
		ResultSet rs = null;
		try {
			String queryFinal = "";
			if(type == null) {
				queryFinal = reader.getQuery(item).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
				logger.info("queryProps:: "+queryFinal, className);
			} else {
				queryFinal = setMultiValuesQuery(item,inputParamter);
			}
			logger.info("queryFinal:: "+queryFinal, className);
			pdSt = conn.prepareStatement(queryFinal);
			rs = pdSt.executeQuery();
			ResultSetMetaData rsMD = rs.getMetaData();
			JSONObject output = setJSONValues(rs, rsMD);
			
			value.put("key", item);
			value.put("value", output);
			value.put("status", "success");
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm a");
			Date date = new Date();
			value.put("date", dateFormat.format(date));
			if(durable) {
				SimplifyCache instance = SimplifyCache.getInstance();
				instance.addTableInfo(item, value);				
			}
			
		} catch (Exception e) {
			logger.error("IN Exception: "+e.getLocalizedMessage(), className);
			e.printStackTrace();
		} finally {
			try {
				pdSt.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				logger.error("IN Exception: "+e.getLocalizedMessage(), className);
			}
		}
		return value;		
	}
	public JSONObject setJSON(ResultSet rs,ResultSetMetaData rsMD) throws SQLException, JSONException {
		int colCount= rsMD.getColumnCount();			
		JSONObject output = new JSONObject();
		ArrayList<JSONObject> recordLst = new ArrayList<JSONObject>();
		ArrayList<String> columnNameLst = new ArrayList<String>();
		for (int i=1;i<=colCount;i++) {
			columnNameLst.add(rsMD.getColumnLabel(i));				
		}
		logger.info("CollistSize:: "+columnNameLst.size(), className);
		while(rs.next()) {				
			JSONObject record = new JSONObject();
			for (int i=0;i<colCount;i++) {
				record.put(columnNameLst.get(i),rs.getString(columnNameLst.get(i))); // Mismatch between Result Set and Hashmap indexes 
			}
			recordLst.add(record);
		}
		output.put("columnNameLst",columnNameLst);
		output.put("recordLst",recordLst);
		return output;
	}
	public JSONObject setJSONValues(ResultSet rs,ResultSetMetaData rsMD) throws SQLException, JSONException {
		int colCount= rsMD.getColumnCount();			
		JSONObject output = new JSONObject();
		ArrayList<ArrayList<String>> recordLst = new ArrayList<ArrayList<String>>();
		ArrayList<String> columnNameLst = new ArrayList<String>();
		for (int i=1;i<=colCount;i++) {
			columnNameLst.add(rsMD.getColumnLabel(i));				
		}
		logger.info("CollistSize:: "+columnNameLst.size(), className);
		while(rs.next()) {				
			ArrayList<String> rowLst = new ArrayList<String>();
			for (int i=0;i<colCount;i++) {
				rowLst.add(rs.getString(columnNameLst.get(i)));
			}
			recordLst.add(rowLst);
		}
		output.put("columnNameLst",columnNameLst);
		output.put("recordLst",recordLst);
		return output;
	}
	public String setMultiValuesQuery(String item, String value) {
		logger.info("IN:: setMultiValuesQuery", className);
		String query = reader.getQuery(item).replaceAll("%DB_SCHEMA%", configMap.get("schema"));
		String queryFinal = query.replaceFirst("%INPUT_PARAMETER%",value);
		return queryFinal;
		
	}
}
 