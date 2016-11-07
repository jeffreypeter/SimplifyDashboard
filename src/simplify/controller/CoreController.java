package simplify.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import simplify.bean.file.SharedFile;
import simplify.utils.BeanLoader;
import simplify.utils.CustomLogger;

@Controller
public class CoreController  {
	private CustomLogger logger = new CustomLogger();
	private String className = this.getClass().getSimpleName();
	
	@RequestMapping(value="file-sharing",method=RequestMethod.GET)  
	public ModelAndView loadFileShare() {
		logger.info("IN:: loadFileShare", className);
		return new ModelAndView("file-sharing");  
	}
	@RequestMapping(value="file-sharing/upload",method=RequestMethod.POST)
	@ResponseBody
	public void uploadFile( @RequestParam List<MultipartFile> file) {
		Iterator<MultipartFile> itr = file.iterator();
		while(itr.hasNext()) {
			MultipartFile item = itr.next();
			System.out.println("item:: "+item.getContentType());
			File uploadedFile = new File("D://Simplify//storage//public//"+item.getOriginalFilename());
			
	 	    try {
				item.transferTo(uploadedFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 	    System.out.println("File Written");
		}
	
	}
	@RequestMapping(value="file-sharing/delete",method=RequestMethod.GET,produces="application/json; charset=utf-8")
	@ResponseBody
	public String doDelete(@RequestParam("fileName") String fileName) {
		JSONObject res = new JSONObject();
		
		try {
			String filePath = "D:\\Simplify\\storage\\public\\"+fileName;
			File file = new File(filePath);
			file.delete();
			res.put("status", "success");
		} catch (Exception e) {
			try {
				res.put("status", "failed");
			} catch (JSONException e1) {
				
				e1.printStackTrace();
			}
			System.out.println("IN Exception:: "+e.getLocalizedMessage());
		}
		System.out.println("file Deleted");
		return res.toString();
	}
	
	@RequestMapping(value="file-sharing/download",method=RequestMethod.GET) 
	public void doDownload(HttpServletResponse response,@RequestParam("fileName") String fileName) throws IOException {
		String filePath = "D:\\Simplify\\storage\\public\\"+fileName;
		 File downloadFile = new File(filePath);
		 FileInputStream inputStream = new FileInputStream(downloadFile);
		 response.setContentType("application/octet-stream");
		 response.setHeader("Content-Disposition", "attachment; filename=\""+ downloadFile.getName() + "\"");
		 /*OutputStream os = response.getOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        while ((len = inputStream.read(buffer)) != -1) {
	            os.write(buffer, 0, len);
	        }
	        os.flush();
	        os.close();  */     
		 IOUtils.copy(inputStream, response.getOutputStream());
		 response.flushBuffer();
		 inputStream.close();
		System.out.println("Dowloaded");
 
    }
	@RequestMapping(value="file-sharing/list",method=RequestMethod.GET,produces="application/json; charset=utf-8")
	@ResponseBody
	public String getFileList() {
		System.out.println("IN:: getFileList");
		JSONObject res = new JSONObject();
		Collection<File> files = FileUtils.listFiles(new File("D://Simplify//storage//public//"), null, false);
		ArrayList<SharedFile> fileLst = new ArrayList<SharedFile>();
		for(File file: files) {
			
			SharedFile sharedFile = (SharedFile)BeanLoader.getBean("sharedFile");
			sharedFile.setName(file.getName());
			sharedFile.setAbsolutePath(file.getPath());
			
			fileLst.add(sharedFile);
			System.out.println("sharedFile:: "+sharedFile.toString());
		}
		ObjectMapper converter = new ObjectMapper();
		try {
			
			String responseStr = converter.writeValueAsString(fileLst);
			res.put("status", "success");
			res.put("data", responseStr);					
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res.put("status", "Failed");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return res.toString();
	}
}

