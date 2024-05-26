package steps;

import io.cucumber.java.de.Und;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Angenommen;

import steps.container.LogicContainer;

public class AufbauSteps {

    private LogicContainer container;
    private int numberOfPlayers = 0;

    public AufbauSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("das Spiel wird vorbereitet")
    void das_spiel_wird_vorbereitet() {
        int frogsInBag = container.bag.getNumberOfFrogs();

        if (frogsInBag == 40) {
            System.out.println("Spiel wird vorbereitet..");
        } else {
            System.out.println("Spiel ist bereits vorbereitet..");
        }
    }

    @Und("es spielen <int> Spieler")
    void es_spielen_int_Spieler(int anzahl) {
        numberOfPlayers = anzahl;
        System.out.println("Es spielen " + anzahl + " Spieler..");
    }


    @Wenn("der Beutel befüllt wird")
    void der_Beutel_befüllt_wird() {
        System.out.println("Beutel wird befüllt..");
        for (int i = 0; i <= (numberOfPlayers * 10); i++) {
            container.bag.putFrog();
        }
        System.out.println("Frösche im Beutel liegen bei " + container.bag.getNumberOfFrogs());
    }

    @Dann("darf er das nur mit den Spielsteinen der Spielerfarben tun")
    public void darf_er_das_nur_mit_den_spielsteinen_der_spielerfarben_tun() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}