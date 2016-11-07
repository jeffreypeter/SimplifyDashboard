package simplify.linux;

import com.jcraft.jsch.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import simplify.bean.SearchResultBean;
import simplify.utils.CustomException;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;

public class SSHClient implements Runnable{	
	private PropertyReader reader = new PropertyReader();
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private Session session;
	private String logDir;
	private String workDir;
	public Session createSession(String envi) throws JSchException{
		JSch jsch = new JSch();
		logger.info("IN createSession:: "+envi, className);
		String host[] = reader.getValue("host_soa_"+envi).split(":");			
		String user = reader.getValue("userid_soa_"+envi);
		String password = reader.getValue("password_soa_"+envi);
		Session session = jsch.getSession(user, host[0], Integer.parseInt(host[1]));
		session.setPassword(password);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		return session;		
	}
	public String runScript(String envi,String scriptName) throws Exception {
		String result="";
		try {
			Session session = createSession(envi);
			logger.info("Session: "+session.isConnected(), className);
			Channel channel = session.openChannel("exec");
			String command = reader.getValue("dir_soa_"+envi+"_"+scriptName);;
			logger.info("Command:: "+command, className);
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			((ChannelExec) channel).setPty(true);
			channel.connect();
			result = IOUtils.toString(in, "UTF-8");
			logger.info("Channel:: "+channel.isConnected(), className);
			logger.info("Result:: "+result, className);
			channel.disconnect();
			session.disconnect();
		} catch(Exception e) {
			session.disconnect();
			throw e;			
		}
		return result;		
	}
	private void createSession(String envi,String ipAddress) throws NumberFormatException, JSchException {
		JSch jsch = new JSch();
		String host[] = reader.getValue("host_"+envi).split(":");			
		String user = reader.getValue("userid_"+envi);
		String password = reader.getValue("password_"+envi);
		Session session = jsch.getSession(user, host[0], Integer.parseInt(host[1]));		
		session.setPassword(password);
		String logDir =reader.getValue("dir_"+envi);
		this.logDir=logDir;
		String workDir = reader.getValue("workdir")+ipAddress+"_"+envi;
		this.workDir = workDir;				
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		this.session=session;// Copy of the Session
	}
	private void getFileList(LinkedHashSet<SearchResultBean> fileSet,String param,String envi,String team,String ipAddress,String filePattern) throws JSchException, IOException, SftpException, ParseException, CustomException{
		createSession(envi,ipAddress);
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		format.setLenient(false);
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		String result = null;		
		logger.info("Session status:: "+ session.isConnected(),className);
		Channel channel = session.openChannel("exec");
		String command = null;
		if("soa".equalsIgnoreCase(team)) {
			command = logDir+";grep -o -n -H "+param+" "+filePattern+"*.log*";
		} else {
			logDir = logDir+filePattern;
			command = logDir+";grep -o -n -H "+param+" *.log*";
		}
		((ChannelExec) channel).setCommand(command);//grep -n a72ryfbg0e8wd TEBT_Req*.log*
		logger.info("command:: "+ command,className);
		channel.setInputStream(null);
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in = channel.getInputStream();
		channel.connect();
		logger.info("Channel Status:: "+channel.isConnected(),className);		
		result = IOUtils.toString(in, "UTF-8");
		if(result.length()<3) {
			throw new CustomException("No Results Found");
		}
		channel.disconnect(); 
		String[] lines = result.split("\n");
//		logger.info("result:: "+ result,className);
		System.out.println("Info:: Parsing Started");
		SearchResultBean bean = new SearchResultBean();
		LinkedHashSet<String> fileName = new LinkedHashSet<String>();
		LinkedHashSet<String> lineNo = new LinkedHashSet<String>();
		logger.info("Total Lines::" + lines.length,className);
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] attributes = line.split(":");
			if (!fileName.contains(attributes[0])) {
				bean = new SearchResultBean();
				lineNo = new LinkedHashSet<String>();
				bean.setFileName(attributes[0]);
				bean.setLineNo(lineNo);
				fileSet.add(bean);
			}
			String lineNoTemp = attributes[1];
			if (!lineNo.contains(lineNoTemp)) {
				lineNo.add(lineNoTemp);
			}
			fileName.add(attributes[0]);
		}			
		logger.info("Info:: Parsing Ended",className);
		(new File(workDir)).mkdir();
		for(SearchResultBean tempBean:fileSet) {
//			logger.info("fileName:: "+tempBean.getFileName()+" TotalPgNos:: "+tempBean.getLineNo().size(),className);
			Thread thread = new Thread(this,tempBean.getFileName());
			thread.start();
			threadList.add(thread);
			if(threadList.size()>9) {
				logger.info("!! ThreadSize:: "+threadList.size(), className);
				for(Thread threadTemp:threadList) {
					try {
						threadTemp.join();
					} catch (InterruptedException e) {
						logger.error("InterruptedException:: "+e.getLocalizedMessage(), className);
					}
				}
				threadList.clear();
			}
		}
		logger.info("out of loop",className); 
		for(Thread threadTemp:threadList) {
			try {
//				logger.info(thread.getName()+":: "+threadTemp.isAlive(), className);
				threadTemp.join();
			} catch (InterruptedException e) {
				logger.error("InterruptedException:: "+e.getLocalizedMessage(), className);
			}
		}
	}
	public String searchParam(LinkedHashSet<SearchResultBean> fileSet,String param,String envi,String team,String ipAddress,String filePattern) throws IOException, ParseException, CustomException {
		logger.info("IN:: searchParam", className);
		String msg="";
		
		 try {
			getFileList(fileSet,param,envi,team,ipAddress,filePattern);
			msg="success";
		} catch (JSchException e) {
			msg = e.getLocalizedMessage();
			logger.error("IN Exception :: searchParam - "+e.getLocalizedMessage(), className);
		} catch (SftpException e) {
			msg = e.getLocalizedMessage();
			logger.error("IN Exception :: searchParam - "+e.getLocalizedMessage(), className);
		} finally {
			try {
				session.disconnect();
				logger.info("Finally Session status:: "+ session.isConnected(),className);
			}catch (Exception e) {
				logger.error("In Exception: session not opened", className);
			}
		}		 
		return msg;
	}
	
	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		String threadName =thread.getName();
//		logger.info("ThreadName:: "+thread.getName(), className);
			Channel channel;
			try {
				channel = session.openChannel("exec");
				InputStream in = channel.getInputStream();
				ChannelExec channelEx = ((ChannelExec) channel);
				channel.setInputStream(null);
				channelEx.setCommand("cd .. ;cd "+logDir+"; cat "+threadName);
				channel.connect();
//				logger.info(threadName+":: channelEx status:: "+channel.isConnected(),className);
				String result = IOUtils.toString(in, "UTF-8");
				FileUtils.write((new File(workDir+"/"+threadName)),result,false);
				logger.info(threadName+ " is copied",className);
				channel.disconnect();
//			logger.info(threadName+":: channelEx closed",className);
			} catch (JSchException e) {
				logger.error("run::JSchException:: "+e.getLocalizedMessage(), className);
			} catch (IOException e) {
				logger.error("run::IOException:: "+e.getLocalizedMessage(), className);
			}
	}	
	public String pickLogs(LinkedHashSet<SearchResultBean> fileSet, String envi,String team, String ipAddress, String filePattern) {
		logger.info("IN:: Picklogs", className);
		String msg="";
		try {
			createSession(envi,ipAddress);
			ArrayList<Thread> threadList = new ArrayList<Thread>();
			logger.info("Session status:: "+ session.isConnected(),className);
			(new File(workDir)).mkdir();
			String pickLogs = reader.getValue("picklogs");
			for(int i=0;i<Integer.parseInt(pickLogs);i++) {
				SearchResultBean tempBean = new SearchResultBean();
				fileSet.add(tempBean);
				if(i==0) {
					tempBean.setFileName(filePattern+".log");	
				} else {
					tempBean.setFileName(filePattern+".log."+i);
				}				
				Thread thread = new Thread(this,tempBean.getFileName());
				thread.start();
				threadList.add(thread);
				if(threadList.size()>9) {
					logger.info("!! ThreadSize:: "+threadList.size(), className);
					for(Thread threadTemp:threadList) {
						try {
							threadTemp.join();
						} catch (InterruptedException e) {
							logger.error("InterruptedException:: "+e.getLocalizedMessage(), className);
						}
					}
					threadList.clear();
				}
			}
			for(Thread threadTemp:threadList) {
				try {
					threadTemp.join();
				} catch (InterruptedException e) {
					msg=e.getLocalizedMessage();
					logger.error("InterruptedException:: "+e.getLocalizedMessage(), className);
				}
			}
			msg="success";
		} catch(Exception e) {
			msg=e.getLocalizedMessage();
			logger.error("In Exception: pickLogs:: "+e.getLocalizedMessage(), className);
		} finally {
			try {
				session.disconnect();
				logger.info("Finally Session status:: "+ session.isConnected(),className);
			}catch (Exception e) {
				logger.error("In Exception: session not opened", className);
			}
			
		}
		return msg;
	}
}
