package steps;

import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Dann;

import static org.assertj.core.api.Assertions.assertThat;

public class NachziehenSteps {

    private LogicContainer container;

    public NachziehenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("Das Spiel hat mit {int} Spielern begonnen")
    public void es_wurde_mit_spielern_gestartet(int anzahl) {
        container.logic.startGame(anzahl);
    }

    @Und("der erste Spieler ist am Zug")
    public void der_erste_spieler_ist_am_zug() {
        container.logic.selectedFrogInHand(container.logic.players()[0], Color.Red);

    }

    @Wenn("er einen neuen Spielstein ziehen will")
    public void er_einen_neuen_spielstein_ziehen_will() {
        container.logic.takeFrogFromBag();

    }

    @Dann("muss die Anzahl der sich im Vorrat befindenden Spielsteine 1 betragen")
    public void muss_die_anzahl_der_sich_im_vorrat_befindenden_spielsteine_1_betragen() {
        int frogsInHandBefore = container.logic.getFrogsInHand(container.logic.players()[0]).size();
        if (frogsInHandBefore < 2) {
            Color frog = container.logic.takeFrogFromBag();
            container.logic.selectedFrogInHand(container.logic.players()[0], frog);
            int frogsInHandAfter = container.logic.getFrogsInHand(container.logic.players()[0]).size();
            assertThat(frogsInHandAfter).isEqualTo(frogsInHandBefore + 1);
        } else {
            assertThat(frogsInHandBefore).isEqualTo(2);
        }
    }
}