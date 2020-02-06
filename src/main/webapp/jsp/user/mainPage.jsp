<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
	<div class="all-wrap">
        <header>
            <jsp:include page="horizontalMenu.jsp" />
        </header>
            <jsp:include page="lotPage.jsp"/>
    </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>