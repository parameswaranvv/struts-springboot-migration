<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
	<h1>Login</h1>
    <h1><%=basePath%></h1>
    <hr>
    <s:form name = "loginForm" action="login" method="post" >
        <s:textfield name="loginForm.userName" label="username" /><br>
        <s:textfield name="loginForm.password" label="password" /><br>
        <s:submit/>
    </s:form>
</body>
</html>