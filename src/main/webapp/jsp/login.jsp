<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginStyle.css"/>
        <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
    <div class="login-page">
        <div class="form">
            <form class="login-form" method="post" action="auction">
                <input type="hidden" name="command" value="login" />
                <input type="text" name="login" placeholder="<fmt:message key="label.login"/>"/>
                <input type="password" name="password" placeholder="<fmt:message key="label.password"/>"/>
                <button><fmt:message key="label.Sign_in"/></button>
                <p class="message"><fmt:message key="text.not_registered"/> <a href="${request.contextPath}?command=showRegistrationPage"><fmt:message key="label.Sign_up"/></a></p>
            </form>
            <c:if test="${not empty errorMessage}">
                <div class="error-container">
                    <fmt:message key="error.${requestScope.errorMessage}"/>
                </div>
            </c:if>
        </div>
   </div>
   </body>
</html>