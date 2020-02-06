<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="epam.by.entity.User"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login Page</title>
	</head>
	<body>
		<form name="login-form" method="post" action="controller">
		    <input type="hidden" name="command" value="login" />
			Please enter your username
			<input type="text" name="login"/><br>
			Please enter your password
			<input type="text" name="password"/>
			<input type="submit" value="submit">
		</form>
	</body>
</html>
