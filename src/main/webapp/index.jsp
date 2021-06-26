<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="current" value="Home"/>
</jsp:include>
<main>
    <img class="fadedImg" src="images/pexels-grape-things-2647933-1920x1279.jpg" alt="flessen wijn">
    <section>
        <h2 class="raisedH2">Maak kennis met de inhoud van mijn wijnkelder</h2>
        <h3>Kenmerken van een wijn</h3>
        <ul>
            <li>Naam</li>
            <li>Soort</li>
            <li>Jaartal</li>
            <li>Prijs</li>
            <li>Korte omschrijving van de wijn (maximaal 200 karakters)</li>
        </ul>
    </section>

    <c:if test="${not empty gemiddelde}">
        <section>
            <h3>Gemiddelde prijs</h3>
            <p>Het gemiddelde prijs van alle wijnen is - ${gemiddelde}€</p>
        </section>
        <section>
            <h3>Duurste fles</h3>
            <ul>
                <li>Naam: ${duurste.naam}</li>
                <li>Soort: ${duurste.soort}</li>
                <li>Jaartal: ${duurste.jaartal}</li>
                <li>Prijs: ${duurste.prijs}€</li>
                <li>Omschrijving: ${duurste.omschrijving}</li>
            </ul>
        </section>
    </c:if>
</main>
</body>
</html>