package steps;


import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.armyoffrogs.Color;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import steps.container.LogicContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



public class AnlegenSteps {


    private final LogicContainer container;

    Color currentPlayer;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    // Potentiell das hier umschreiben in was cooleres
    @Angenommen("der {word} Spieler möchte einen Frosch anlegen")
    public void der_spieler_möchte_einen_frosch_anlegen(String order) {

        switch (order) {
            case "erste":
                currentPlayer= container.logic.players()[0];
                break;
            case "zweite":
                currentPlayer = container.logic.players()[1];
                break;
            case "dritte":
                currentPlayer= container.logic.players()[2];
                break;
            case "vierte":
                currentPlayer = container.logic.players()[3];
                break;
            default:
                throw new IllegalArgumentException("Ungültige Reihenfolge: " + order);
        }
        // Write code here that turns the phrase above into concrete actions
    }

    @Wenn("der Frosch die eigene Teamfarbe hat")
    public void der_frosch_die_eigene_teamfarbe_hat() {
        // Erhalten Sie die Liste der Frösche in der Hand des aktuellen Spielers
        List<Color> frogsInHand = container.logic.getFrogsInHand(currentPlayer);

        // Wählen Sie einen Frosch mit der Farbe des aktuellen Spielers
        Color frog = null;
        for ( Color f : frogsInHand) {
            if (f.equals(currentPlayer)) {
                frog = f;
                break;
            }
        }

        // Überprüfen Sie, ob ein Frosch mit der Farbe des aktuellen Spielers gefunden wurde
        if (frog == null) {
            throw new IllegalArgumentException("Kein Frosch mit der Farbe des aktuellen Spielers gefunden");
        }

        // Nun können Sie den Frosch für weitere Aktionen verwenden
    }


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
        List<Color> frogColors = container.logic.getFrogsInHand(currentPlayer);

        // Überprüfen ob alle Frösche im Vorrat die Teamfarbe des aktuellen Spielers haben
        for (int i = 0; i < container.logic.getFrogsInHand(currentPlayer).size(); i++) {
            Color frogColor = frogColors.get(i);

            /*
            // Das hier wurde benutzt, um das Ergebnis zu simulieren
            frogColor = currentPlayer;
            */

            assertThat(frogColor).isEqualTo(currentPlayer);
        }

        System.out.println("Der aktuelle Spieler ist: " + currentPlayer);
    }

    @Dann("darf der Frosch ausschließlich an andersfarbigen Fröschen platziert werden")
    public void darf_der_frosch_ausschließlich_an_andersfarbigen_fröschen_platziert_werden() {
        //kann man erst implementieren, wenn die GUI fertig ist
    }
}
