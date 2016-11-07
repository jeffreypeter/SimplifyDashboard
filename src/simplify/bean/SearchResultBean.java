package simplify.bean;

import java.util.Date;
import java.util.LinkedHashSet;

public class SearchResultBean {	
	private String fileName;
	private Date updatedDate;
	private LinkedHashSet<String> lineNo = new  LinkedHashSet<String>();
	private LinkedHashSet<String> threadIds = new LinkedHashSet<String>();
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	

	public LinkedHashSet<String> getLineNo() {
		return lineNo;
	}

	public void setLineNo(LinkedHashSet<String> lineNo) {
		this.lineNo = lineNo;
	}

	public LinkedHashSet<String> getThreadIds() {
		return threadIds;
	}

	public void setThreadIds(LinkedHashSet<String> threadIds) {
		this.threadIds = threadIds;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/*public int hashCode(){
	 System.out.println("hashCode");
	return fileName.hashCode()*fileName.hashCode();//for simplicity reason
}
@Override 
public boolean equals(Object obj) {
  boolean result = false;
  if (obj instanceof SearchResultBean) {
	   SearchResultBean ex = (SearchResultBean) obj;
      result = ex.getFileName() == this.getFileName();
      System.out.println("In Equals");
  }
  return result;
}*/
}
