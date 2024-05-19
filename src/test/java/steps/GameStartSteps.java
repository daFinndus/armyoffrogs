package steps;

import steps.container.LogicContainer;

import de.fhkiel.tsw.Gamelogic;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStartSteps {

    private LogicContainer container;

    private boolean success;

    public GameStartSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Wenn("das Spiel( ist) mit {int} Spielern gestartet( wird)")
    public void das_spiel_mit_spielern_gestartet(int anzahl) {
        container.logic = new Gamelogic();
        success = container.logic.newGame(anzahl);
    }

    @Dann("wird ein Spiel erstellt")
    public void wird_ein_spiel_erstellt() {
        assertThat(success)
                .isTrue();
    }

    @Dann("wird kein Spiel erstellt")
    public void wird_kein_spiel_erstellt() {
        assertThat(success)
                .isFalse();
    }

    @Dann("es spielen {int} Spieler")
    public void es_spielen_spieler(int anzahl) {
        assertThat(container.logic.players())
                .hasSize(anzahl);
    }
}
