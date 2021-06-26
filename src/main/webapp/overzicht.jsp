<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Overzicht | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="current" value="Overzicht"/>
</jsp:include>
<main>
    <img class="fadedImg" src="images/pexels-florent-b-2664149-1920x1298.jpg" alt="flessen wijn">
    <section>
        <h2 class="raisedH2">Bekijk alle wijnen</h2>
        <c:choose>
            <c:when test="${not empty wijnen}">
        <div class="horizontalScroll">
            <table>
                <thead>
                <tr>
                    <th>Naam</th>
                    <th>Soort</th>
                    <th>Jaartal</th>
                    <th>Prijs</th>
                    <th>Omschrijving</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="w" items="${wijnen}">
                    <tr>
                        <td>${w.naam}</td>
                        <td>${w.soort}</td>
                        <td>${w.jaartal}</td>
                        <td>${w.prijs}</td>
                        <td>${w.omschrijving}</td>
                        <td><a href="Servlet?command=verwijderBevestig&naam=${w.formattedNaam}">X</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
            </c:when>
            <c:otherwise>
                <p>De wijnkelder is leeg.<br><a href="voegToe.jsp">Voeg een wijn toe!</a></p>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>