<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
	    <ul class="menu-main">
	        <li><a href="${request.contextPath}?command=showMainPage"><fmt:message key="label.Main_Menu"/></a></li>
	        <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <div class="dropdown">
                        <li>
                            <button class="dropbtn"> <fmt:message key="label.Admin_menu"/> &#9660</button>
                            <div class="dropdown-content">
                                <a href="${request.contextPath}?command=getUsers"> <fmt:message key="label.User_menu"/></a>
                                <a href="${request.contextPath}?command=getLotsByStatus&status=MODERATION"> <fmt:message key="label.Lots_for_moderation"/></a>
                                <a href="${request.contextPath}?command=getLotsByStatus&status=SOLD"> <fmt:message key="label.Sold_lots"/></a>
                                <a href="${request.contextPath}?command=getLotsByStatus&status=NOT_ACTIVE"> <fmt:message key="label.Not_active_lots"/></a>
                            </div>
                        </li>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <li>
                            <button class="dropbtn"> <fmt:message key="label.User_menu"/> &#9660</button>
                            <div class="dropdown-content">
                                <a href="${request.contextPath}?command=getUserLots"> <fmt:message key="label.Your_lots"/></a>
                                <a href="${request.contextPath}?command=getAccountInfo"> <fmt:message key="label.Account_info"/></a>
                                <a href="${request.contextPath}?command=getBalanceInfo"> <fmt:message key="label.Balance_info"/></a>
                            </div>
                        </li>
                    </div>
                </c:otherwise>
            </c:choose>
	        <li><a href="${request.contextPath}?command=showCreateLotPage"> <fmt:message key="label.Suggest_lot"/></a></li>
	        <li><a href="${request.contextPath}?command=logout"> <fmt:message key="label.Logout"/></a></li>
	        <div class="lang-container">
	        <li>
                <div class="en"><a href="${request.contextPath}?command=changeLanguage&locale=en_US">en</a></div>
                <div class="ru"><a href="${request.contextPath}?command=changeLanguage&locale=ru_RU">ru</a></div>
                <div class="by"><a href="${request.contextPath}?command=changeLanguage&locale=be_BY">be</a></div>
            </li>
            </div>
	    </ul>

	</body>
</html>