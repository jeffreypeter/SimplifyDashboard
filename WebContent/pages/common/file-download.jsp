<%    
  String filename = (String)request.getParameter("name");
  String filepath = (String)request.getParameter("absolutePath");  
  response.setContentType("APPLICATION/OCTET-STREAM");   
  response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
  System.out.println(filepath + filename);
  java.io.FileInputStream fileInputStream=new java.io.FileInputStream(filepath);  
            
  int i;   
  while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
  }   
  fileInputStream.close();   
%>
