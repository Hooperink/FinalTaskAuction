<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/createLot.css"/>
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
                        <form name="edit-form" method="post" action="controller">
                            <input type="hidden" name="command" value="placeBet"/>
                            <input type="hidden" name="lotId" value="${lot.id}"/>
                            <input type="hidden" name="betId" value="${bet.id}"/>
                            <div class="info-container">
                                <div class="name-input">
                                    <div class="label">Name:</div>
                                    ${lot.name}
                                </div>
                                <div class="date">
                                    <div class="start-date-label">
                                        Start of sales:
                                    </div>
                                    <fmt:formatDate value= "${lot.startSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                                </div>
                            </div>
                            <div class="description">
                                <div class="label">Description: </div>
                                <div class="description-input">
                                    <textarea cols="40" rows="15" readonly required>${lot.description} </textarea>
                                </div>
                            </div>
                            <div class="price-info">
                                    <div class="label"> Price: </div>
                                    ${lot.price}
                            </div>
                            <div class="bet-container">
                               <div class="bet-label">Last bet:</div>
                               <c:out value= "${bet.bet == null ? 'None' : bet.bet}"/>
                            </div>
                            <div class="bidder-container">
                                <div class="last-bet-label">Bidder: </div>
                                <c:out value= "${user.login == null ? 'None' : user.login}"/>
                            </div>
                            <div class="bet-input-container">
                                <div class="bet-label">Bet: </div>
                                <input type="text" name="betAmount" id="place-bet">
                            </div>
                            <div class="message-container">${requestScope.message}</div>
                            <input type="submit" value="Place bet">
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class ="form-container">
                        <form name="edit-form" method="post" action="controller">
                            <input type="hidden" name="command" value="editLot"/>
                            <input type="hidden" name="lotId" value="${lot.id}"/>
                            <input type="hidden" name="betId" value="${bet.id}"/>
                            <div class="info-container">
                                <div class="name-input">
                                    <div class="label">Name:</div>
                                    <input type="text" name="name" required minlength="5" maxlength="25" value="${lot.name}">
                                </div>
                                <div class="price-info">
                                    <div class="label"> Price: </div>
                                    ${lot.price}
                                </div>
                                <div class="date">
                                    <div class="start-date-label">
                                        Start of sales:
                                    </div>
                                    <fmt:formatDate value= "${lot.startSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                                </div>
                            </div>
                            <div class="bet-container">
                               <div class="label">Last bet:</div>
                               <c:out value= "${bet.bet == null ? 'None' : bet.bet}"/>
                            </div>
                            <div class="bidder-container">
                                <div class="label">Bidder: </div>
                                <c:out value= "${user.login == null ? 'None' : user.login}"/>
                            </div>
                            <div class="description">
                                <div class="description-label">Description: </div>
                                <div class="description-input">
                                    <textarea name="description" cols="40" rows="15" required>${lot.description}</textarea>
                                </div>
                             </div>
                            <input type="submit" value="Edit">
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