package simplify.client.bpd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import simplify.bean.TaskBean;
import simplify.bean.TimerTaskBean;
import simplify.utils.CustomLogger;
import simplify.utils.SimplifyCache;
import simplify.utils.property.WorkflowPropertyReader;
import bpm.rest.client.BPMClient;
import bpm.rest.client.BPMClientException;
import bpm.rest.client.authentication.AuthenticationTokenHandler;
import bpm.rest.client.authentication.AuthenticationTokenHandlerException;
import bpm.rest.client.authentication.was.WASAuthenticationTokenHandler;

public class BpdController {
	private WorkflowPropertyReader reader = null;
	private HashMap<String,String> configMap = null;
	private SimplifyCache instance = SimplifyCache.getInstance();
	private AuthenticationTokenHandler handler;
	private BPMClient client;
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private ArrayList<TimerTaskBean> timerList = null;
	public BpdController(String envi) throws BPMClientException, AuthenticationTokenHandlerException {		
		logger.info("IN::  BpdController", className);
		reader = new WorkflowPropertyReader(envi);
		configMap = reader.getConnectionDetails();
		logger.info("configMap:: "+configMap, className);
		handler = new WASAuthenticationTokenHandler(configMap.get("user"),configMap.get("password"));
		client= new BPDApiExtended(configMap.get("host"), Integer.parseInt(configMap.get("port")), handler);
		
	}
	
	public JSONObject performSearch(String paramName,String paramValue) throws BPMClientException, AuthenticationTokenHandlerException, JSONException {
		logger.info("IN:: performSearch", className);
		JSONObject result = new JSONObject();
		JSONObject mmsJson = new JSONObject();
		mmsJson.put("paramName",paramName);//"evrNo"
		mmsJson.put("paramValue",paramValue);//"EVR100002165"RAJ_901562
		result=client.runService("MMS@ParamSearch", mmsJson);
		return result;
	}
	
	public ArrayList<TaskBean> parseResult(JSONObject result) throws JSONException{
		ArrayList<TaskBean> beanList = new ArrayList<TaskBean>();
		JSONObject outerData = result.getJSONObject("data");
		JSONObject innerData = outerData.getJSONObject("data");
		JSONObject searchResult = innerData.getJSONObject("searchResult");
		JSONArray items = searchResult.getJSONArray("items");
		for (int i=0;i<items.length();i++) {
			JSONObject temp = items.getJSONObject(i);
			TaskBean bean = new TaskBean();
			bean.setProId(temp.getString("proId"));
			bean.setTaskId(temp.getString("taskId"));
			bean.setTaskName(temp.getString("taskName"));
			bean.setDueDate(temp.getString("dueDate"));
			bean.setAssignedTo(temp.getString("assignedTo"));
			bean.setTaskStatus(temp.getString("taskStatus"));
			bean.setStartDate(temp.getString("startDate"));
			bean.setProName(temp.getString("proName"));
			bean.setProStatus(temp.getString("proStatus"));
			beanList.add(bean);
		}
		System.out.println(beanList.size());
		return beanList;
	}
	
	public void terminatePiid(int instanceId) throws BPMClientException, AuthenticationTokenHandlerException {
		System.out.println("instanceId:: "+instanceId);
		client.terminateProcessInstance(instanceId);		
	}
	public void deletePiid(int instanceId) throws BPMClientException, AuthenticationTokenHandlerException {
		System.out.println("In Delete");
		client.deleteProcessInstance(instanceId);
	}
	public JSONObject getBPDInstanceDetails(int instanceId) throws BPMClientException, AuthenticationTokenHandlerException {
		logger.info("IN:: getBPDInstanceDetails", className);
		return ((BPDApiExtended) client).getBPDInstanceDetails(instanceId,"data");
//		return client.getBPDInstanceDetails(instanceId);
	}
	public JSONObject assignTask(String taskId,String userId) throws BPMClientException, AuthenticationTokenHandlerException, JSONException {
		JSONObject input = new JSONObject();
		JSONObject output = new JSONObject();
		input.put("taskId", taskId);
		input.put("userName", userId);
		output=client.runService("TEBTNB@ReassignTask", input);	
		return output; 
	}
	public JSONObject triggerTimer(int instanceId, String timerTokenId) throws BPMClientException, AuthenticationTokenHandlerException {
		return ((BPDApiExtended) client).triggerTimer(instanceId, timerTokenId);
	}
	public JSONObject getTaskDetails(int piid) throws BPMClientException, AuthenticationTokenHandlerException, JSONException {
		JSONObject result = ((BPDApiExtended) client).getBPDInstanceDetails(piid,"executionTree");
		JSONObject data = result.getJSONObject("data");
		JSONObject executionTree = data.getJSONObject("executionTree");
		JSONObject root = executionTree.getJSONObject("root");
		timerList = new ArrayList<TimerTaskBean>();
		parseChildren(root);
		JSONObject output =parserTimer(timerList,piid);
//		client.triggerTimer(62490, "6");
		logger.info("output:: "+output.toString(), className);
		return output;
	}
	public void parseChildren(JSONObject root){
		try {
			JSONArray children = root.getJSONArray("children");
			for (int i = 0; i < children.length(); i++) {
				JSONObject tempChildren = children.getJSONObject(i);
				JSONArray createdTaskIDs = null;
				try {
					createdTaskIDs = tempChildren
							.getJSONArray("createdTaskIDs");
				} catch (JSONException e) {

				}

				if (createdTaskIDs != null) {
					try {
						logger.info("createdTaskIDs:: "+createdTaskIDs, className);
						TimerTaskBean temp = new TimerTaskBean();
						temp.setTaskName(tempChildren.getString("name"));
						temp.setTaskId((String)createdTaskIDs.get(0));					
						ArrayList<String> timer= new ArrayList<String>();
						temp.setTimer(timer);
						JSONArray childrenArray =  tempChildren.getJSONArray("children");
						for(int j=0;j<childrenArray.length();j++) {
							JSONObject tempChild = (JSONObject) childrenArray.get(j);
							logger.info("tempChild:: "+tempChild.toString(), className);
							String timerDetails = tempChild.getString("name")+"--"+tempChild.getString("tokenId");							
							timer.add(timerDetails);
						}
						logger.info("TaskName:: "+temp.getTaskName(), className);
						timerList.add(temp);
					} catch(JSONException e) {

					}

				} else {
					parseChildren(tempChildren);
				}
			} 
		}catch(JSONException e) {
			logger.error("IN:: parseChildren :: JSONException:: "+e.getLocalizedMessage(), className);
		}
	}
	public JSONObject parserTimer(ArrayList<TimerTaskBean> timerList, int piid) throws JSONException {
		JSONObject output = new JSONObject();
		if(timerList.isEmpty()) {
			output.put("status", "No Timer Found");
		} else {
			output.put("status", "success");
			output.put("instanceId", Integer.toString(piid));
			JSONArray lst = new JSONArray();
			output.put("timerlist", lst);			
			logger.info("timerlist:: "+timerList.size(), className);
			for(TimerTaskBean temp:timerList) {
				JSONObject timerTask = new JSONObject();
				timerTask.put("taskid", temp.getTaskId());
				timerTask.put("taskname", temp.getTaskName());
				ArrayList<String> timer = temp.getTimer();
				JSONArray timerDetailLst = new JSONArray();
				for (String timerTemp: timer) {
					logger.info("check:: "+timerTemp, className);
					timerDetailLst.put(timerTemp);
				}
				timerTask.put("timerdtl", timerDetailLst);
				if(!timer.isEmpty()) {
					lst.put(timerTask);
				}
			}
		}
		return output;
	}
	public JSONObject runService(String serviceName, JSONObject param) throws BPMClientException, AuthenticationTokenHandlerException {
		return client.runService(serviceName, param);
	}	
	public JSONObject getItemCount(String item) throws Exception{
		logger.info("IN:: getItemCount", className);
		LinkedHashMap<String, String> itemDetails = reader.getItemDetails(item);
		String jsonPath= itemDetails.get("path");
		String value="";
		JSONObject output = client.runService(itemDetails.get("query"),new JSONObject(itemDetails.get("value")));
		value = output.getJSONObject("data").getJSONObject("data").getString(jsonPath);
		logger.info("count:: "+value, className);
		JSONObject outputStr = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm a");
		Date date = new Date();
		outputStr.put("value", value);
		outputStr.put("key", item);
		outputStr.put("date", dateFormat.format(date));
		instance.addInfo(item, outputStr);
		return outputStr;
	}
	public JSONObject getHandleErrorApplications() throws Exception{
		logger.info("IN:: getHandleErrorApplications", className);
		JSONObject output = client.runService("TEBTNB@GetTaskInstances",new JSONObject("{\"taskName\": \"Handle Errors\",\"taskStatus\":\"New_or_Received\"}"));
		JSONArray items = output.getJSONObject("data").getJSONObject("data").getJSONObject("InstanceDetails").getJSONArray("items");
		String colNameArr[] = {"applicationNo","taskId","taskInitiationDate","processInstanceId","assignedToUser","assignedToRole","priority"};
		HashSet<String> colNameSet = new HashSet<String>(Arrays.asList(colNameArr));
		HashSet<String> appSet = new HashSet<String>();
		ArrayList<ArrayList<String>> rowLst = new ArrayList<ArrayList<String>>();
		for(int i=0;i<items.length();i++) {
			JSONObject item = items.getJSONObject(i);
			ArrayList<String> row = new ArrayList<String>();
			for(String colName: colNameSet) {
				if("applicationNo".equalsIgnoreCase(colName)) {
					appSet.add(item.getString(colName));
				}
				String value = item.getString(colName);
				row.add(value); 
			}
			rowLst.add(row);
		}
		JSONObject value = new JSONObject();
		value.put("appLst", appSet);
		value.put("colNameLst", colNameSet);
		value.put("rowLst", rowLst);
		logger.info("value:: "+value, className);
		return value;		
	}
}
 