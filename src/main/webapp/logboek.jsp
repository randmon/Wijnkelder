<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logboek | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="current" value="Logboek"/>
</jsp:include>
    <main>
    <h2>Bekijk mijn activiteiten op deze website</h2>
    <div class="imageSide">
        <div class="noSplit">
            <c:choose>
                <c:when test="${not empty logboek}">
                    <ul>
                        <c:forEach var="l" items="${logboek}">
                            <li>${l.key} | ${l.value}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p>Nog geen activiteit.</p>
                </c:otherwise>
            </c:choose>
        </div>
        <img src="images/pexels-oleg-magni-1005639-1920%20x%202546.jpg" alt="wijn kelder">
    </div>
    </main>
</body>
</html>