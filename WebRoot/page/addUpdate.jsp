<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Insert title here</title>
</head>
<body>
	<form action="save.do" method="post">
		username:<input type="text" name="username" value="${user.username}"/>
		<br>
		password:<input type="password" name="password" value="${user.password}"/>
		<br>
		<input type="hidden" name="id" value="${user.id}"> 
		<input type="submit" value="保存" />
	</form>
</body>
</html>