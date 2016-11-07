package simplify.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import simplify.linux.ScriptExecutor;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;

/**
 * Servlet implementation class WasController
 */
public class WasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private PropertyReader reader = new PropertyReader();
	
    public WasController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info("IN WasController GET:: "+action, className);
		if("qdepth".equalsIgnoreCase(action)) {
			String eviStr = reader.getValue("envi_qdepth");
			ArrayList<String> eviList = new ArrayList<String>(Arrays.asList(eviStr.split(",")));
			request.setAttribute("eviList", eviList);
			RequestDispatcher rd = request.getRequestDispatcher("pages/wsadmin-qdepth.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info("IN WasController POST:: "+action, className);
		JSONObject output = new JSONObject();
		try {
			if("qdepth".equalsIgnoreCase(action)) {
				String envi = request.getParameter("envi");
				ScriptExecutor sc = new ScriptExecutor();
				logger.info("envi:: "+envi, className);
				output.put("column", "Name,Depth");
				output.put("status", "success");
				String dataStr= sc.executeScript(envi,"qdepth");
				output.put("data",dataStr);
				
			} 
		} catch (Exception e) {
			logger.error("IN WasController POST:: "+e.getLocalizedMessage(), className);
			try {
				output.put("status", "failed");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} finally {
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    String outputStr = output.toString();
		    response.getWriter().write(outputStr);
		}
	}

}
