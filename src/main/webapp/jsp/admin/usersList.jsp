<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/switch.css"/>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userMenu.css"/>
	</head>

	<body>
	    <div class="list-container">
	        <table>
	            <tr>
	            <th>Name:</th>
	            <th>Balance:</th>
	            <th>Role:</th>
	            <th>Is banned:</th>
                    <c:forEach items="${users}" var="user" varStatus="loop">
                    <tr>
                        <td>Name: "${user.login}"</td>
                        <td>Balance: "${user.balance}"</td>
                        <td>Role: "${user.role}"</td>
                        <td>
                            <div class="button-container">
                                <form name="activity" method="post" action="controller" id="activity${user.id}">
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