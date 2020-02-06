<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/accountUpdatePage.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showDescription.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	</head>
	<body>
	<div class="all-wrap">
        <header>
            <jsp:include page="horizontalMenu.jsp" />
        </header>
        <div class="all-info-container">
            <div class="status-container">
                <form method="get" action="controller">
                    <input type="hidden" name="command" value="getAccountInfo">
                    <div class="status-label">Status:</div>
                        <select name="status" onchange = 'this.form.submit()'>
                            <option value="ACTIVE" class="option-container" ${status == 'ACTIVE' ? 'selected="selected"' : ''} > Active </option>
                            <option value="SOLD" class="option-container" ${status == 'SOLD' ? 'selected="selected"' : ''} > Sold </option>
                        </select>
                </form>
            </div>
            <div class="user-info-container">
                Hello, <b>${user.login}</b>,
                your balance is: ${user.balance}
            </div>
            <div class="update-account-container">
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="updateAccount"/>
                    <input type="text" name="amount" id="place-bet" required>
                    <input type="submit" value="Update account">
                </form>
            </div>
            <div class="info-label">Your bids in ${status} lots:</div>
            <div class = "lots-info-container">
                <c:forEach items="${lots}" var="lot">
                    <div class="single-lot-info">
                        <div class="lot-name-label">Name: ${lot.name}</div>
                        <c:forEach items="${bets}" var="bet">
                            <c:if test= "${lot.id == bet.lotId}">
                                Sold for: ${bet.bet}
                            </c:if>
                        </c:forEach>
                        <button onclick='showDescription(${lot.id})'>Show description</button>
                        <div class="show-lot-button">
                            <c:if test="${status != 'SOLD'}">
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="getSingleLot"/>
                                    <input type="hidden" name="id" value="${lot.id}"/>
                                    <input type="submit" value="Show lot page"/>
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