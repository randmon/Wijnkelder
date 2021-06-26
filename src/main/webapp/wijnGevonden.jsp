<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wijnen gevonden | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="header.jsp">
        <jsp:param name="current" value="VindWijn"/>
    </jsp:include>
    <main>
        <h2>Wijnen gevonden</h2>
        <p>We hebben volgende wijnen van jaartal <b>${jaartal}</b>:</p>
        <div class="horizontalScroll">
        <table>
            <thead>
            <tr>
                <th>Naam</th>
                <th>Soort</th>
                <th>Jaartal</th>
                <th>Prijs</th>
                <th>Omschrijving</th>
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
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>

        <h3>Zoekopdrachten</h3>
        <p>U heeft al ${aantalZoekOpdrachten} succesvolle zoekresultaten uitgevoerd.</p>
    </main>
</body>
</html>