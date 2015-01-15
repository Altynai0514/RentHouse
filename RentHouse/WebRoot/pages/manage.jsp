<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0);  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>青鸟租房 - 用户管理</title>
	<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
	<link type="text/css" rel="stylesheet" href="<%=path%>/css/boxy.css" />
	<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="js/jquery.boxy.zh.js"></script>
</head>
<body>
	<s:include value="header.jsp"></s:include>
	<s:form name="searchForm" id="searchForm" action="doSearch">
		<s:include value="search_list.jsp"></s:include>
		<s:include value="house_list.jsp"></s:include>
	</s:form>
	<s:include value="footer.jsp"></s:include>
</body>
</html>