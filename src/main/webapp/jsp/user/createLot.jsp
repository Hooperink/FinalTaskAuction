<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/inputValidator.js"></script>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/createLot.css"/>
	</head>
	<body>
        <div class="all-wrap">
            <header>
                <jsp:include page="horizontalMenu.jsp" />
            </header>
            <div class ="form-container">
                <form name="lot-form" action="controller" method="post">
                    <input type="hidden" name="command" value="createLot">
                    <div class="input">
                        <div class="name-input">
                            <div class="label">Name:</div>
                            <input type="text" name="name" required minlength="5" maxlength="25">
                        </div>
                        <div class="price-input">
                            <div class="label"> Price: </div>
                            <input type="text" name="price" id="place-bet" required>
                        </div>
                    </div>
                    <div class="description">
                        <div class="label">Description: </div>
                        <div class="description-input">
                            <textarea name="description" placeholder="Enter description." cols="40" rows="15" required></textarea>
                        </div>
                    </div>
                    <input type="submit" value="Create lot">
                </form>
            </div>
        </div>
    </body>
	<footer>
        <jsp:include page="footer.jsp"/>
	</footer>
</html>