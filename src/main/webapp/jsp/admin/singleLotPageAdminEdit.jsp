<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/createLot.css"/>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/radioButtons.css"/>
	</head>
	<body>
        <div class = "all-wrap">
            <header>
                <jsp:include page="/jsp/user/horizontalMenu.jsp" />
            </header>
            <c:set var="lot" value='${requestScope.lot}' />
            <c:set var="bet" value='${requestScope.bet}' />

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
                        <c:if test="${lot.status == 'SOLD'}">
                            <div class="sold-info">
                                <div class="label"> <fmt:message key="label.Sold_for"/>: </div>
                                ${bet.bet}
                            </div>
                        </c:if>
                        <div class="start-date">
                            <div class="label">
                                <fmt:message key="label.Start_of_sales"/>:
                            </div>
                                <fmt:formatDate value= "${lot.startSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                        </div>
                        <c:if test="${lot.endSellDate != null}">
                            <div class="end-date">
                                <div class="label">
                                    <fmt:message key="label.End_of_sales"/>:
                                </div>
                                <fmt:formatDate value= "${lot.endSellDate}" pattern="MM/dd/yyyy HH:mm"/>
                             </div>
                        </c:if>
                    </div>
                    <c:if test="${lot.status != 'SOLD'}">
                        <div class="dropdown-status">
                        <div class="status-label"><fmt:message key="label.Status"/>: </div>
                            <div class="switch-field">
                                <input type="radio" id="radio-one" name="lotStatus" value="ACTIVE" ${lot.status == 'ACTIVE' ? 'checked' : ''}/>
                                <label for="radio-one"><fmt:message key="label.Active"/></label>

                                <input type="radio" id="radio-two" name="lotStatus" value="NOT_ACTIVE" ${lot.status == 'NOT_ACTIVE' ? 'checked' : ''}/>
                                <label for="radio-two"><fmt:message key="label.Not_active"/></label>

                                <input type="radio" id="radio-three" name="lotStatus" value="MODERATION" ${lot.status == 'MODERATION' ? 'checked' : ''}/>
                                <label for="radio-three"><fmt:message key="label.Moderation"/></label>

                                <c:if test="${bet != null}">
                                    <input type="radio" id="radio-four" name="lotStatus" value="SOLD" ${lot.status == 'SOLD' ? 'checked' : ''}/>
                                    <label for="radio-four"><fmt:message key="label.Sold"/></label>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                    <div class="image-container">
                        <img src="${pageContext.request.contextPath}/images/${lot.imagePath}">
                    </div>
                    <div class="description">
                        <div class="description-label"><fmt:message key="label.Description"/>: </div>
                        <div class="description-input">
                            <textarea name="description" cols="40" rows="15" required maxlength="240">${lot.description}</textarea>
                        </div>
                     </div>
                    <input type="submit" value= <fmt:message key="label.Edit"/>>
                </form>
            </div>
        </div>
    </body>
	<footer>
        <jsp:include page="/jsp/user/footer.jsp"/>
	</footer>
</html>