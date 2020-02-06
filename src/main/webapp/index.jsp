<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <jsp:forward page="/controller">
            <jsp:param name="command" value="showMainPage"/>
        </jsp:forward>
    </body>
</html>