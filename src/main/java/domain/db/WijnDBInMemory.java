package domain.db;

import domain.DomainException;
import domain.model.Wijn;

import java.util.ArrayList;
import java.util.List;

public class WijnDBInMemory  {
    private List<Wijn> wijnen = new ArrayList<>();

    public WijnDBInMemory() {
        Wijn wijn1 = new Wijn("Sirius", "Bordeaux", 2017, 4.84, "Bordeaux Sec");
        Wijn wijn2 = new Wijn("Ch. Couhins blanc - G.C.", "Bordeaux", 2014, 22.95, "Pessac Léognan");
        Wijn wijn3 = new Wijn("Dom. Fichet - Les 3 Terroirs", "Bourgogne", 2016, 10.60, "Mâcon Burgy - Chardonnay");
        Wijn wijn4 = new Wijn("La Chablissienne", "Bourgogne", 2017, 10.71, "Petit Chablis Pas si Petit");
        Wijn wijn5 = new Wijn("Cuvée Anne de Chypre", "Jura", 2017, 10.44, "Roussette De Savoie - Altesse");
        Wijn wijn6 = new Wijn("Dom. de l'Idylle", "Jura", 2018, 7.63, "Cruet - Jacquère");
        this.voegToe(wijn1);
        this.voegToe(wijn2);
        this.voegToe(wijn3);
        this.voegToe(wijn4);
        this.voegToe(wijn5);
        this.voegToe(wijn6);
    }

    /**
     * Voeg een nieuwe wijn toe
     *
     * @throws DomainException indien de gegeven wijn niet geldig is
     */
    public void voegToe(Wijn wijn) {
        if (wijn == null)
            throw new DomainException("Geen geldige wijn om toe te voegen");
        if (this.vindWijn(wijn.getNaam())!= null)
            throw new DomainException("Er bestaat al een wijn met deze naam");
        this.wijnen.add(wijn);
    }

    public List<Wijn> getAlleWijnen() {
        return wijnen;
    }

    /**
     * Geef de wijn met gegeven naam
     *
     * @return de gevonden wijn indien mogelijk
     * @throws DomainException indien de gegeven naam leeg is
     */
    public Wijn vindWijn(String naam) {
        if (naam == null || naam.trim().isEmpty())
            throw new DomainException("Geef de naam van de wijn die je wil zoeken");
        for (Wijn wijn : getAlleWijnen()) {
            if (wijn.getNaam().equals(naam))
                return wijn;
        }
        return null;
    }


    /**
     * Verwijder de wijn met de gegeven naam
     *
     * @return true indien de wijn verwijderd werd, false indien niet
     */
    public boolean verwijder(String wijn) {
        return wijnen.remove(this.vindWijn(wijn));
    }


    /**
     * Vind wijnen van gegeven jaartal
     *
     * @param jaartalAsString
     * @return vindWijnenVanJaartal(int jaartal)
     * @throws DomainException indien het gegeven jaartal leeg is
     */
    public ArrayList<Wijn> vindWijnenVanJaartal(String jaartalAsString) {
        try {
            return vindWijnenVanJaartal(Integer.parseInt(jaartalAsString));
        } catch (NumberFormatException e) {
            throw new DomainException("Vul een jaartal in");
        }
    }

    /**
     * Vind wijnen van gegeven jaartal
     *
     * @return de lijst met gevonden wijnen
     * @throws DomainException indien het gegeven jaartal niet correct is
     * @throws DomainException indien er geen wijnen  van het gegeven jaartal gevonden worden
     */
    public ArrayList<Wijn> vindWijnenVanJaartal(int jaartal) {
        if (jaartal < 0)
            throw new DomainException("Het jaartal moet positief zijn.");
        ArrayList<Wijn> result = new ArrayList<Wijn>();
        for (Wijn w : this.getAlleWijnen()) {
            if (w.getJaartal() == jaartal) {
                result.add(w);
            }
        }
        if (result.isEmpty())
            throw new DomainException("We kunnen geen wijnen vinden van het gegeven jaartal.");
        return result;
    }

    public double getGemiddeldePrijs() {
        return (double) Math.round(wijnen.stream().mapToDouble(Wijn::getPrijs).sum() / wijnen.size() *100) / 100;
    }

    public Wijn getDuursteWijn() {
        double max = 0;
        Wijn maxWijn = null;
        for (Wijn w : wijnen) {
            if (w.getPrijs() > max) {
                max = w.getPrijs();
                maxWijn = w;
            }
        }
        return maxWijn;
    }

}
