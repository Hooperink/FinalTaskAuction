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
                <jsp:include page="/jsp/user/horizontalMenu.jsp" />
            </header>
            <c:set var="lot" value='${requestScope.lot}' />
            <c:set var="bet" value='${requestScope.bet}' />

            <div class ="form-container">
                <form name="edit-form" method="post" action="controller">
                    <div class="dropdown-status">
                        <div class="status-label">Status: </div>
                        <c:if test="${lot.status == 'SOLD'}">
                            SOLD
                        </c:if>
                        <c:if test="${lot.status != 'SOLD'}">
                            <select name = "lotStatus">
                                <option value="MODERATION" class="option-container" ${lot.status == 'MODERATION' ? 'selected="selected"' : ''}> Moderation </option>
                                <option value="ACTIVE" class="option-container" ${lot.status == 'ACTIVE' ? 'selected="selected"' : ''}> Active </option>
                                <option value="NOT_ACTIVE" class="option-container" ${lot.status == 'NOT_ACTIVE' ? 'selected="selected"' : ''}> Not active </option>
                                <c:if test="${bet != null}">
                                    <option value="SOLD" class="option-container" ${lot.status == 'SOLD' ? 'selected="selected"' : ''}> Sold </option>
                                </c:if>
                            </select>
                        </c:if>
                    </div>
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
                        <c:if test="${lot.status == 'SOLD'}">
                            <div class="sold-info">
                                <div class="label"> Sold for: </div>
                                ${bet.bet}
                            </div>
                        </c:if>
                        <div class="start-date">
                            <div class="label">
                                Start of sales:
                            </div>
                                <fmt:formatDate value= "${lot.startSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                        </div>
                        <c:if test="${lot.endSellDate != null}">
                            <div class="end-date">
                                <div class="label">
                                    End of sales:
                                </div>
                                <fmt:formatDate value= "${lot.endSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                             </div>
                        </c:if>
                    </div>
                    <div class="description">
                        <div class="label">Description: </div>
                        <div class="description-input">
                            <textarea name="description" cols="40" rows="15" required>${lot.description}</textarea>
                        </div>
                     </div>
                    <input type="submit" value="Edit">
                </form>
            </div>
        </div>
    </body>
	<footer>
        <jsp:include page="/jsp/user/footer.jsp"/>
	</footer>
</html>