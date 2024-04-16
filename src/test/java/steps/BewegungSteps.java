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

    @Angenommen("der erste Spieler ist am Zug")
    public void der_erste_Spieler_ist_am_Zug() {
        int amount_of_players = 2;

        gameround = new Gameround();
        logic = new Gamelogic();
        // For example:

        logic.startGame(amount_of_players);


        Color[] players = logic.players();

        // Debugging:
        for (int x = 0; x < players.length; x++) {
            System.out.println(players[x]);
            System.out.println("Printed player");
        }

        if (players.length == 0) {
            throw new io.cucumber.java.PendingException();
        }
    }

    @Wenn("er einen Frosch bewegen möchte")
    public void er_einen_Frosch_bewegen_moechte() {
        player = gameround.getCurrentPlayer();
        assertThat(player).isNotNull();
    }

    @Dann("muss der Frosch seine Teamfarbe haben")
    public boolean muss_der_Frosch_seine_Teamfarbe_haben() {
        List<Color> frogsInHand = logic.getFrogsInHand(player);
        return frogsInHand.contains(player);
    }
}
