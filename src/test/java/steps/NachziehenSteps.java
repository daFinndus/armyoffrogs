package steps;


import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;
import de.fhkiel.tsw.Frog;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;

import static org.assertj.core.api.Assertions.assertThat;

public class NachziehenSteps {

    private LogicContainer container;

    private int numberOfPlayers;
    public Color currentPlayer;

    AnlegenSteps anlegenSteps = new AnlegenSteps(container);

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

    @Und("der {word} Spieler ist am Zug")
    public void der_spieler_ist_am_zug(String spieler) {
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
    public void muss_die_anzahl_der_spielsteine_in_seinem_vorrat_betragen(Integer anzahl) {
        int frogsInHand = container.logic.getFrogsOfPlayer(currentPlayer).length;

        assertThat(frogsInHand).isEqualTo(anzahl);
    }
}