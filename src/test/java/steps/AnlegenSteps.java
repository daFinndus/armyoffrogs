package steps;


import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import steps.container.LogicContainer;

import java.util.*;
import java.util.logging.Logger;

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

        container.logic.setCurrentPlayer(currentPlayer);
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
        selectedFrog = container.logic.getFrogsInHand(currentPlayer).get(1);
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
        // Simulieren der Frösche in der Hand
        List<Color> frogsInHand = List.of(currentPlayer, currentPlayer);

        // Simulieren eines Frosches auf dem Board
        Position frogPosition = new Position(Color.Green, 0, 0, Color.None);
        Set<Position> board = container.logic.getBoard();
        board.add(frogPosition);
        container.logic.setBoard(board);

        // Durchgehen aller Frösche auf dem Board
        for (Position pos : container.logic.getBoard()) {
            for (Color color : frogsInHand) {
                if (pos.frog() != color) {
                    frogsInHand.add(pos.frog());
                }
            }
        }

        assertThat(frogsInHand).containsOnly(currentPlayer);
    }

    @Dann("darf der Frosch ausschließlich an andersfarbigen Fröschen platziert werden")
    public void darf_der_frosch_ausschließlich_an_andersfarbigen_fröschen_platziert_werden() {
        // Erschaffen einer Position mit Frosch anderer Farbe als der Spielerfarbe
        Position frogPosition = new Position(Color.Red, 0, 0, Color.None);
        Set<Position> board = container.logic.getBoard();
        board.add(frogPosition);
        container.logic.setBoard(board);

        // Simulieren des Vorrats des aktuellen Spielers, ehe er seinen Frosch platziert
        Map<Color, List<Frog>> frogs = new EnumMap<>(Color.class);
        List<Frog> playerFrogs = new LinkedList<>(List.of(new Frog(Color.Red, null), new Frog(currentPlayer, null)));
        frogs.put(currentPlayer, playerFrogs);
        container.logic.setPlayerFrogs(frogs);

        // Simulieren eines gelegten Frosches des aktuellen Spielers
        container.logic.placeFrog(new Position(Color.None, 1, 0, Color.None), currentPlayer);
    }

    @Dann("kann dieser einen beliebigen Stein aus seinem Vorrat legen")
    public void kann_dieser_einen_beliebigen_stein_aus_seinem_vorrat_legen() {
        List<Color> frogsInHand = container.logic.getFrogsInHand(currentPlayer);

        // Platzieren von einem der beiden Frösche aus dem Vorrat
        container.logic.placeFrog(new Position(Color.None, 0, 0, Color.None), frogsInHand.get(0));

    }

    @Dann("muss dieser an alle Spielsteine angelegt werden können")
    public void muss_dieser_an_alle_spielsteine_angelegt_werden_können() {
        // Erschaffen einer Position mit Frosch
        Position frogPosition = new Position(Color.Red, 0, 0, Color.None);
        Set<Position> board = container.logic.getBoard();
        board.add(frogPosition);
        container.logic.setBoard(board);

        // Simulieren des Vorrats des aktuellen Spielers, ehe er seinen Frosch platziert
        Map<Color, List<Frog>> frogs = new EnumMap<>(Color.class);
        List<Frog> playerFrogs = new LinkedList<>(List.of(new Frog(Color.Red, null), new Frog(currentPlayer, null)));
        frogs.put(currentPlayer, playerFrogs);
        container.logic.setPlayerFrogs(frogs);

        // Simulieren eines gelegten Frosches des aktuellen Spielers
        container.logic.placeFrog(new Position(Color.None, 1, 0, Color.None), Color.Red);
    }

    @Dann("muss bereits ein Frosch auf der Spielfläche liegen der nicht der Teamfarbe entspricht")
    public void muss_bereits_ein_frosch_auf_der_spielfläche_liegen_der_nicht_der_teamfarbe_entspricht() {
        // Erschaffen einer Position mit Frosch anderer Farbe als der Spielerfarbe
        Position frogPosition = new Position(Color.Red, 0, 0, Color.None);
        Set<Position> board = container.logic.getBoard();
        board.add(frogPosition);
        container.logic.setBoard(board);

        assertThat(container.logic.getBoard().size()).isGreaterThan(0);

        for (Position pos : container.logic.getBoard()) {
            if (pos.frog() == selectedFrog && pos.frog() == currentPlayer) {
                throw new IllegalArgumentException("Der Frosch darf nicht an einen eigenen Frosch angelegt werden");
            }
        }
    }
}
