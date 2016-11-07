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

import simplify.bean.DBClientBean;
import simplify.reports.DBClient;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;
import simplify.utils.property.DatabasePropertyReader;

/**
 * Servlet implementation class AnalyticsController
 */
public class AnalyticsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private DatabasePropertyReader reader = new DatabasePropertyReader();
    public AnalyticsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("IN:: AnalyticsController - GET", className);
		String action=request.getParameter("action");
    	logger.info("action:: "+action, className);
		if("report-mobility".equalsIgnoreCase(action)) {
			String data[] = action.split("-");
			LinkedHashMap<String, String> enviMap = reader.getItemDetailsMap(data[1], "envi");
			LinkedHashMap<String, String> reportMap = reader.getItemDetailsMap(data[1], "report");
			logger.info("reportMap:: "+reportMap,className);
			request.setAttribute("reportMap", reportMap);
			request.setAttribute("enviMap", enviMap);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/report-mobility.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: AnalyticsController - POST", className);
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
			logger.info("report:: "+report+" param::"+param, className);
			msg = client.viewReport(report,bean,param,envi);
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/download.jsp?report="+report+"&workdir="+ reader.getWorkDir());
			dispatcher.forward(request, response);
		}
		}catch (Exception e) {
			logger.error("IN DataController ::"+ e.getLocalizedMessage(), className);
		}
	}

}
