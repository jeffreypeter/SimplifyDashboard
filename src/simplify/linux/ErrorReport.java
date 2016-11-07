package simplify.linux;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


import org.apache.commons.io.IOUtils;

import simplify.utils.CustomException;
import simplify.utils.CustomLogger;
import simplify.utils.PropertyReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ErrorReport {


	private Session session;
	private PropertyReader reader = new PropertyReader();
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	private String logDir;
	private void createSession(String envi) throws NumberFormatException, JSchException {
		JSch jsch = new JSch();
		String host[] = reader.getValue("host_"+envi).split(":");			
		String user = reader.getValue("userid_"+envi);
		String password = reader.getValue("password_"+envi);
		Session session = jsch.getSession(user, host[0], Integer.parseInt(host[1]));
		this.logDir =reader.getValue("dir_"+envi);
		session.setPassword(password);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		this.session=session;
	}
	private void parseResult(ArrayList<String> resultList) {
		logger.info("IN parseResult", className);
		HashSet<String> fileBucket = new HashSet<String>();
		for(String line:resultList) {
			String linearr[] = line.split("ERROR ");
			String info="";
			for(int i=0;i<linearr.length;i++) {
				if(i==1) {
					info= linearr[1];
					fileBucket.add(info);
				}
			}
		}
		for(String line:fileBucket) {
			logger.info("line:: "+line, className);
			System.out.println(line);
		}
		
	}
	private void getReport() throws CustomException {
		logger.info("IN getReport", className);
		try {
			createSession("soa_dev");
//			createSession("soa_prod_173");
			logger.info("Session Status:: "+session.isConnected(),className);
			Channel channel = session.openChannel("exec");			
			String command = logDir+";find ./ApplicationModule* -type f -newermt \"2015-02-01 20:00:00\" ! -newermt \"2015-02-21 13:00:00\" -exec grep -n -H -B 0 -A 0 'ERROR '  {} \\;";
			((ChannelExec) channel).setCommand(command);
			logger.info("command:: "+ command,className);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			logger.info("Channel Status:: "+channel.isConnected(),className);		
			String result = IOUtils.toString(in, "UTF-8");
			ArrayList<String> resultList = new ArrayList<String>(Arrays.asList(result.split("\n")));
			logger.info("resultList::"+resultList.size(), className);
			parseResult(resultList);			
			channel.disconnect(); 
			System.out.println("completed");
			logger.info("completed", className);
		} catch (NumberFormatException e) {
			logger.error("IN getReport: "+e.getLocalizedMessage(), className);
			throw new CustomException(e.getLocalizedMessage());
		} catch (JSchException e) {
			logger.error("IN getReport: "+e.getLocalizedMessage(), className);
			throw new CustomException(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("IN getReport: "+e.getLocalizedMessage(), className);
			throw new CustomException(e.getLocalizedMessage());
		} finally {
			session.disconnect();
			logger.error("Session Closed successfully "+session.isConnected(), className);
		}
	}
	public static void main(String args[]) {		
		ErrorReport er = new ErrorReport();
		try {
			er.getReport();
		} catch (CustomException e) {
			System.out.println(e);
		}
	}


}
