<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lotStyle.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showDeleteButton.js"></script>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
	    <div class = "lot-display">
            <c:forEach items="${lots}" var="lot">
                <div class="border">
                    <div class="product-wrap">
                        <a href="${request.contextPath}?command=getSingleLot&id=${lot.id}"><img src="${pageContext.request.contextPath}/images/${lot.imagePath}"></a>
                        <div class="loop-action">
                            <a href="${request.contextPath}?command=getSingleLot&id=${lot.id}" class="add-to-cart"><fmt:message key="label.Show_lot"/></a>
                            <c:if test="${sessionScope.role == 'ADMIN'}">
                                <a href="#" onclick="openForm(${lot.id}); return false;"><fmt:message key="label.Delete"/></a>
                                <div class="form-popup" id="${lot.id}">
                                    <fmt:message key="label.Are_you_sure?"/>
                                    <a href="${request.contextPath}?command=deleteLot&id=${lot.id}"> <fmt:message key="label.Delete"/></a>
                                    <a href="#" onclick="closeForm(${lot.id}); return false;"><fmt:message key="label.Close"/></a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="product-info">
                        <div class="product-title"><c:out value="${lot.name}"/></div>
                        <div class="price-bet-container">
                            <div class="price"> <fmt:message key="label.Start_bet"/>: <ctg:currency-exchange currency="${lot.price}" locale ="${sessionScope.lang}"/></div>
                            <c:forEach items="${bets}" var="bet">
                                <c:if test="${lot.id == bet.lotId}">
                                    <div class="bet">
                                         <fmt:message key="label.Current_bet"/>: <ctg:currency-exchange currency="${bet.bet}" locale ="${sessionScope.lang}"/>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${amountOfPages > 1}">
            <div class="pagination">
                <div class="previous-pagination">
                    <c:if test="${currentPage != 1}">
                        <a href="${request.contextPath}?command=getListLot&page=${currentPage - 1}"> <fmt:message key="label.Previous"/></a>
                    </c:if>
                </div>
                <div class="number-pagination">
                    <c:forEach begin="1" end="${amountOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <div class = "active">${i}</div>
                            </c:when>
                            <c:otherwise>
                                <a href="${request.contextPath}?command=getListLot&page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="next-pagination">
                    <c:if test="${currentPage lt amountOfPages}">
                        <a href="${request.contextPath}?command=getListLot&page=${currentPage + 1}"> <fmt:message key="label.Next"/></a>
                    </c:if>
                </div>
            </div>
        </c:if>
	</body>
</html>