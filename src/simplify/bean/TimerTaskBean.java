package simplify.bean;

import java.util.ArrayList;

public class TimerTaskBean {
	private String taskName;
	private String taskId;
	private ArrayList<String> timer = new ArrayList<String>();
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public ArrayList<String> getTimer() {
		return timer;
	}
	public void setTimer(ArrayList<String> timer) {
		this.timer = timer;
	}
	
}
