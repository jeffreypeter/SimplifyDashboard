package simplify.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import simplify.utils.CustomLogger;
import simplify.utils.property.XPropertyReader;

@Controller
public class ConfigurationController {
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	@RequestMapping(value="settings",method=RequestMethod.GET) 
	public ModelAndView loadPage() {
		logger.info("IN:: loadPage", className);
		return new ModelAndView("admin-settings");
	}
	@RequestMapping(value="settings/reload",method=RequestMethod.POST)
	@ResponseBody
	public String reloadConfiguration() {
		logger.info("IN:: reloadConfiguration", className);
		JSONObject res = new JSONObject();
		try {
			XPropertyReader.loadPropertyFile();
			res.put("status", "success");
			res.put("msg", "Property file Reloaded");
		}catch(Exception e) {
			try {
				res.put("status", "failed");
				res.put("msg", e.getLocalizedMessage());
			} catch(JSONException je) {
				je.printStackTrace();
			}
		}
		return res.toString();
	}
}
