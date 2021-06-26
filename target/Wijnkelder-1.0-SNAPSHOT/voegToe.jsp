<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voeg Toe | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="current" value="VoegToe"/>
</jsp:include>
<main>
    <section>
        <h2>Voeg een wijn toe</h2>
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
                <form action="Servlet?command=voegtoe" method="POST" novalidate>
                    <!-- novalidate in order to be able to run tests correctly -->
                    <label for="naam">Naam</label><br>
                    <input type="text" id="naam" name="naam" value="${vorigeNaam}" required><br><br>
                    <label for="soort">Soort</label><br>
                    <input type="text" id="soort" name="soort" value="${vorigeSoort}" required><br><br>
                    <label for="jaartal">Jaartal</label><br>
                    <input type="number" id="jaartal" name="jaartal" value="${vorigeJaartal}" required><br><br>
                    <label for="prijs">Prijs</label><br>
                    <input type="number" id="prijs" name="prijs" value="${vorigePrijs}" required><br><br>
                    <label for="omschrijving">Omschrijving</label><br>
                    <input type="text" id="omschrijving" name="omschrijving" value="${vorigeOmschrijving}" required><br><br>
                    <input type="submit" id="voegToe" value="Voeg Toe">
                </form>
            </div>
            <img src="images/pexels-bruno-cantuária-774455-1280x1926.jpg" alt="flessen wijn">
        </div>
    </section>
</main>
</body>
</html>