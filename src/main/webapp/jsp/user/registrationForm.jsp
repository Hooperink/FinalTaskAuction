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
                    <input type="hidden" name="command" value="register" />
                    <input type="text" required name="login" placeholder="<fmt:message key="label.login"/>"/>
                    <input type="password" required name="password" placeholder="<fmt:message key="label.password"/>"/>
                    <input type="password" required name="repeatPassword" placeholder="<fmt:message key="label.repeat_password"/>"/>
                    <button><fmt:message key="label.Sign_up"/></button>
                    <p class="message"><fmt:message key="text.already_registered"/> <a href="${request.contextPath}?command=showLoginPage"><fmt:message key="label.Sign_in"/></a></p>
                    <c:if test="${not empty errorMessage}">
                        <div class="error-container">
                            <fmt:message key="error.${requestScope.errorMessage}"/>
                        </div>
                    </c:if>
                </form>
            </div>
       </div>
   </body>
</html>