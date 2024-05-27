package steps;
import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.Gameround;
import de.fhkiel.tsw.armyoffrogs.Color;
import io.cucumber.java.PendingException;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

import java.awt.*;


public class AnlegenSteps {

    private LogicContainer container;
    private Gameround gameRound;
    private Gamelogic gameLogic;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        this.gameRound = container.round;
        this.gameLogic = container.logic;
        System.out.println(getClass().getName());
    }

    @Angenommen("der {word} Spieler möchte einen Frosch anlegen")
    public void der_spieler_möchte_einen_frosch_anlegen(String order) {
        Color currentPlayerColor;
        switch (order) {
            case "erste":
                currentPlayerColor = gameLogic.players()[0];
                break;
            case "zweite":
                currentPlayerColor = gameLogic.players()[1];
                break;
            case "dritte":
                currentPlayerColor = gameLogic.players()[2];
                break;
            case "vierte":
                currentPlayerColor = gameLogic.players()[3];
                break;
            default:
                throw new IllegalArgumentException("Ungültige Reihenfolge: " + order);
        }
        // Write code here that turns the phrase above into concrete actions
    }

    @Wenn("der Frosch die eigene Teamfarbe hat")
    public void der_frosch_die_eigene_teamfarbe_hat() {
        // Erhalten Sie die Farbe des aktuellen Spielers
        Color currentPlayerColor = gameRound.getCurrentPlayer();

        // Erstellen Sie einen Frosch mit der Farbe des aktuellen Spielers
        Frog frog = new Frog(currentPlayerColor);

        // Überprüfen Sie, ob der Frosch die Farbe des aktuellen Spielers hat
        if (!frog.getColor().equals(currentPlayerColor)) {
            throw new IllegalArgumentException("Der Frosch hat nicht die Farbe des aktuellen Spielers");
        }
    }


    @Wenn("er die Aktion Anlegen überspringen will")
    public void er_die_aktion_anlegen_überspringen_will() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dann("müssen alle bereits gelegten Spielsteine und die in seinem Vorrat seine Teamfarbe haben")
    public void müssen_alle_bereits_gelegten_spielsteine_und_die_in_seinem_vorrat_seine_teamfarbe_haben() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dann("darf der Frosch ausschließlich an andersfarbigen Fröschen platziert werden")
    public void darf_der_frosch_ausschließlich_an_andersfarbigen_fröschen_platziert_werden() {
        // This step would involve checking the color of the frogs adjacent to the position where the current player wants to place their frog.
        // If any of them have the same color as the current player's frog, throw an exception.
        // The implementation of this step would depend on how you've structured your game and how you're keeping track of the game state.
        // As such, it's not possible to provide a specific code example without more information.
    }
}
