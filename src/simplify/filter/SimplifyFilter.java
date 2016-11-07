package simplify.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;

/**
 * Servlet Filter implementation class SimplifyFilter
 */
public class SimplifyFilter implements Filter {

	private static CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private static final long serialVersionUID = 1L;
	private String maskUrl;
	/**
     * Default constructor. 
     */
    public SimplifyFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			String ipAddress = request.getRemoteAddr();
			String url = ((HttpServletRequest)request).getRequestURL().toString();
			String queryString = (((HttpServletRequest)request).getQueryString() !=null)?((HttpServletRequest)request).getQueryString():"";
			logger.info("url:: "+url+"--QueryString:: "+queryString, className);
			String action = (String) request.getParameter("action");
//			HttpServletRequest req = (HttpServletRequest) request;
			/*HttpSession session = req.getSession();
			String role = (String) session.getAttribute("role");				
			if(role == null) {
				session.setAttribute("role", "monitor");			
				logger.info("Role is set to "+role, className);
				chain.doFilter(request, response);
			} else {
				logger.info("Role ::"+role, className);
				ArrayList<String> restrictionLst = SimplifyCache.getInstance().getRoleRestriction().get(role);
				logger.info("Restriction ::"+restrictionLst, className);
				if(restrictionLst.contains(action)) {
					String controller = url.substring(url.indexOf(maskUrl)+maskUrl.length());
					String parameter = IOUtils.toString(request.getInputStream());
					logger.info("Controller::"+controller+";paramter:: "+parameter,className);
					session.setAttribute("controller", controller);
					session.setAttribute("parameter", parameter);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/pages/login.jsp");
					requestDispatcher.forward(request, response);
				} else {
					chain.doFilter(request, response);
				}*/
//			}
		} catch (Exception e) {
			logger.error("Exception in doFilter:: "+e.getLocalizedMessage(), className);
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String maskUrl = fConfig.getInitParameter("MaskURL"); 
		this.maskUrl= maskUrl;  
	}

}
