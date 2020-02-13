<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/successPage.css"/>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
	<div class="all-wrap">
        <header>
            <jsp:include page="horizontalMenu.jsp" />
        </header>
        <div class="message-container">
            <div class="message-label">
                <fmt:message key="label.Your_lot_is_suggested"/>
            </div>
            <div class="link-container">
                <a href="${request.contextPath}?command=showMainPage"><fmt:message key="label.Main_Menu"/></a>
            </div>
        </div>
        </body>
    </div>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>