package steps;


import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.armyoffrogs.Color;
import steps.container.LogicContainer;

import static org.assertj.core.api.Assertions.assertThat;

public class AnlegenSteps {


    private LogicContainer container;

    Color currentPlayer;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    // Potentiell das hier umschreiben in was cooleres
    @Wenn("er die Aktion Anlegen überspringen will")
    public void er_die_aktion_anlegen_überspringen_will() {
        System.out.println("Der Spieler möchte die Aktion Anlegen überspringen..");
    }

    // Das hier muss noch mit der GUI kombiniert werden, aktuell wird nur der Vorrat überprüft
    // Das läuft logischerweise auch nur durch, wenn beide Frösche im Vorrat die Teamfarbe haben
    @Dann("müssen alle bereits gelegten Spielsteine und die in seinem Vorrat seine Teamfarbe haben")
    public void müssen_alle_bereits_gelegten_spielsteine_und_die_in_seinem_vorrat_seine_teamfarbe_haben() {
        // Festlegen vom zweiten Spieler als aktuellen Spieler
        currentPlayer = container.logic.players()[1];

        // Überprüfen ob alle Frösche im Vorrat die Teamfarbe des aktuellen Spielers haben
        for (int i = 0; i < container.logic.getFrogsOfPlayer(currentPlayer).length; i++) {
            Frog frog = container.logic.getFrogsOfPlayer(currentPlayer)[i];
            Color frogColor = frog.getColor();

            /*
            // Das hier wurde benutzt, um das Ergebnis zu simulieren
            frogColor = currentPlayer;
            */

            assertThat(frogColor).isEqualTo(currentPlayer);
        }

        System.out.println("Der aktuelle Spieler ist: " + currentPlayer);
    }
}
