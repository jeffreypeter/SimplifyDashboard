package simplify.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import org.apache.commons.io.FileUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simplify.bean.SearchResultBean;
import simplify.linux.SSHClient;
import simplify.linux.StringManipulator;
import simplify.utils.CustomException;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;


public class LinuxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SSHClient client = new SSHClient();
	private String className = this.getClass().getSimpleName();
	private CustomLogger logger = new CustomLogger();
	private PropertyReader reader = new PropertyReader();	
	public LinuxController() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info("In get action ::"+action,className);
		
		if("logsearch".equals(action)) {
			String eviStr = reader.getValue("envi");
			String teamStr = reader.getValue("team");
			String soaLogFilesStr = reader.getValue("soa_log_files");
			ArrayList<String> soaLogFilesLst = new ArrayList<String>(Arrays.asList(soaLogFilesStr.split(",")));
			ArrayList<String> eviList = new ArrayList<String>(Arrays.asList(eviStr.split(",")));
			ArrayList<String> teamList = new ArrayList<String>(Arrays.asList(teamStr.split(",")));
			request.setAttribute("eviList", eviList);
			request.setAttribute("teamList", teamList);
			request.setAttribute("soaLogFilesLst", soaLogFilesLst);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/log-search.jsp");
			dispatcher.forward(request, response);
			
		} else if("newTab".equals(action)) {
			logger.info("In newTab",className);
			String ipAddress = request.getRemoteAddr();  
			String envi=request.getParameter("envi");
			String param = request.getParameter("param");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;			
			String fileName = request.getParameter("fileName");
			logger.info("fileName:: "+fileName, className);
			SearchResultBean file = new SearchResultBean();
			file.setFileName(fileName);
			StringManipulator sm = new StringManipulator();			
			request.setAttribute("title", fileName);
			request.setAttribute("envi", envi);
			request.setAttribute("param", param);
			request.setAttribute("content", sm.getFile(file, workDir));
			logger.info("Content Recieved", className);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/viewFile.jsp");
			dispatcher.forward(request, response);
		}else {
			logger.info("IN Default", className);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SimplifyDashboardController");
			dispatcher.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: Post",className);	
		JSONObject output = new JSONObject();
		String action= request.getParameter("action");
		String ipAddress = request.getRemoteAddr();
		
		
		if("logsearch".equalsIgnoreCase(action)) {
			
			String param = request.getParameter("param").trim();
			String team = request.getParameter("team");
			String envi= team+"_"+request.getParameter("envi");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
			String filePattern = request.getParameter("filePattern").trim();
			String msg="";
			if(filePattern==null) {
				filePattern="";
			}	
			LinkedHashSet<SearchResultBean> fileSet = new LinkedHashSet<SearchResultBean>();			
			try {
				request.setAttribute("param",param);
				request.setAttribute("envi",envi);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				logger.info("envi::"+envi+" action:: "+action+" param:: "+param+" ipAddress:: "+ipAddress+"workDir:: "+workDir+" filePattern:: "+filePattern,className);
				FileUtils.deleteDirectory(new File(workDir));
				if("202.144.80.123".equalsIgnoreCase(ipAddress)) {				
					throw new CustomException("Please disable the Proxy");
				}
				
				msg=client.searchParam(fileSet,param, envi,team,ipAddress,filePattern);
				logger.info("OUT:: searchParam", className);	
								
			} 
			catch (CustomException e) {
				logger.error(e.getMessage(),className);
				msg=e.getMessage();
			} catch (Exception e) {
				msg=e.getMessage();
				logger.error("Exception :: "+e.getLocalizedMessage(),className);
			} finally {				
				logger.info("msg :: "+msg,className);
				request.setAttribute("msg",msg);
				request.setAttribute("workdir",workDir);
				request.setAttribute("fileSet", fileSet);
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/LogSearch.jsp");
				dispatcher.forward(request, response);
			}
		} else if("logParse".equals(action)) {		
			String envi= request.getParameter("envi");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
			logger.info("In logParse", className);
			String fileReq = request.getParameter("file");
			String depth = request.getParameter("depth");
			SearchResultBean bean = new SearchResultBean();
			String data[] = fileReq.split(";");
			bean.setFileName(data[0]);
			String lineNos = data[1].substring(data[1].indexOf('[')+1, data[1].indexOf(']'));			
			bean.setLineNo(new LinkedHashSet<String>(Arrays.asList(lineNos.split(","))));
			StringManipulator sm =  new StringManipulator();			
			sm.getThreadId(bean, workDir,depth);
			logger.info("OUT:: getThreadId", className);
			logger.info(bean.getFileName()+bean.getThreadIds(),className);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try {
				JSONArray threadList = new JSONArray(bean.getThreadIds());
				if(!bean.getThreadIds().isEmpty()) {
					output.put("status", "success");
					output.put("filename",bean.getFileName());
					output.put("threadlist",threadList);
				} else {
					logger.info("Thead Size ::"+bean.getThreadIds().size(), className);
					output.put("filename",bean.getFileName());
					output.put("status", "fail");
					output.put("errormsg","No thread Container found for "+bean.getFileName());
				}
			} catch (JSONException e) {
				logger.error("In Exception:: "+e.getLocalizedMessage(), className);
			}
			response.getWriter().write(output.toString());
			
		}
		else if("parseThread".equals(action)) {
			String envi= request.getParameter("envi");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
			logger.info("In parseThread", className);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String ipData = request.getParameter("ipdata");
			logger.info("data:: "+ipData, className);
			String[] data = ipData.split(";");
			SearchResultBean file = new SearchResultBean();
			file.setFileName(data[0]);
			LinkedHashSet<String> tempList =new LinkedHashSet<String>();
			tempList.add(data[1]);
			file.setThreadIds(tempList);
			StringManipulator sm =  new StringManipulator();
			try {
				output.put("status", "success");
				output.put("threadId", data[1]);
				output.put("data",sm.getThreadContent(file, workDir));
				logger.info("OUT:: getThreadContent", className);
			} catch (JSONException e) {
				logger.error("In Exception:: "+e.getLocalizedMessage(), className);
			}
			response.getWriter().write(output.toString());
		} else if ("openLog".equals(action)) {
			logger.info("In openLog", className);
			String envi= request.getParameter("envi");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
			String ipData = request.getParameter("ipdata");
			logger.info("data:: "+ipData+" workDir::"+workDir, className);
			SearchResultBean file = new SearchResultBean();
			file.setFileName(ipData);
			response.setContentType( "text/plain;charset=UTF-8" );
			response.setHeader( "Content-Disposition", "attachment;filename="+ipData+".txt" );
			StringManipulator sm = new StringManipulator();			
			PrintWriter out = response.getWriter();
	        try {
	            out.println(sm.getFile(file, workDir));
	        } finally {            
	            out.close();
	        }
		} else if ("picklogs".equals(action)) {
			logger.info("In picklogs", className);
			String team = request.getParameter("team");
			String envi= team+"_"+request.getParameter("envi");
			String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
			String filePattern = request.getParameter("filePattern").trim();
			String msg="";
			request.setAttribute("envi",envi);
			request.setAttribute("param",filePattern);
			logger.info("team:: "+team+" envi::"+envi+" filePattern::"+filePattern, className);
			try {
				FileUtils.deleteDirectory(new File(workDir));
				if("202.144.80.123".equalsIgnoreCase(ipAddress)) {				
					throw new CustomException("Please disable the Proxy");
				}
				LinkedHashSet<SearchResultBean> fileSet = new LinkedHashSet<SearchResultBean>();
				msg = client.pickLogs(fileSet,envi,team,ipAddress,filePattern);
				if(msg.equals("success")) {
					request.setAttribute("fileSet", fileSet);
				}
				
			} catch (Exception e) {
				logger.error("In Exception:: "+e.getLocalizedMessage(), className);
				msg=e.getLocalizedMessage();
			} finally {
				logger.info("OUT:: pickLogs - Msg:: "+msg, className);
				request.setAttribute("msg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/PickLogs.jsp");
				dispatcher.forward(request, response);
			}
			
		}
	}


}
