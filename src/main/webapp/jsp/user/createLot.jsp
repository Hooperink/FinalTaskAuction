<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>

<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/createLot.css"/>
	    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	</head>
	<body>
        <div class="all-wrap">
            <header>
                <jsp:include page="horizontalMenu.jsp" />
            </header>
            <div class ="form-container">
                <form name="lot-form" action="auction" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="command" value= "createLot">
                    <div class="input">
                        <div class="name-input">
                            <div class="label"> <fmt:message key="label.Lot_name"/>:</div>
                            <input type="text" name="name" required minlength="5" maxlength="25">
                        </div>
                        <div class="price-input">
                            <div class="label">  <fmt:message key="label.Price"/>: </div>
                            <input type="text" name="price" id="place-bet" required>
                        </div>
                    </div>
                    <div class="description">
                        <div class="label"> <fmt:message key="label.Description"/>: </div>
                        <div class="description-input">
                            <fmt:message key="label.Enter_Description" var="enterDescription"/>
                            <textarea name="description" placeholder= "${enterDescription}" cols="40" rows="15" required maxlength="240"></textarea>
                        </div>
                    </div>
                    <input type="file" name="filecover" value="Upload"/>
                    <fmt:message key="label.Suggest_lot" var="suggestLot"/>
                    <input type="submit" value="${suggestLot}">
                </form>
            </div>
        </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>