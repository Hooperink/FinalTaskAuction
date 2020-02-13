<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/switch.css"/>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userMenu.css"/>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>

	<body>
	    <div class="list-container">
	        <table>
	            <tr>
                    <th><fmt:message key="label.Name"/>:</th>
                    <th><fmt:message key="label.Balance"/>:</th>
                    <th><fmt:message key="label.Role"/>:</th>
                    <th><fmt:message key="label.Activity"/>:</th>
                    <c:forEach items="${users}" var="user" varStatus="loop">
                        <tr>
                            <td>"${user.login}"</td>
                            <td>"${user.balance}"</td>
                            <fmt:message key="label.Administrator" var="roleAdmin"/>
                            <fmt:message key="label.User" var="roleUser"/>
                            <td>"${user.role == 'ADMIN' ? roleAdmin : roleUser }"</td>
                            <fmt:message key="label.Active" var="statusActive"/>
                            <fmt:message key="label.Not_active" var="statusNotActive"/>
                            <td>"${user.isActive ? statusActive : statusNotActive}"</td>
                            <td>
                                <div class="button-container">
                                    <form name="activity" method="post" action="auction" id="activity${user.id}">
                                        <input type="hidden" name="id" value="${user.id}">
                                        <input type="hidden" name="command" value="changeActivity">
                                            <label class="switch" onchange="document.getElementById('activity${user.id}').submit();">
                                                <input type="checkbox" <c:out value = "${user.isActive ? 'checked' : 'unchecked'}"/>  >
                                                <div class="slider round"></div>
                                            </label>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </body>
</html>