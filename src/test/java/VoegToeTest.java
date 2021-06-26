import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class VoegToeTest {
    private WebDriver driver;
    private String url = "http://localhost:8080/Wijnkelder_war_exploded/voegToe.jsp";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }

    //VOEG 1 WIJN TOE
    @Test
    public void voeg_random_wijn_toe_no_checks() {
        driver.get(url);
        sendNaam();
        sendSoort();
        sendJaartal();
        sendPrijs();
        sendOmschrijving();
        submit();
    }

    @Test
    public void voegToeOngeldigeNaam() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";
        Assert.assertEquals(driver.getTitle(), title);

        sendNaam("     "); //Lege naam -> ongeldig
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);

        //Naam error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De naam van de wijn is niet ingevuld"));
    }

    @Test
    public void voegToeOngeldigeSoort() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";
        Assert.assertEquals(driver.getTitle(), title);

        sendSoort("     "); //Lege soort -> ongeldig
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);

        //Soort error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De soort is niet ingevuld"));
    }

    @Test
    public void voegToeOngeldigeJaartal() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";
        Assert.assertEquals(driver.getTitle(), title);

        sendJaartal(" "); //Lege jaartal -> ongeldig
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);

        //Jaartal error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("Het gegeven jaartal is niet geldig"));

        sendJaartal("-1000"); //Negatieve jaartal -> ongeldig
        submit();
        errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("Het gegeven jaartal is niet geldig"));

        sendJaartal("ABC"); //Niet numerieke jaartal -> ongeldig
        submit();
        errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("Het gegeven jaartal is niet geldig"));
    }

    @Test
    public void voegToeOngeldigePrijs() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";
        Assert.assertEquals(driver.getTitle(), title);

        sendPrijs("     "); //Lege prijs -> ongeldig
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);

        //Soort error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De gegeven prijs is ongeldig"));

        sendPrijs("-12"); //Negatieve prijs -> ongeldig
        submit();
        errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De prijs moet positief zijn"));

        sendPrijs("ABC"); // Geen numerieke prijs -> ongeldig
        submit();
        errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De gegeven prijs is ongeldig"));
    }

    @Test
    public void voegToeOngeldigeOmschrijving() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";
        Assert.assertEquals(driver.getTitle(), title);

        sendOmschrijving("     "); //Lege omschrijving -> ongeldig
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);

        //Omschrijving error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De omschrijving is niet ingevuld"));

        sendOmschrijving("012345678901234567890123456789012345678901234567890123456789" +
                "0123456789012345678901234567890123456789012345678901234567890123456789" +
                "01234567890123456789012345678901234567890123456789012345678901234567891"); //Omschrijving 201 chars -> ongeldig
        submit();
        errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De omschrijving is te lang (max 200 karakters)"));
    }

    @Test
    public void test_voegToeNaamDieAlBestaat() {
        driver.get(url);
        String title = "Voeg Toe | Wijnkelder";

        //Send geldige velden
        sendSoort();
        sendJaartal();
        sendPrijs();
        sendOmschrijving();
        sendNaam("Selenium same name test");
        submit();

        driver.get(url);

        //Send geldige velden + al bestaande naam
        sendSoort();
        sendJaartal();
        sendPrijs();
        sendOmschrijving();
        sendNaam("Selenium same name test");
        submit();

        //We blijven op dezelfde pagina
        Assert.assertEquals(driver.getTitle(), title);
        //Naam error getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("Er bestaat al een wijn met deze naam"));
    }

    @Test
    public void test_voegToeCorrecteVeldenBlijven() {
        driver.get(url);

        //Send alles correct behalve naam
        sendSoort("Dit blijft hier!");
        sendJaartal("1");
        sendPrijs("1");
        sendOmschrijving("Dit blijft hier!");
        submit();

        //We blijven in dezelfde pagina
        Assert.assertEquals(driver.getTitle(), "Voeg Toe | Wijnkelder");

        //Error voor naam getoond
        String errors = driver.findElement(By.id("error")).getText();
        Assert.assertTrue(errors.contains("De naam van de wijn is niet ingevuld"));

        //Andere gegevens blijven
        Assert.assertEquals(driver.findElement(By.id("soort")).getAttribute("value"), "Dit blijft hier!");
        Assert.assertEquals(driver.findElement(By.id("jaartal")).getAttribute("value"), "1");
        Assert.assertEquals(driver.findElement(By.id("prijs")).getAttribute("value"), "1");
        Assert.assertEquals(driver.findElement(By.id("omschrijving")).getAttribute("value"), "Dit blijft hier!");
    }

    @Test
    public void test_voegToeAlleVeldenCorrect() {
        driver.get(url);
        String randomNaam = "Selenium Test " + Math.round(Math.random()*100000);
        sendNaam(randomNaam);
        sendSoort();
        String randomJaartal = String.valueOf(Math.round(Math.random()*120) + 1900);
        sendJaartal(randomJaartal);
        sendPrijs();
        sendOmschrijving();
        submit();

        //Pagina verandert naar overzicht
        Assert.assertEquals(driver.getTitle(), "Overzicht | Wijnkelder");

        //Wijn werd toegevoegd aan tabel
        List<WebElement> table = driver.findElements(By.tagName("td"));
        Assert.assertTrue(containsWebElementsWithText(table, randomNaam));
        Assert.assertTrue(containsWebElementsWithText(table, randomJaartal));
    }

    private void sendNaam() {
        sendNaam("Selenium Test " + Math.round(Math.random()*100000));
    }

    private void sendNaam(String s) {
        WebElement naam = driver.findElement(By.id("naam"));
        naam.clear();
        naam.sendKeys(s);
    }

    private void sendSoort() {
        sendSoort("Soort " + Math.round(Math.random()*10));
    }

    private void sendSoort(String s) {
        WebElement soort = driver.findElement(By.id("soort"));
        soort.clear();
        soort.sendKeys(s);
    }

    private void sendJaartal() {
        sendJaartal(String.valueOf(Math.round(Math.random()*120) + 1900));
    }

    private void sendJaartal(String s) {
        WebElement jaartal = driver.findElement(By.id("jaartal"));
        jaartal.clear();
        jaartal.sendKeys(s);
    }

    private void sendPrijs() {
        sendPrijs(String.valueOf((double) Math.round(Math.random()*10000) / 100));
    }

    private void sendPrijs(String s) {
        WebElement prijs = driver.findElement(By.id("prijs"));
        prijs.clear();
        prijs.sendKeys(s);
    }

    private void sendOmschrijving() {
        sendOmschrijving("Dit is een test.");
    }

    private void sendOmschrijving(String s) {
        WebElement omschrijving = driver.findElement(By.id("omschrijving"));
        omschrijving.clear();
        omschrijving.sendKeys(s);
    }

    private void submit() {
        driver.findElement(By.id("voegToe")).click();
    }

    private boolean containsWebElementsWithText(List<WebElement> elements, String text) {
        for (WebElement element : elements) {
            if (element.getText().equals(text)) {
                return true;
            }
        }
        return false;
    }

    @After
    public void cleanUp() {
        driver.quit();
    }
}
