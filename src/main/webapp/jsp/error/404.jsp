<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/404.css"/>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
        <div class="wrap">
           <div class="logo">
            <h1>404</h1>
            <p> Sorry - File not Found!</p>
           </div>
          </div>
    </body>
</html>