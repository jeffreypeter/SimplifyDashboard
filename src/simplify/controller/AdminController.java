package simplify.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import simplify.bean.User;
import simplify.utils.CustomLogger;
import simplify.utils.property.AdminPropertyReader;
import simplify.utils.property.XPropertyReader;

/**
 * Servlet implementation class AdminController
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private XPropertyReader reader = new AdminPropertyReader(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: AdminController - get"+className, className);
		String action=request.getParameter("action");
    	logger.info("action:: "+action, className);
    	/*if("edit-settings".equals(action)) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin-settings.jsp");
			dispatcher.forward(request, response);
    	} else*/
    	{
    		RequestDispatcher dispatcher = request.getRequestDispatcher("pages/login.jsp");
			dispatcher.forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("IN:: AdminController - post"+className, className);
		String action=request.getParameter("action");
    	logger.info("action:: "+action, className);
    	if(("authenticate").equalsIgnoreCase(action)) {
    		User user = new User();
    		user.setUserName("admin");
    		((AdminPropertyReader) reader).authenticateUser(user);
    		String passwordAth ="";
    		JSONObject output = new JSONObject();
    		String outputStr="";
    		/*try {
    			StringBuilder sb = new StringBuilder();
    			BufferedReader br = request.getReader();
    		    String str;
    		    while( (str = br.readLine()) != null ){
    		        sb.append(str);
    		    }   
    			logger.info("inputStr:: "+sb, className);
    			JSONObject input = new JSONObject(sb.toString());
    			logger.info("input:: "+input, className);
    			String user = input.getString("user");
        		String password = input.getString("password");
    			logger.info("In Authenticate - user:: "+user+";password:: "+password, className);
	    		if(user!=null && !user.isEmpty() && password!=null && !password.isEmpty()) {
		    		if(passwordAth == null) {		    			
		    			output.put("msg", "Invalid User Name");
		    		} else if(passwordAth.equalsIgnoreCase(password)) {
		    			output.put("msg", "success");
		    			output.put("authenticate","true");
		    		} else {
		    			output.put("msg", "success");
		    			output.put("authenticate","false");
		    		}
		    		outputStr = output.toString();
		    		logger.info("outputStr: "+outputStr, className);
	    		} else {
	    			output.put("msg", "success");
	    			logger.info("Invalid Input", className);
	    			output.put("msg", "Please Enter Valid User Name or Password");
	    		}
    		} catch (Exception e ) {
    			logger.error("Exception AdminController - post ::"+ e.getLocalizedMessage(), className);    			
    		}*/
    		response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(outputStr);
    	}
	}

}
