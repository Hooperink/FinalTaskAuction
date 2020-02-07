<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="data:;base64,iVBORw0KGgo=">
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
