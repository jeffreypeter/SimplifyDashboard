package simplify.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONArray;
import simplify.utils.property.DatabasePropertyReader;

public class DataHandler {

	CustomLogger logger = new CustomLogger();
	String className = this.getClass().getSimpleName();
	
	
	public void generateXLS(JSONArray colName, JSONArray recordLst,String reportName, String outputDir)  throws Exception {
		logger.info("IN:: generateXLS", className);
		int colCount= colName.length();
		int rowCount=0;
		HSSFWorkbook  book = new HSSFWorkbook();
		HSSFSheet  sheet = book.createSheet(reportName);	
		HSSFFont font = book.createFont();
		font.setBold(true);		
		HSSFCellStyle style = book.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		HSSFRow  rowDefault = sheet.createRow(rowCount);
		for (int i=0;i<colCount;i++) {
			HSSFCell cell = rowDefault.createCell(i);
			cell.setCellValue(colName.getString(i));
			cell.setCellStyle(style);
		}

		for(int i=0;i<recordLst.length();i++) {
			JSONArray record =  recordLst.getJSONArray(i);
			HSSFRow row = sheet.createRow(++rowCount);
			for (int j=0;j<record.length();j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(record.getString(j));
			}				
		}
		for (int i=0;i<colCount;i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream file = new FileOutputStream(outputDir);
		book.write(file);
		file.close();
		book.close();
		logger.info("OUT:: generateXLS", className);
	}
	
}
