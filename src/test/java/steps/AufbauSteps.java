package steps;

import io.cucumber.java.de.Und;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Angenommen;

import de.fhkiel.tsw.Frog;

import steps.container.LogicContainer;
import de.fhkiel.tsw.Bag;

public class AufbauSteps {

    private LogicContainer container;
    private int numberOfPlayers = 0;

    Bag bag;

    public AufbauSteps(LogicContainer container) {
        this.container = container;
        bag = container.logic.bag;
        System.out.println(getClass().getName());
    }

    @Angenommen("das Spiel wird vorbereitet")
    public void das_spiel_wird_vorbereitet() {
        int frogsInBag = bag.getNumberOfFrogs();

        if (frogsInBag == 0) {
            System.out.println("Spiel wird vorbereitet..");
        } else {
            System.out.println("Spiel ist bereits vorbereitet..");
        }
    }

    @Angenommen("es nehmen {int} Spieler teil")
    public void es_nehmen_spieler_teil(Integer anzahl) {
        numberOfPlayers = anzahl;
        container.logic.newGame(anzahl);
        System.out.println("Es spielen " + anzahl + " Spieler..");
    }


    @Wenn("der Beutel befüllt wird")
    public void der_beutel_befüllt_wird() {
        container.logic.startGame(numberOfPlayers);

        System.out.println(getClass().getName() + " - " + bag.getNumberOfFrogs());
        // Check what colors are in the bag
        for (int i = 0; i < bag.getNumberOfFrogs(); i++) {
            System.out.println("Farbe im Beutel: " + bag.takeFrog().getColor());
        }
    }

    /*
    @Wenn("der Beutel befüllt wird")
    void der_beutel_befüllt_wird() {
        System.out.println("Beutel wird befüllt..");
        for (int i = 0; i < container.logic.players().length; i++) {
            for (int j = 0; j <= (numberOfPlayers * 10); j++) {
                container.bag.putFrog(new Frog(container.logic.players()[i], null));

            }
        }
        System.out.println("Frösche im Beutel liegen bei " + container.bag.getNumberOfFrogs());
    }
    */


    @Dann("darf er das nur mit den Spielsteinen der Spielerfarben tun")
    public void darf_er_das_nur_mit_den_spielsteinen_der_spielerfarben_tun() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}