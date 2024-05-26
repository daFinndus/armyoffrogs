package steps;

import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;

import static org.assertj.core.api.Assertions.assertThat;

public class AblaufSteps {

    private LogicContainer container;

    public AblaufSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    // Check if the game is running

    /*
    We have to adjust the upper value of the frogsInBag() method to 40,
    because the bag cannot contain 40 frogs when the game started.
    This needs to be adjusted at some point.
     */
    @Angenommen("das Spiel läuft bereits")
    public boolean das_spiel_läuft_bereits() {
        int frogsInBag = container.logic.frogsInBag();

        if (0 < frogsInBag && frogsInBag <= 40) {
            return true;
        } else {
            return false;
        }
    }

    @Und("es wurde mit {int} Spielern gestartet")
    public void es_wurde_mit_spielern_gestartet(int anzahl) {
        container.logic.newGame(anzahl);
    }


    @Wenn("der Spieler mit der Teamfarbe {word} seine Aktionen abgeschlossen hat")
    public void der_spieler_mit_der_teamfarbe_seine_aktionen_abgeschlossen_hat(String teamfarbe) {
        Color color;

        switch (teamfarbe) {
            case "Rot":
                color = Color.Red;
                break;
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
                throw new IllegalArgumentException("Not a valid color: " + teamfarbe);
        }

        container.round.endTurn(color, container.logic.players());
    }

    @Dann("muss der nächste Spieler die Teamfarbe {word} haben")
    public void muss_der_nächste_spieler_die_teamfarbe_haben(String teamfarbe) {
        assertThat(container.round.getCurrentPlayer().toString()).isEqualTo(teamfarbe);
    }

}
