<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <form name = "loginForm" action="login.do" method="post" >
        userName:<input id="userName" name="userName" type="text" /><br>  
        passWord:<input id="passWord" name="password" type="password" /><br>  
        <input type="submit" id="submit" name="submit" value="submit" />  
    </form>  
</body>
</html>