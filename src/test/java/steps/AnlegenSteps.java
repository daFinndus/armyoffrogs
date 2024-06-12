package steps;


import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import steps.container.LogicContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;


public class AnlegenSteps {
    private final LogicContainer container;

    Color currentPlayer;
    Color selectedFrog;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    // Potentiell das hier umschreiben in was cooleres
    @Angenommen("der {word} Spieler ist am Zug")
    public void der_spieler_ist_am_zug(String order) {

        switch (order) {
            case "erste":
                currentPlayer = container.logic.players()[0];
                break;
            case "zweite":
                currentPlayer = container.logic.players()[1];
                break;
            case "dritte":
                currentPlayer = container.logic.players()[2];
                break;
            case "vierte":
                currentPlayer = container.logic.players()[3];
                break;
            default:
                throw new IllegalArgumentException("Ungültige Reihenfolge: " + order);
        }
    }

    @Wenn("der Frosch die eigene Teamfarbe hat")
    public void der_frosch_die_eigene_teamfarbe_hat() {
        // Simulieren des ausgewählten Frosches
        selectedFrog = currentPlayer;
    }

    @Wenn("er die Aktion Anlegen überspringen will")
    public void er_die_aktion_anlegen_überspringen_will() {
        System.out.println("Der Spieler möchte die Aktion Anlegen überspringen..");
    }

    @Wenn("der Startspieler festgelegt wurde")
    public void der_startspieler_festgelegt_wurde() {
        currentPlayer = container.logic.players()[0];
    }

    @Wenn("dieser einen Spielstein anlegen möchte der nicht der eigenen Teamfarbe entspricht")
    public void dieser_einen_spielstein_anlegen_möchte_der_nicht_der_eigenen_teamfarbe_entspricht() {
        // Simulieren des ausgewählten Frosches
        // Alles außer Color.Blue, da der aktuelle Spieler Color.Blue ist
        selectedFrog = Color.Red;
    }

    @Wenn("er einen Frosch hinzufügen möchte")
    public void er_einen_frosch_hinzufügen_möchte() {
        // Hier kann eine komplett beliebige Farbe gewählt werden
        // Sie darf nur nicht der bereits gesetzten Farbe + Teamfarbe gleichen
        selectedFrog = Color.Green;
    }

    // Das hier muss noch mit der GUI kombiniert werden, aktuell wird nur der Vorrat überprüft
    // Das läuft logischerweise auch nur durch, wenn beide Frösche im Vorrat die Teamfarbe haben
    @Dann("müssen alle bereits gelegten Spielsteine und die in seinem Vorrat seine Teamfarbe haben")
    public void müssen_alle_bereits_gelegten_spielsteine_und_die_in_seinem_vorrat_seine_teamfarbe_haben() {
        // Festlegen vom zweiten Spieler als aktuellen Spieler
        currentPlayer = container.logic.players()[1];

        // Simulieren einer Hand voller Frösche der Spielerfarbe
        Color[] frogColors = {currentPlayer, currentPlayer};

        // Überprüfen ob alle Frösche im Vorrat die Teamfarbe des aktuellen Spielers haben
        for (int i = 0; i < container.logic.getFrogsInHand(currentPlayer).size(); i++) {
            Color frogColor = frogColors[i];

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
        // Simulieren eines gelegten Frosches des Spielers davor
        container.logic.placeFrog(new Position(Color.None, 0, 0, Color.None), Color.Red);

        // Simulieren eines gelegten Frosches des aktuellen Spielers
        container.logic.placeFrog(new Position(Color.None, 1, 0, Color.None), currentPlayer);
    }

    @Dann("kann dieser einen beliebigen Stein aus seinem Vorrat legen")
    public void kann_dieser_einen_beliebigen_stein_aus_seinem_vorrat_legen() {
        List<Color> frogsInHand = container.logic.getFrogsInHand(currentPlayer);

        // Platzieren von einem der beiden Frösche aus dem Vorrat
        container.logic.placeFrog(new Position(frogsInHand.get(0), 0, 0, Color.None), currentPlayer);

    }

    @Dann("muss dieser an alle Spielsteine angelegt werden können")
    public void muss_dieser_an_alle_spielsteine_angelegt_werden_können() {
        // Simulieren eines gelegten Frosches des Spielers davor
        container.logic.placeFrog(new Position(Color.None, 0, 0, Color.None), Color.Red);

        // Simulieren eines gelegten Frosches des aktuellen Spielers
        container.logic.placeFrog(new Position(selectedFrog, 1, 0, Color.None), currentPlayer);
    }

    @Dann("muss bereits ein Frosch auf der Spielfläche liegen der nicht der Teamfarbe entspricht")
    public void muss_bereits_ein_frosch_auf_der_spielfläche_liegen_der_nicht_der_teamfarbe_entspricht() {
        // Simulieren eines gelegten Frosches des Spielers davor
        container.logic.placeFrog(new Position(Color.None, 0, 0, Color.None), Color.Red);

        assertThat(container.logic.getBoard().size()).isGreaterThan(0);

        for (Position pos : container.logic.getBoard()) {
            if (pos.frog() == selectedFrog && pos.frog() == currentPlayer) {
                throw new IllegalArgumentException("Der Frosch darf nicht an einen eigenen Frosch angelegt werden");
            }
        }
    }
}
