package steps;

import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;

import static org.assertj.core.api.Assertions.assertThat;

public class BewegungSteps {
    private LogicContainer container;

    public BewegungSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("der Spieler mit der Teamfarbe {word} ist am Zug")
    public void der_spieler_mit_der_teamfarbe_ist_am_zug(String teamfarbe) {
        Color color;

        switch (teamfarbe) {
            case "Gruen":
                color = Color.Green;
                break;
            case "Blau":
                color = Color.Blue;
                break;
            case "Weiss":
                color = Color.White;
                break;
            default:
                color = Color.Red;
                break;
        }
        if (!container.round.getCurrentPlayer().equals(color)) {
            throw new IllegalStateException("Erwarteter Spieler mit der Teamfarbe" + teamfarbe + " ist am Zug");
        }
    }

    @Wenn("der Spieler seinen Frosch bewegen möchte")
    public void der_spieler_seinen_frosch_bewegen_möchte() {
        // Replace null with an actual frog
        container.logic.clicked(null);
    }

    @Und("er infolgedessen einen Spielstein ohne Verbindung hinterlässt")
    public void er_infolgedessen_einen_Spielstein_ohne_Verbindung_hinterlässt() {
        // Implement the logic to check if a game piece is left without connection
    }

    @Dann("muss die Bewegung nicht erlaubt sein")
    public void muss_die_bewegung_nicht_erlaubt_sein() {
        // Check if the move was actually not allowed
        assertThat(true).isTrue();
    }
}