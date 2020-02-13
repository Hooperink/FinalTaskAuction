<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/createLotUser.css"/>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
        <div class = "all-wrap">
            <header>
                <jsp:include page="horizontalMenu.jsp"/>
            </header>
            <c:set var="lot" value='${requestScope.lot}'/>
            <c:set var="bet" value='${requestScope.bet}'/>
            <c:set var="user" value='${requestScope.bidder}'/>
            <c:choose>
                <c:when test="${sessionScope.id != lot.sellerId}">
                    <div class ="form-container">
                        <form name="edit-form" method="post" action="auction">
                            <input type="hidden" name="command" value="placeBet"/>
                            <input type="hidden" name="lotId" value="${lot.id}"/>
                            <input type="hidden" name="betId" value="${bet.id}"/>
                            <div class="info-container">
                                <div class="name-input">
                                    <div class="label"> <fmt:message key="label.Lot_name"/>:</div>
                                    ${lot.name}
                                </div>
                                <div class="date">
                                    <div class="start-date-label">
                                         <fmt:message key="label.Start_of_sales"/>:
                                    </div>
                                    <fmt:formatDate value= "${lot.startSellDate}"/>
                                </div>
                                <div class="status">
                                    <div class="status-label">
                                        <fmt:message key="label.Status"/>:
                                    </div>
                                    <fmt:message key="label.${lot.status}"/>
                                </div>
                            </div>
                            <div class="description">
                                <div class="label"> <fmt:message key="label.Description"/>: </div>
                                <div class="description-container">
                                    <div class="description-text">${lot.description}</div>
                                </div>
                            </div>
                            <div class="price-info">
                                    <div class="label">  <fmt:message key="label.Price"/>: </div>
                                    ${lot.price}
                            </div>
                            <div class="bet-container">
                               <fmt:message key="label.None" var="NoneValue"/>
                               <div class="bet-label"> <fmt:message key="label.Last_bet"/>:</div>
                               <c:out value= "${bet.bet == null ? NoneValue : bet.bet}"/>
                            </div>
                            <div class="bidder-container">
                                <div class="last-bet-label"><fmt:message key="label.Bidder"/>: </div>
                                <c:out value= "${user.login == null ? NoneValue : user.login}"/>
                            </div>
                            <div class="bet-input-container">
                                <div class="bet-label"><fmt:message key="label.Bet"/>:</div>
                                <input type="text" name="betAmount" id="place-bet">
                            </div>
                            <fmt:message key="label.Place_bet" var="placeBet"/>
                            <input type="submit" value="${placeBet}">
                        </form>
                        <c:if test="${requestScope.message != null}">
                            <div class="message-container"><fmt:message key="label.${requestScope.message}"/></div>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class ="form-container">
                        <form name="edit-form" method="post" action="auction">
                            <input type="hidden" name="command" value="editLot"/>
                            <input type="hidden" name="lotId" value="${lot.id}"/>
                            <input type="hidden" name="betId" value="${bet.id}"/>
                            <div class="info-container">
                                <div class="name-input">
                                    <div class="label"><fmt:message key="label.Lot_name"/>:</div>
                                    <input type="text" name="name" required minlength="5" maxlength="25" value="${lot.name}">
                                </div>
                                <div class="price-info">
                                    <div class="label"> <fmt:message key="label.Price"/>: </div>
                                    ${lot.price}
                                </div>
                                <div class="date">
                                    <div class="start-date-label">
                                        <fmt:message key="label.Start_of_sales"/>:
                                    </div>
                                    <fmt:formatDate value= "${lot.startSellDate}"/>
                                </div>
                                <div class="status">
                                    <div class="status-label">
                                        <fmt:message key="label.Status"/>:
                                    </div>
                                    <fmt:message key="label.${lot.status}"/>
                                </div>
                            </div>
                            <fmt:message key="label.None" var="NoneValue"/>
                            <div class="bet-container">
                               <div class="label"><fmt:message key="label.Last_bet"/>:</div>
                               <c:out value= "${bet.bet == null ? NoneValue : bet.bet}"/>
                            </div>
                            <div class="bidder-container">
                                <div class="label"><fmt:message key="label.Bidder"/>: </div>
                                <c:out value= "${user.login == null ? NoneValue : user.login}"/>
                            </div>
                            <div class="description">
                                <div class="description-label"><fmt:message key="label.Description"/>: </div>
                                <div class="description-input">
                                    <textarea name="description" cols="40" rows="15" maxlength="240" required>${lot.description}</textarea>
                                </div>
                             </div>
                            <input type="submit" value=<fmt:message key="label.Edit"/>>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>