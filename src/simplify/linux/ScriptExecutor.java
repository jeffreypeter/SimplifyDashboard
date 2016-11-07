package simplify.linux;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simplify.utils.CustomLogger;

import com.jcraft.jsch.*;

public class ScriptExecutor {
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	
	public String executeScript(String envi,String script) throws Exception {
		SSHClient client = new SSHClient();
		String result = client.runScript(envi, script);
		String dataStr = result.substring(result.indexOf("---Start---")+11, result.length()).replaceAll("\n", "");
		logger.info("output:: "+dataStr, className);
		return dataStr;
	}
}
