<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verwijder | Wijnkelder</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="current" value="Overzicht"/>
</jsp:include>
<main>
    <section>
        <h2>Deze wijn verwijderen?</h2>
        <div class="imageSide">
            <div class="noSplit">
                <h3>${wijn.naam}</h3>
                <ul>
                    <li>Soort: ${wijn.soort}</li>
                    <li>Jaartal: ${wijn.jaartal}</li>
                    <li>Prijs: ${wijn.prijs}€</li>
                    <li>Omschrijving: ${wijn.omschrijving}</li>
                </ul>
                <form action="Servlet?command=verwijder&naam=${wijn.naam}" method="post">
                    <input type="submit" value="Verwijderen"> <a class="greyButton" href="Servlet?command=overzicht">Terug</a>
                </form>
            </div>
            <img src="images/pexels-natalie-3365560-1280x853.jpg" alt="Wijn, kaas en fruit">
        </div>
    </section>
</main>
</body>
</html>