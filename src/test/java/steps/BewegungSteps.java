package steps;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhkiel.tsw.Gameround;
import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.armyoffrogs.Color;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import java.util.List;

public class BewegungSteps {

    Gamelogic logic;
    Gameround gameround;

    Color player;

    @Angenommen("das Spiel startet mit {int} Spielern")
    public void das_Spiel_ist_mit_Spielern_gestartet(Integer spieler) {
        logic = new Gamelogic();
        logic.startGame(spieler);

        gameround = new Gameround();
        gameround.round = 0;
    }

    @Angenommen("der {word} Spieler ist am Zug")
    public void der_Spieler_ist_am_Zug(String position) {
        Color[] players = logic.players();

        System.out.println(players);
    }

    @Wenn("er einen Frosch bewegen möchte")
    public void er_einen_Frosch_bewegen_moechte() {

    }

    @Dann("muss der Frosch seine Teamfarbe haben")
    public boolean muss_der_Frosch_seine_Teamfarbe() {
        Color teamFarbe = logic.players()[0];
        List<Color> froschFarbe = logic.getFrogsInHand(teamFarbe);

        for (int x = 0; x < froschFarbe.size(); x++) {
            if (froschFarbe.get(x) == teamFarbe) {
                return true;
            }
        }
        return false;
    }
}
