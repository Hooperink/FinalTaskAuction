<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lotStyle.css"/>
	</head>
	<body>
	    <div class = "lot-display">
            <c:forEach items="${lots}" var="lot">
                <div class="border">
                    <div class="product-wrap">
                        <a href="${request.contextPath}controller?command=getSingleLot&id=${lot.id}"><img src="https://185504.selcdn.ru/static/dolinaroz.reshop.by/catalog/764/4818233015c3519b99ec8f_medium.jpg"></a>
                        <div class="loop-action">
                            <a href="${request.contextPath}controller?command=getSingleLot&id=${lot.id}" class="add-to-cart">Просмотр</a>
                            <c:if test="${sessionScope.role == 'ADMIN'}">
                                <a href="${request.contextPath}controller?command=deleteLot&id=${lot.id}" class="add-to-cart">Удалить</a>
                            </c:if>
                        </div>
                    </div>
                    <div class="product-info">
                        <div class="product-title"><c:out value="${lot.name}"/></div>
                        <div class="price-bet-container">
                            <div class="price">Start bet: ${lot.price}</div>
                            <c:forEach items="${bets}" var="bet">
                                <c:if test="${lot.id == bet.lotId}">
                                    <div class="bet">
                                        Current bet: <c:out value= "${bet.bet}"/>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="pagination">
            <div class="previous-pagination">
                <c:if test="${currentPage != 1}">
                    <a href="${request.contextPath}controller?command=getListLot&page=${currentPage - 1}">Previous</a>
                </c:if>
            </div>
            <div class="number-pagination">
                <c:forEach begin="1" end="${amountOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <div class = "active">${i}</div>
                        </c:when>
                        <c:otherwise>
                            <a href="${request.contextPath}controller?command=getListLot&page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="next-pagination">
                <c:if test="${currentPage lt amountOfPages}">
                    <a href="${request.contextPath}controller?command=getListLot&page=${currentPage + 1}">Next</a>
                </c:if>
            </div>
        </div>
	</body>
</html>