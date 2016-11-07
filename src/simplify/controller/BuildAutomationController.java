package simplify.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;

/**
 * Servlet implementation class BuildAutomationController
 */
public class BuildAutomationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomLogger logger = new CustomLogger();
	private static PropertyReader reader = new PropertyReader("buildAutomation");
	private String className = this.getClass().getSimpleName();
    public BuildAutomationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info("In BuildAutomation Controller - action: "+action, className);
		if("".equals(action)) {
			
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/automation-build.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
