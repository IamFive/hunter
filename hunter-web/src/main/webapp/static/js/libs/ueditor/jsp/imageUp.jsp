<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.fwd.invoker.util.Uploader"%>

<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	Uploader up = new Uploader(request);
	String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
	up.setAllowFiles(fileType);
	up.setMaxSize(10000L); //单位KB
	up.upload();
	String url = up.getUrl();
	response.getWriter().print("{'original':'" + up.getOriginalName() + "','url':'" + url + "','title':'" + up.getTitle() + "','state':'" + up.getState() + "'}");
%>
