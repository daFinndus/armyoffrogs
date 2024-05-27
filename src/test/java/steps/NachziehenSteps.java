package steps;

import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;

import static org.assertj.core.api.Assertions.assertThat;

public class NachziehenSteps {
    private LogicContainer container;
    private int numberOfPlayers;

    Color currentPlayer;

    public NachziehenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("das Spiel hat mit {int} Spielern begonnen")
    public void das_spiel_hat_mit_spieler_spielern_begonnen(Integer anzahl) {
        numberOfPlayers = anzahl;
        container.logic.newGame(anzahl);
        container.logic.startGame(anzahl);
    }

    @Angenommen("der {word} Spieler ist an der Reihe")
    public void der_spieler_ist_an_der_reihe(String spieler) {
        switch (spieler) {
            case "erste":
                currentPlayer = Color.Red;
                break;
            case "zweite":
                currentPlayer = Color.Green;
                break;
            case "dritte":
                currentPlayer = Color.Blue;
                break;
            case "vierte":
                currentPlayer = Color.White;
                break;
            default:
                throw new IllegalArgumentException("Not a valid player: " + spieler);
        }

        container.logic.selectedFrogInHand(container.logic.players()[0], Color.Red);
    }


    @Wenn("er einen neuen Spielstein ziehen will")
    public void er_einen_neuen_spielstein_ziehen_will() {
        container.logic.takeFrogFromBag();
    }

    // Das hier muss potentiell noch etwas logischer verfasst werden
    @Wenn("der Spieler die Aktion Nachziehen überspringen will")
    public void der_spieler_die_aktion_nachziehen_ueberspringen_will() {
        System.out.println("Der Spieler möchte die Aktion Nachziehen überspringen..");
    }

    @Dann("muss die Anzahl der Spielsteine in seinem Vorrat {int} betragen")
    public void muss_die_anzahl_der_sich_im_vorrat_befindenden_spielsteine_betragen(Integer anzahl) {
        System.out.println("Aktueller Spieler ist: " + currentPlayer);

        if (anzahl == 1) {
            container.logic.removeFrogFromHand(currentPlayer, 0);
        }

        int frogsInHand = container.logic.getFrogsInHand(currentPlayer).size();
        assertThat(frogsInHand).isEqualTo(anzahl);
    }
}