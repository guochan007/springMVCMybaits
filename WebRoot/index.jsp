<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    <%=path%><br>
    <%=basePath%><br>
    
    <a href="hello.do">点击hello</a><br>
    <a href="<%=path%>/page/login.jsp">登录</a><br>
    <a href="<%=path%>/page/login2.jsp">登录（加入session）</a>&nbsp;&nbsp;&nbsp;<a href="userDetail.do">验证session</a><br>
    <a href="<%=path%>/addUpdate">新增用户</a><br>
    <a href="<%=path%>/list">用户列表</a><br>
  </body>
</html>
