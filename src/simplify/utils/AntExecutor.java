package simplify.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;



public class AntExecutor {
	/*private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();

	private DefaultLogger getDefaultLogger() {
		DefaultLogger log = new DefaultLogger();
		log.setErrorPrintStream(System.err);
		log.setOutputPrintStream(System.out);
		log.setMessageOutputLevel(Project.MSG_INFO);
		return log;
	}
	public boolean executeAntTask(String buildFilePath,String target) {
		logger.info("IN:: executeAntTask", className);
		boolean flag=false;		
		Project project = new Project();
		File buildFile = new File(buildFilePath);
		project.addBuildListener(getDefaultLogger());
		try {
			project.fireBuildStarted();
			project.init();
			ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
            project.addReference("ant.projectHelper", projectHelper);
            projectHelper.parse(project, buildFile);
            String targetToExecute = (target != null && !target.isEmpty()) ? target.trim() : project.getDefaultTarget();
            project.executeTarget(targetToExecute);
            project.fireBuildFinished(null);
            flag = true;
            
		} catch (Exception e) {
			logger.error("In exception:: "+e.getLocalizedMessage(), className);
		}
		return flag;
	}
	public static void main(String[] args) {
		 	ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "SET");
		    Map<String, String> env = pb.environment();
		    env.put("ANT_HOME", "D:/IBM/SDPShared/plugins/org.apache.ant_1.7.1.v20100518-1145");
		    Process p = pb.start();
		    InputStreamReader isr = new InputStreamReader(p.getInputStream());
		    char[] buf = new char[1024];
		    while (!isr.ready()) {
		        ;
		    }
		    while (isr.read(buf) != -1) {
		        System.out.println(buf);
		    }
		AntExecutor exe = new AntExecutor();
		exe.executeAntTask("D:/Simplify360/script/build.xml",null);
	}*/
}
