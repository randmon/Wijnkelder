<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vind Wijn | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="header.jsp">
        <jsp:param name="current" value="VindWijn"/>
    </jsp:include>
    <main>
        <h2>Vind wijn</h2>
        <div class="imageSide">
            <div class="noSplit">
        <c:if test="${not empty errors}">
            <div id="error">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <p>Wil je snel de wijn vinden van een bepaald jaar? Vul hieronder het jaartal in.
            We tonen je alle wijnen van het gegeven jaartal.</p>
        <form action="Servlet?command=zoek" method="GET" novalidate>
            <label for="jaartal">Gezochte jaartal:</label>
            <input class="inputNaastLabel" id="jaartal" name="jaartal" type="number" value="${jaartal}"> <br><br>
            <input type="hidden" name="command" value="zoek">
            <input type="submit" value="Zoek" id="zoek">
        </form>
            </div>
        <img src="images/pexels-pixabay-39511.jpg" alt="druiven">
        </div>
    </main>
</body>
</html>