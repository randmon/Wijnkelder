<header>
    <h1>Mijn Wijnkelder</h1>
    <nav>
        <nav>
            <ul>
                <li><a ${param.current eq 'Home'?"class = current":""} href="Servlet?command=home">Home</a></li>
                <li><a ${param.current eq 'Overzicht'?"class = current":""} href="Servlet?command=overzicht">Overzicht</a></li>
                <li><a ${param.current eq 'VoegToe'?"class = current":""} href="voegToe.jsp">Voeg Toe</a></li>
                <li><a ${param.current eq 'VindWijn'?"class = current":""} href="vindWijn.jsp">Vind Wijnen</a></li>
                <li><a ${param.current eq 'Logboek'?"class = current":""} href="Servlet?command=logboek">Mijn Logboek</a></li>
            </ul>
        </nav>
    </nav>
</header>