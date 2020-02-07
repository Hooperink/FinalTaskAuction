<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css"/>
	     <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
        <div class = "footer-wrap">
            <ul>
                <li><a href="#">Twitter</a></li>
                <li><a href="#">Codepen</a></li>
                <li><a href="#">Email</a></li>
                <li><a href="#">Dribbble</a></li>
                <li><a href="#">Github</a></li>
                <li>
                    <p>👋</p>
                </li>
            </ul>
        </div>
	</body>
</html>