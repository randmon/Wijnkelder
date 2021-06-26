package ui.controller;

import domain.DomainException;
import domain.db.WijnDBInMemory;
import domain.model.Wijn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    public WijnDBInMemory wijnDB = new WijnDBInMemory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = "home";
        if (request.getParameter("command") != null) {
            command = request.getParameter("command");
        }
        String destination;
        switch (command) {
            case "home":
                destination = home(request);
                break;
            case "overzicht":
                destination = overzicht(request);
                break;
            case "voegtoe":
                destination = voegToe(request);
                break;
            case "verwijderBevestig":
                destination = verwijderBevestig(request);
                break;
            case "verwijder":
                destination = verwijder(request);
                break;
            case "zoek":
                destination = zoek(request, response);
                break;
            case "logboek":
                destination = logboek(request);
                break;
            default:
                destination = home(request);
        }
        request.getRequestDispatcher(destination).forward(request, response);
    }

    private String home(HttpServletRequest request) {
        double gemiddelde = wijnDB.getGemiddeldePrijs();
        request.setAttribute("gemiddelde", gemiddelde);
        Wijn duurste = wijnDB.getDuursteWijn();
        request.setAttribute("duurste", duurste);
        addToLogboek(request, "Home");
        return "index.jsp";
    }

    private String overzicht(HttpServletRequest request) {
        request.setAttribute("wijnen", wijnDB.getAlleWijnen());
        addToLogboek(request, "Overzicht");
        return "overzicht.jsp";
    }

    private String voegToe(HttpServletRequest request) {
        ArrayList<String> errors = new ArrayList<>();
        Wijn wijn = new Wijn();
        setNaam(wijn, request, errors);
        setSoort(wijn, request, errors);
        setJaartal(wijn, request, errors);
        setPrijs(wijn, request, errors);
        setOmschrijving(wijn, request, errors);

        if (errors.isEmpty()) {
            //Geen errors gevonden
            try {
                wijnDB.voegToe(wijn); //Domain exception wordt hier gegooid als deze wijn al bestaat
            } catch (DomainException e) {
                errors.add(e.getMessage());
                request.setAttribute("errors", errors);
                request.setAttribute("vorigeNaam", "");
                addToLogboek(request, "Voeg Toe (error)");
                return "voegToe.jsp";
            }
            addToLogboek(request, "Voeg Toe - " + request.getParameter("naam"));
            request.setAttribute("wijnen", wijnDB.getAlleWijnen());
            return "overzicht.jsp";
        } else {
            request.setAttribute("errors", errors);
            addToLogboek(request, "Voeg Toe (error)");
            return "voegToe.jsp";
        }
    }

    private void setNaam(Wijn wijn, HttpServletRequest request, ArrayList<String> errors) {
        String naam = request.getParameter("naam");
        try {
            wijn.setNaam(naam);
            request.setAttribute("vorigeNaam", naam);
        } catch (DomainException e) {
            errors.add(e.getMessage());
        }
    }

    private void setSoort(Wijn wijn, HttpServletRequest request, ArrayList<String> errors) {
        String soort = request.getParameter("soort");
        try {
            wijn.setSoort(soort);
            request.setAttribute("vorigeSoort", soort);
        } catch (DomainException e) {
            errors.add(e.getMessage());
        }
    }

    private void setJaartal(Wijn wijn, HttpServletRequest request, ArrayList<String> errors) {
        String jaartal = request.getParameter("jaartal");
        try {
            wijn.setJaartal(jaartal);
            request.setAttribute("vorigeJaartal", jaartal);
        } catch (DomainException e) {
            errors.add(e.getMessage());
        }
    }

    private void setPrijs(Wijn wijn, HttpServletRequest request, ArrayList<String> errors) {
        String prijs = request.getParameter("prijs");
        try {
            wijn.setPrijs(prijs);
            request.setAttribute("vorigePrijs", prijs);
        } catch (DomainException e) {
            errors.add(e.getMessage());
        }
    }

    private void setOmschrijving(Wijn wijn, HttpServletRequest request, ArrayList<String> errors) {
        String omschrijving = request.getParameter("omschrijving");
        try {
            wijn.setOmschrijving(omschrijving);
            request.setAttribute("vorigeOmschrijving", omschrijving);
        } catch (DomainException e) {
            errors.add(e.getMessage());
        }
    }

    private String verwijderBevestig(HttpServletRequest request) {
        try {
            String naam = request.getParameter("naam");
            Wijn wijn = wijnDB.vindWijn(naam);
            if (wijn == null) throw new DomainException();
            request.setAttribute("wijn", wijn);
            return "verwijderBevestig.jsp";
        } catch (DomainException e) {
            return overzicht(request);
        }
    }

    private String verwijder(HttpServletRequest request) {
        String naam = request.getParameter("naam");
        wijnDB.verwijder(naam);
        addToLogboek(request, "Verwijder - " + naam);
        request.setAttribute("wijnen", wijnDB.getAlleWijnen());
        return "overzicht.jsp";
    }

    private String zoek(HttpServletRequest request, HttpServletResponse response) {
        String jaartal = request.getParameter("jaartal");
        request.setAttribute("jaartal", jaartal);
        try {
            ArrayList<Wijn> wijnen = wijnDB.vindWijnenVanJaartal(jaartal);
            request.setAttribute("wijnen", wijnen);
            addAantalOpdrachtenToRequest(request, response, getZoekOpdrachtenCookie(request));
            addToLogboek(request, "Vind Wijn - " + jaartal);
            return "wijnGevonden.jsp";
        } catch (DomainException e) {
            request.setAttribute("errors", e.getMessage());
            addToLogboek(request, "Vind Wijn (error)");
            return "vindWijn.jsp";
        }
    }

    private void addAantalOpdrachtenToRequest(HttpServletRequest request, HttpServletResponse response, Cookie aantalZoekOpdrachten) {
        if (aantalZoekOpdrachten == null) {
            Cookie cookie = new Cookie("aantalZoekOpdrachten", "1");
            response.addCookie(cookie);
            request.setAttribute("aantalZoekOpdrachten", "1");
        } else {
            String aantal = String.valueOf(Integer.parseInt(aantalZoekOpdrachten.getValue())+1);
            Cookie cookie = new Cookie("aantalZoekOpdrachten", aantal);
            response.addCookie(cookie);
            request.setAttribute("aantalZoekOpdrachten", aantal);
        }
    }

    private Cookie getZoekOpdrachtenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (c.getName().equals("aantalZoekOpdrachten")) return c;
        }
        return null;
    }

    //SESSION
    private void addToLogboek(HttpServletRequest request, String activiteit) {
        HttpSession session = request.getSession();
        Map<String, String> logboek;
        logboek = (Map<String, String>) session.getAttribute("logboek");
        if (logboek == null) logboek = new LinkedHashMap<>();
        logboek.put(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSS")), activiteit);
        session.setAttribute("logboek", logboek);
    }

    private String logboek(HttpServletRequest request) {
        //addToLogboek(request, "Logboek");
        return "logboek.jsp";
    }
}


