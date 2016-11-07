package simplify.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import simplify.bean.TaskBean;
import simplify.client.bpd.BpdController;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;
import simplify.utils.property.DatabasePropertyReader;
import simplify.utils.property.WorkflowPropertyReader;

/**
 * Servlet implementation class BPMController
 */
public class BPMController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private WorkflowPropertyReader reader = new WorkflowPropertyReader();
	public BPMController() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("IN BPMController Get", className);
		String action= request.getParameter("action");
		logger.info("action:: "+action, className);
		if("workflow-lookup".equals(action)){
			String data[] = action.split("-"); 
			LinkedHashMap<String, String> enviMap = reader.getItemDetailsMap(data[1], "envi");
    		LinkedHashMap<String, String> lookUpMap = reader.getItemDetailsMap(data[1], data[1]);			
    		request.setAttribute("lookUpMap", lookUpMap);
    		request.setAttribute("enviMap", enviMap);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/workflow-lookup.jsp");
			dispatcher.forward(request, response);
		} else if ("workflow-filter".equals(action)) {
			String data[] = action.split("-");
			LinkedHashMap<String, String> enviMap = reader.getItemDetailsMap(data[1], "envi");				
			request.setAttribute("enviMap", enviMap);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/workflow-filter.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: BPMController - Post", className);
		String action = request.getParameter("action");
		logger.info("Action:: "+action, className);
		ArrayList<TaskBean> beanList = new ArrayList<TaskBean>();
		JSONObject result = null;
		try {
			if("search".equals(action)) {
				logger.info("IN:: Search action", className);
				String paramName = request.getParameter("paramName");
				String paramValue = request.getParameter("paramValue").trim();
				logger.info("paramName:: "+paramName+" paramValue::"+paramValue, className);		
				String envi = request.getParameter("envi");
				BpdController bpdController =  new BpdController(envi);
				result = bpdController.performSearch(paramName, paramValue);
				beanList = bpdController.parseResult(result);
				logger.info("beanList length: "+beanList, className);
				request.setAttribute("beanList", beanList);
				request.setAttribute("envi", envi);
				request.setAttribute("paramName", paramName);
				request.setAttribute("paramValue", paramValue);
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/workflow-view.jsp");
				dispatcher.forward(request, response);
				
			} else if("terminate".equals(action)){
				logger.info("IN:: Terminate action", className);
				String paramName = request.getParameter("paramName");
				String paramValue = request.getParameter("paramValue").trim();
				String envi = request.getParameter("envi");
				String instanceId = request.getParameter("instanceId");
				int iId = Integer.parseInt(instanceId);
				BpdController bpdController =  new BpdController(envi);
				bpdController.terminatePiid(iId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("ControllerMain?action=search&paramName="+paramName+"&paramValue="+paramValue);
				dispatcher.forward(request, response);	
			} else if ("delete".equals(action)) {
				String paramName = request.getParameter("paramName");
				String paramValue = request.getParameter("paramValue").trim();
				String envi = request.getParameter("envi");
				String instanceId = request.getParameter("instanceId");
				BpdController bpdController =  new BpdController(envi);
				bpdController.deletePiid(Integer.parseInt(instanceId));
//				RequestDispatcher dispatcher = request.getRequestDispatcher("ControllerMain?action=search&paramName="+paramName+"&paramValue="+paramValue);
//				dispatcher.forward(request, response);	
			} else if ("instanceDetails".equals(action)){
				logger.info("IN:: InstanceDetails action", className);
				String envi = request.getParameter("envi");
				String instanceId = request.getParameter("instanceId");
				BpdController bpdController =  new BpdController(envi);				
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    String op = bpdController.getBPDInstanceDetails(Integer.parseInt(instanceId)).toString();
			    logger.info("OUT:: InstanceDetails", className);
				response.getWriter().write(op);
			} else if ("assignTask".equals(action)) {
				logger.info("IN:: AssignTask action", className);
				String envi = request.getParameter("envi");
				String taskId = request.getParameter("taskId");
				String userId = request.getParameter("userId");
				BpdController bpdController =  new BpdController(envi);
				bpdController.assignTask(taskId, userId);
			}else if("logsearch".equals(action)) {
				logger.info("IN:: LogSearch action", className);
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/LogSearch.jsp");
				dispatcher.forward(request, response);
			} else if ("taskDetails".equals(action)) {
				String envi = request.getParameter("envi");
				String piid= request.getParameter("piid");
				logger.info("IN:: taskDetails action", className);
				logger.info("envi:: "+envi+" piid"+piid, className);
				BpdController bpdController =  new BpdController(envi);
				int tempPiid = Integer.parseInt(piid);
				JSONObject output=bpdController.getTaskDetails(tempPiid);
				logger.info("Out:: getTaskDetails", className);
				response.getWriter().write(output.toString());
			} else if ("triggerTimer".equals(action)) {
				logger.info("IN:: triggerTimer action", className);
				String envi = request.getParameter("envi");
				String instanceId= request.getParameter("instanceId");
				String tokenId = request.getParameter("tokenId");				
				logger.info("envi:: "+envi+" instanceId"+instanceId+" tokenId:: "+tokenId, className);
				BpdController bpdController =  new BpdController(envi);
				int tempInstanceId = Integer.parseInt(instanceId);
				JSONObject output=bpdController.triggerTimer(tempInstanceId,tokenId);
				logger.info("Out:: triggerTimer", className);
				response.getWriter().write(output.toString());
			} else if("filter".equalsIgnoreCase(action)) {
				logger.info("IN:: Filter action", className);				
				String envi = request.getParameter("envi");
				String data = request.getParameter("data");
				logger.info("envi:: "+envi+"data:: "+data, className);
				JSONObject param = new JSONObject(data);
				
				BpdController bpdController =  new BpdController(envi);
				JSONObject ouput = new JSONObject();
				JSONObject results = bpdController.runService("CMS@UniversalSearch", param);
				JSONObject taskList = results.getJSONObject("data").getJSONObject("data");
				ouput.put("data", taskList.getJSONObject("taskList"));
				ouput.put("envi", envi);
				ouput.put("status", "success");
				logger.info("Output:: "+taskList, className);
				request.setAttribute("output", ouput);
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/workflow-jview.jsp");
				dispatcher.forward(request, response);
				
			} else if("refreshDashboard".equalsIgnoreCase(action)) {
				logger.info("IN:: refreshDashboard action", className);				
				String envi = request.getParameter("envi");
				String item = request.getParameter("item");
				BpdController bpdController =  new BpdController(envi);
				JSONObject value = bpdController.getItemCount(item);
				
				JSONObject output = new JSONObject();
				output.put("msg", "success");
				output.put("output", value);
				logger.info("output:: "+output.toString(), className);
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(output.toString());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/SimplifyController");
				dispatcher.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("IN Exception:: "+e.getLocalizedMessage(), className);
		} finally {
			if("search".equals(action)) {
			}
		}
	}

}
