package simplify.mq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import simplify.utils.CustomException;
import simplify.utils.CustomLogger;
import simplify.utils.property.QueuePropertyReader;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class MQConnectionFactory implements Callable<MQQueueManager>{
	
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	
	private Map<String,String> mqPropertyMap = new HashMap<String,String>();
	private String envi = null;
	
	public MQConnectionFactory(){
		super();
	}
	
	public MQConnectionFactory(String envi){
		super();
		this.envi = envi;
		QueuePropertyReader mqPropertyReader = new QueuePropertyReader(envi);
		mqPropertyMap = mqPropertyReader.getMQConnectionProperties();	
	}
	
	public MQQueueManager GetQueueManager() throws Exception {
		
		logger.info("QMGR::"+mqPropertyMap.get("qmanager"),className);
		logger.info("hostname::"+mqPropertyMap.get("hostname"),className);
		logger.info("channel::"+mqPropertyMap.get("channel"),className);
		logger.info("port::"+mqPropertyMap.get("port"),className);
		
		MQEnvironment.hostname = mqPropertyMap.get("hostname");
		MQEnvironment.channel = mqPropertyMap.get("channel");
		MQEnvironment.port = Integer.parseInt(mqPropertyMap.get("port"));
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<MQQueueManager> future = executor.submit(this);
		
		try{
			return future.get(3, TimeUnit.SECONDS);	
		}catch(TimeoutException e) {
			logger.info("Timeout while trying to connect to QManager ::"+e.getMessage(),className);
			return null;
		}catch (Exception e) {
			logger.info("Unknown exception occured in GetQueueManager method",className);
			throw new CustomException(e.getLocalizedMessage());
		}finally{
			executor.shutdown();
		}
	}
	public Map<String,Boolean> getQueueStatus(MQQueueManager qmgr) throws Exception {
	
		List<String> qList = null;
		String queueString =  mqPropertyMap.get("qlist");
		qList = Arrays.asList(queueString.split(","));
		
		Map<String,Boolean> qstatusMap = new HashMap<String, Boolean>();
		ExecutorService executor = Executors.newFixedThreadPool(qList.size()); // Creates a Thread Pool
		try {
			if(null!=qmgr){
				for (String qname : qList) {
					Future<Boolean> future = executor.submit(new QueueStatus(qname,qmgr)); 
					qstatusMap.put(qname, future.get());
				}
			} else {
				logger.info("Qmanager is null", className);
				for (String qname : qList) {
					qstatusMap.put(qname, false);
				}
			}
			return qstatusMap;
		}
		catch(Exception e) {
			logger.info("Exception occured in getQueueStatus method ::"+e.getMessage(),className);
			throw new CustomException(e.getLocalizedMessage());
		}finally{
			executor.shutdown();
		}
	}

	@Override
	public MQQueueManager call() throws Exception{
		try {
			MQQueueManager qMgr = new MQQueueManager(mqPropertyMap.get("qmanager"));
//			TimeUnit.SECONDS.sleep(6);
			if(qMgr.isConnected()){
				return qMgr;
			} else {
				logger.info("qManager is closed", className);
				return null;
			}
		}
		catch (MQException e) {
			logger.info("Callable block terminated MQException ::"+e.getMessage(),className);
			return null;
		}
		catch (Exception e) {
			logger.info("Callable block terminated unknown Exception ::"+e.getMessage(),className);
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	
	/*INNER CLASS*/
	class QueueStatus implements Callable<Boolean> {

		private String qname;
		private MQQueueManager qMgr = null;
		private final int openOptionsForInputQueue = MQC.MQOO_OUTPUT	| MQC.MQOO_FAIL_IF_QUIESCING;  
		
		public QueueStatus(String qname, MQQueueManager qMgr){
			this.qname = qname;
			this.qMgr = qMgr;
		}
		@Override
		public Boolean call() throws Exception {
			try {
				MQQueue queue = qMgr.accessQueue(qname, openOptionsForInputQueue);
				return queue.isOpen();
			}catch(Exception e) {
				logger.info("Exception in inner class ::"+e.getMessage(),className);
				throw new CustomException(e.getLocalizedMessage());
			}
		}
	}
	public static void main(String args[]){
		//System.out.println("IN MAIN");
		
		try {
			MQConnectionFactory mqConnectionFactory = new MQConnectionFactory("DEV");
			MQQueueManager mqQueueManager = mqConnectionFactory.GetQueueManager();
			Map<String,Boolean>qStMap = mqConnectionFactory.getQueueStatus(mqQueueManager);
			
			for(String keyString : qStMap.keySet()) {
				//System.out.println("Key :"+keyString+" :: Value:"+qStMap.get(keyString));
			}
		}catch(Exception e) {
			//System.out.println("exception occured in main");
			//e.printStackTrace();
		}
	}
}
