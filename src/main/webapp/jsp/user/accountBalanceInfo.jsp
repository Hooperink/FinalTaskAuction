<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/accountUpdatePage.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showDescription.js"></script>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
	<div class="all-wrap">
        <header>
            <jsp:include page="horizontalMenu.jsp" />
        </header>
        <div class="all-info-container">
            <div class="status-container">
                <form method="get" action="auction">
                    <input type="hidden" name="command" value="getAccountInfo">
                    <div class="status-label"><fmt:message key="label.Status"/>:</div>
                        <select name="status" onchange = 'this.form.submit()'>
                            <option value="ACTIVE" class="option-container" ${status == 'ACTIVE' ? 'selected="selected"' : ''} > <fmt:message key="label.Active"/> </option>
                            <option value="SOLD" class="option-container" ${status == 'SOLD' ? 'selected="selected"' : ''} > <fmt:message key="label.Sold"/> </option>
                            <option value="DELETED" class="option-container" ${status == 'DELETED' ? 'selected="selected"' : ''} > <fmt:message key="label.Deleted"/> </option>
                        </select>
                </form>
            </div>
            <div class="user-info-container">
                <fmt:message key="label.Hello"/>, <b>${user.login}</b>,
                <fmt:message key="label.your_balance_is"/>:
                <ctg:currency-exchange currency="${user.balance}" locale ="${sessionScope.lang}"/>
            </div>
            <div class="update-account-container">
                <form method="post" action="auction">
                    <input type="hidden" name="command" value="updateAccount"/>
                    <input type="text" name="amount" id="place-bet" required>
                    <fmt:message key="label.Update_balance" var="UpdateBalance"/>
                    <input type="submit" value= "${UpdateBalance}"/>
                </form>
            </div>
        </div>
    </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>