package steps;

import steps.container.LogicContainer;

import de.fhkiel.tsw.armyoffrogs.Color;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerColorSteps {

    private LogicContainer container;

    public PlayerColorSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    private Color[] player;

    @Wenn("die Spielerfarben abgefragt werden")
    public void die_spielerfarben_abgefragt_werden() {
        player = container.logic.players();
    }

    @Dann("ist die erste Farbe Rot")
    public void ist_die_erste_farbe_rot() {
        assertThat(player[0]).isEqualTo(Color.Red);
    }

    @Dann("die zweite Farbe Grün")
    public void die_zweite_farbe_grün() {
        assertThat(player[1]).isEqualTo(Color.Green);
    }

    @Dann("die dritte Farbe Blau")
    public void die_dritte_farbe_blau() {
        assertThat(player[2]).isEqualTo(Color.Blue);
    }

    @Dann("die vierte Farbe Weiß")
    public void die_vierte_farbe_weiß() {
        assertThat(player[3]).isEqualTo(Color.White);
    }
}
