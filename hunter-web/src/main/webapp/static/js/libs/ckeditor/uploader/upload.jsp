<%@page import="java.io.File"%>  

<%@page import="java.util.UUID"%>  

<%@page import="org.apache.commons.fileupload.FileItem"%>  

<%@page import="java.util.List"%>  

<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>  

<%@page import="org.apache.commons.fileupload.FileItemFactory"%>  

<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%> 

<%@ page import="java.util.ResourceBundle"%>

<%@ page import="com.fwd.invoker.enums.UploadFolder"%> 

<%@ page language="java" contentType="text/html; charset=UTF-8"%>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<html>  

<head>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" pageEncoding="UTF-8">  

<meta http-equiv="pragma" content="no-cache">  

<meta http-equiv="cache-control" content="no-cache">  

<meta http-equiv="expires" content="0">  

<title>JSP上传文件</title>  

</head>  

<body>  

<%  

if(ServletFileUpload.isMultipartContent(request)){  

    String type = "";  
 
    if(request.getParameter("type") != null)//获取文件分类  

        type = request.getParameter("type") + File.separator;  


    String callback = request.getParameter("CKEditorFuncNum");//获取回调JS的函数Num  

    FileItemFactory factory = new DiskFileItemFactory();  
    
    ServletFileUpload servletFileUpload = new ServletFileUpload(factory);  

    servletFileUpload.setHeaderEncoding("UTF-8");//解决文件名乱码的问题  
   
    List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);  
	if(fileItemsList.size()<=0) {
		 out.println("<script type='text/javascript'>alert('上传失败！');window.history.go(-1);</script>"); 
	}
    for (FileItem item : fileItemsList) {  

        if (!item.isFormField()) {  

            String fileName = item.getName();  

            fileName = "file" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));  

            //定义文件路径，根据你的文件夹结构，可能需要做修改  
            
            ResourceBundle res = ResourceBundle.getBundle("application"); 
			String folderPath = res.getString("pro.upload.base");
			
			String clientPath =  folderPath +File.separator+ UploadFolder.CKEditor.get() +File.separator + type + fileName; 

            //保存文件到服务器上  

            File file = new File(clientPath);  

            if (!file.getParentFile().exists()) {  

                file.getParentFile().mkdirs();  

            }  

            item.write(file);  

            
            //打印一段JS，调用parent页面的CKEditor的函数，传递函数编号和上传后文件的路径；这句很重要，成败在此一句  

            out.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("+callback+",'"+clientPath+"')</script>");  

            break;  

        }  

    }  

}  

 %>  

</body>  

</html>  