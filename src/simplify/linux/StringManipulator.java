package simplify.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;

import simplify.bean.SearchResultBean;
import simplify.utils.CustomLogger;

public class StringManipulator {
	private String className = this.getClass().getSimpleName();
	private CustomLogger logger = new CustomLogger();
	private boolean traverseFlag = true;
	private int currentLineNo = 0;
	private int initLineNo = 0;
	private String workDir;
	private SearchResultBean file;
	private String threadName1 = "WebContainer"; //HardCoding
	private String threadName2 = "Default "; //HardCoding
	private int offsetLimit = 50;
	private String tempThread="";
	public void getThreadId(SearchResultBean file,String workDir,String depth) throws IOException {
		logger.info("IN:: getThreadId ", className);
		this.offsetLimit=Integer.parseInt(depth);
		LinkedHashSet<String> lineNos = file.getLineNo();
		this.workDir= workDir;
		this.file = file;
		for(String lineNo: lineNos) {
			int tempLine = Integer.parseInt(lineNo.replaceAll("\\s",""));
			initLineNo =tempLine -1; // Exact Line number
			currentLineNo = initLineNo;
			traverse();
			if(!tempThread.equals("")) {
				file.getThreadIds().add(tempThread);
				tempThread="";
			}
			
		}
	}
	private void traverse() throws IOException {
		int diff = Math.abs(currentLineNo - initLineNo);
		if(currentLineNo < 0 && traverseFlag==true) {
			logger.info("Changing the traverseFlag", className);
			currentLineNo = initLineNo;
			traverseFlag = false;
		}
		if(diff>=offsetLimit) {
			logger.info("initLineNo:: "+initLineNo,className);
			logger.info("currentLineNo:: "+currentLineNo,className);
			logger.info("offset Exeeded",className);
			return;
		}
		if(traverseFlag) {
			String lineContent = FileUtils.readLines(new File(workDir+"/"+file.getFileName())).get(currentLineNo);
			if(lineContent.contains(threadName1) || lineContent.contains(threadName2)) {//HardCoding - improvement can be done - code complexity
				String thread = lineContent.substring(lineContent.indexOf('[')+1, lineContent.indexOf(']'));
				logger.info("initLineNo::"+initLineNo+" threadId:: "+thread,className);
				tempThread=thread;
				return; // HardCoding
			} else {
				currentLineNo--;
				traverse();
			}
		} else {
			String lineContent = FileUtils.readLines(new File(workDir+"/"+file.getFileName())).get(currentLineNo);
			if(lineContent.contains(threadName1) || lineContent.contains(threadName2)) {//HardCoding
				String thread = lineContent.substring(lineContent.indexOf('[')+1, lineContent.indexOf(']'));
				logger.info("initLineNo::"+initLineNo+" threadId:: "+thread,className);
				traverseFlag=true;
				tempThread=thread;
				return; // HardCoding
			} else {
				currentLineNo++;
				traverse();
			}
		}
		return;
	}
	/*public void getLogs(SearchResultBean file,String workDir) {
		logger.info("IN:: getThreadId ", className);
		LinkedHashSet<String> threadIds = file.getThreadIds();
		this.workDir= workDir;
		this.file = file;
	}*/
	public String getThreadContent(SearchResultBean file,String workDir) throws IOException {
		logger.info("IN:: getThreadContent", className);
		ArrayList<String> content = new ArrayList<String>(FileUtils.readLines(new File(workDir+"/"+file.getFileName())));
		ListIterator<String> lines = content.listIterator();
		LinkedHashSet<String> threadIds = file.getThreadIds();
		StringBuilder finalThreadContent = new StringBuilder();
		String thread="";
		for(String temp: threadIds) {
			thread= "["+temp+"]";
		}
		while(lines.hasNext()) {
			String line = (String) lines.next();
			if(line.contains(thread)) {
				finalThreadContent.append(line+"\n");
			}
		}
		return finalThreadContent.toString();
	}
	public String getFile(SearchResultBean file,String workDir) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
 
			br = new BufferedReader(new FileReader(workDir+"/"+file.getFileName()));
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return sb.toString();
	}
}
