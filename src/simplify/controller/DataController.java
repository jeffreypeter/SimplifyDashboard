package simplify.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import simplify.bean.DBClientBean;
import simplify.reports.DBClient;
import simplify.utils.CustomLogger;
import simplify.utils.property.DatabasePropertyReader;

/**
 * Servlet implementation class DataController
 */
public class DataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private DatabasePropertyReader reader = new DatabasePropertyReader();
    public DataController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: DataController - get"+className, className);
		String action=request.getParameter("action");
    	logger.info("action:: "+action, className);
    	if("report-debug".equals(action)) {
    		String data[] = action.split("-");
    		LinkedHashMap<String, String> enviMap = reader.getItemDetailsMap(data[1], "envi");
    		LinkedHashMap<String, String> reportMap = reader.getItemDetailsMap(data[1], data[0]);
    		request.setAttribute("reportMap", reportMap);
    		request.setAttribute("enviMap", enviMap);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/report-debug.jsp");
			dispatcher.forward(request, response);
    	} else {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("SimplifyDashboardController");
			dispatcher.forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("In doPost:: DataController", className);
		System.out.println("IN POST");
		String action = request.getParameter("action");
		String envi	= request.getParameter("envi");
		String msg="";
		logger.info("action:: "+action+" envi::"+envi, action);
		try {
			if("view".equals(action)) {
				String param = request.getParameter("data");
				String report = request.getParameter("report").trim();
				DBClient client = new DBClient(envi);
				DBClientBean bean = new DBClientBean();
				msg = client.viewReport(report,bean,param,envi);
				logger.info("report:: "+report+" param::"+param, className);
				request.setAttribute("msg", msg);
				request.setAttribute("param", param);
				request.setAttribute("report", report);
				request.setAttribute("envi", envi);
				if("success".equals(msg)) {
					request.setAttribute("reportData", bean);
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/report-view.jsp");
				dispatcher.forward(request, response);
			} else if ("print".equals(action)) {
				String param = request.getParameter("data");
				String report = request.getParameter("report").trim();
				DBClient client = new DBClient(envi);
				DBClientBean bean = new DBClientBean();
				logger.info("report:: " + report + " param::" + param, action);
				client.printReport(report, bean, param, envi);
				logger.info("Out: Print Report", className);
//				request.setAttribute("report", report);
//				request.setAttribute("workdir", reader.getWorkDir());
				RequestDispatcher dispatcher = request.getRequestDispatcher("pages/download.jsp?report="+report+"&workdir="+ reader.getWorkDir());
				dispatcher.forward(request, response);
			} else if ("refreshDashboard".equalsIgnoreCase(action)) {
				String item = request.getParameter("item");
				logger.info("item:: " + item+" -envi::"+envi, className);
				DBClient client = new DBClient(envi);
				JSONObject output = new JSONObject();			
				JSONObject outputStr = client.dashBoardRefresh(item);
				logger.info("outputStr:: " + outputStr, className);
				try {
					if(outputStr==null) {
						output.put("msg", "failed");
					} else {
						output.put("msg", "success");
						output.put("output", outputStr);
					}
				} catch(Exception e) {
					logger.info("IN Exception" + e.getLocalizedMessage(), className);								
				}
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(output.toString());
			} else if ("populateChart".equalsIgnoreCase(action)) {
				String item = request.getParameter("item");
				logger.info("item:: " + item, className);
				DBClient client = new DBClient("prod");
				JSONObject outputStr = client.populateChart(item);
				logger.info("outputStr:: " + outputStr, className);
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(outputStr.toString());
				
			}
			else if ("populateTable".equalsIgnoreCase(action)) {
				String item = request.getParameter("item");
				logger.info("item:: " + item, className);
				DBClient client = new DBClient("prod");
				JSONObject outputStr = client.populateTable(item,null,null,true);
				logger.info("outputStr:: " + outputStr, className);
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(outputStr.toString());
				
			}
		} catch(Exception e) {
			logger.error("IN DataController post:: "+e.getLocalizedMessage(), className);
		}
	} 

}
