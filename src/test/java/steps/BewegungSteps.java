package steps;

import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;
import steps.container.LogicContainer;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;

import static org.assertj.core.api.Assertions.assertThat;

public class BewegungSteps {
    private LogicContainer container;
    private Color currentPlayer;

    public BewegungSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("der Spieler mit der Teamfarbe {word} ist am Zug")
    public void der_spieler_mit_der_teamfarbe_ist_am_zug(String teamfarbe) {
        container.logic = new Gamelogic();

        switch (teamfarbe) {
            case "Rot":
                currentPlayer = Color.Red;
                container.logic.newGame(2);
                break;
            case "Gruen":
                currentPlayer = Color.Green;
                container.logic.newGame(2);
                break;
            case "Blau":
                currentPlayer = Color.Blue;
                container.logic.newGame(3);
                break;
            case "Weiss":
                currentPlayer = Color.White;
                container.logic.newGame(4);
                break;
            default:
                currentPlayer = Color.Red;
                container.logic.newGame(2);
                break;
        }

        container.logic.startGame(container.logic.players().length);
    }

    @Wenn("der Spieler seinen Frosch bewegen möchte")
    public void der_spieler_seinen_frosch_bewegen_möchte() {
        // Simulieren der ersten zwei Runden des Frosche platzierens
        container.logic.placeFrog(new Position(Color.None, 0, 0, Color.None), currentPlayer);
        container.logic.placeFrog(new Position(Color.None, 0, 1, Color.None), Color.Green);
        container.logic.placeFrog(new Position(Color.None, 1, 0, Color.None), Color.Green);

        container.logic.highlightFrog(new Position(currentPlayer, 0, 0, Color.None));
    }

    @Und("er infolgedessen einen Spielstein ohne Verbindung hinterlässt")
    public void er_infolgedessen_einen_Spielstein_ohne_Verbindung_hinterlässt() {
        throw new io.cucumber.java.PendingException();
    }

    @Dann("muss die Bewegung nicht erlaubt sein")
    public void muss_die_bewegung_nicht_erlaubt_sein() {
        throw new io.cucumber.java.PendingException();
    }
}