<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
	</head>
	<body>
	    <ul class="menu-main">
	        <li><a href="${request.contextPath}controller?command=showMainPage">Main page</a></li>
	        <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <div class="dropdown">
                        <li>
                            <button class="dropbtn">ADMIN &#9660</button>
                            <div class="dropdown-content">
                                <a href="${request.contextPath}controller?command=getUsers">User menu</a>
                                <a href="${request.contextPath}controller?command=getLotsByStatus&status=MODERATION">Lots for moderation</a>
                                <a href="${request.contextPath}controller?command=getLotsByStatus&status=SOLD">Sold lots</a>
                                <a href="${request.contextPath}controller?command=getLotsByStatus&status=NOT_ACTIVE">Not active lots</a>
                            </div>
                        </li>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <li>
                            <button class="dropbtn">USER &#9660</button>
                            <div class="dropdown-content">
                                <a href="${request.contextPath}controller?command=getUserLots">Your lots</a>
                                <a href="${request.contextPath}controller?command=getAccountInfo">Account info</a>
                            </div>
                        </li>
                    </div>
                </c:otherwise>
            </c:choose>
	        <li><a href="${request.contextPath}controller?command=showCreateLotPage">Create lot</a></li>
	        <li><a href="${request.contextPath}controller?command=logout">Logout</a></li>
	    </ul>
	</body>
</html>