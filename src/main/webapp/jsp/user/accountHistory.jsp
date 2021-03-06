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
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
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
            <div class="info-label"><fmt:message key="label.Your_bids_in_lots_with_status"/> <fmt:message key="label.${status}"/>:</div>
            <div class = "lots-info-container">
                <c:forEach items="${lots}" var="lot">
                    <div class="single-lot-info">
                        <div class="lot-name-label"><fmt:message key="label.Name"/>: ${lot.name}</div>
                        <c:forEach items="${bets}" var="bet">
                            <c:if test= "${lot.id == bet.lotId}">
                                <fmt:message key="label.Sold_for"/>: ${bet.bet}
                            </c:if>
                        </c:forEach>
                        <button onclick='showDescription(${lot.id})'><fmt:message key="label.Show_description"/></button>
                        <div class="show-lot-button">
                            <c:if test="${status == 'ACTIVE'}">
                                <form action="auction" method="get">
                                    <input type="hidden" name="command" value="getSingleLot"/>
                                    <input type="hidden" name="id" value="${lot.id}"/>
                                    <fmt:message key="label.Show_lot_page" var="showLotPage"/>
                                    <input type="submit" value="${showLotPage}"/>
                                </form>
                            </c:if>
                        </div>
                        <div class="description" id="${lot.id}" style="display:none;">
                            ${lot.description}
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>