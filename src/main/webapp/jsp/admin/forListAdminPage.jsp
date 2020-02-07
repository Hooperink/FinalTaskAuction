<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
        <div class="all-wrap">
            <header>
                <jsp:include page="/jsp/user/horizontalMenu.jsp"/>
            </header>
                <jsp:include page="lotsAdminList.jsp"/>
        </div>
    </body>
	<footer>
        <jsp:include page="/jsp/user/footer.jsp"/>
	</footer>
</html>