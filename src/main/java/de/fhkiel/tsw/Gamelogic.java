package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Die Gamelogic Klasse implementiert die Spiellogik für das Spiel "Army of Frogs".
 * Sie verwaltet den Spielzustand, einschließlich der Spieler, der Frösche und des Spielbretts.
 * Sie bietet auch Methoden zum Starten eines neuen Spiels, zum Zurücksetzen des Spiels,
 * zum Hinzufügen und Entfernen von Fröschen und zum Speichern und Laden des Spielzustands.
 */
public class Gamelogic implements Game {

    // Das ist die Variable für die Spielerliste und für die Spielerfrösche
    private Color[] players = new Color[0];
    private Map<Color, List<Frog>> playerFrogs = new HashMap<>();

    // Das sind die Variablen für das Spielbrett und den Beutel
    Set<Position> board = new HashSet<>();
    public Bag bag = new Bag();

    // Die Variablen werden genutzt, um den Spielstatus zu speichern
    boolean gameRunning = false;
    boolean gameStarted = false;

    // Die Variable wird genutzt, um den aktuellen Spieler zu speichern
    private Color currentPlayer;

    @Override
    public boolean newGame(int spieler) {
        try {
            if (gameRunning) {
                resetGame();
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Zurücksetzen des Spiels: " + e);
        }

        System.out.println("newGame(" + spieler + ") ausgeführt.");
        // Check if the number of players is allowed
        if (2 <= spieler && spieler <= 4) {
            players = new Color[spieler];

            // Set the players
            try {
                // Fill the players array with the colors of the players
                Color[] colorOrder = {Color.Red, Color.Green, Color.Blue, Color.White};
                players = Arrays.copyOfRange(colorOrder, 0, spieler);
            } catch (Exception e) {
                System.out.println("Fehler beim Setzen der Spieler: " + e);
            }

            try {
                for (int i = 0; i < players.length; i++) {
                    for (int j = 0; j < 10; j++) {
                        bag.putFrog(new Frog(players[i], null));
                    }
                }
            } catch (Exception e) {
                System.out.println("Fehler beim Befüllen des Beutels: " + e);
            }

            // Change necessary variables
            gameRunning = true;
            bag.setGameRunning(gameRunning);

            return true;
        } else {
            players = new Color[0];
            return false;
        }
    }

    /**
     * Diese Methode setzt das Spiel zurück.
     * Sie wird aufgerufen, wenn ein neues Spiel gestartet wird, während ein Spiel läuft.
     * Sie setzt alle notwendigen Spielvariablen zurück.
     * Sie setzt auch die Spielstatusvariablen `gameRunning` und `gameStarted` zurück.
     * Schließlich setzt sie den aktuellen Spieler auf den ersten Spieler in der Spielerliste zurück.
     */
    public void resetGame() {
        System.out.println("resetGame() ausgeführt.");

        currentPlayer = null;

        players = new Color[0];
        playerFrogs = new HashMap<>();

        board = new HashSet<>();
        bag = new Bag();

        gameRunning = false;
        gameStarted = false;
    }

    @Override
    public Color[] players() {
        System.out.println("players() ausgeführt.");
        return players;
    }

    @Override
    public String getInfo() {
        System.out.println("getInfo() ausgeführt.");
        return "Das ist das Venom Projekt von Finn, Jonas und Darren!";
    }

    // Funktion um einen Frosch zur Hand hinzuzufügen
    public void addFrogToHand(Color spieler, Frog frog) {
        List<Frog> frogs = playerFrogs.getOrDefault(spieler, new ArrayList<>());

        playerFrogs.put(spieler, frogs);
    }

    /**
     * Diese Methode entfernt einen Frosch an einem bestimmten Index aus der Hand eines Spielers.
     * Sie wird aufgerufen, wenn ein Spieler einen Frosch auf das Spielfeld legt.
     *
     * @param spieler Der Spieler, der den Frosch aus der Hand entfernt.
     * @param index   Der Index des Frosches, der aus der Hand des Spielers entfernt wird.
     */
    public void removeFrogFromHand(Color spieler, Integer index) {
        List<Frog> frogs = playerFrogs.get(spieler);
        if (frogs != null) {
            playerFrogs.get(spieler).remove((int) index);
        }
    }

    @Override
    public List<Color> getFrogsInHand(Color spieler) {
        System.out.println("getFrogsInHand(" + spieler + ") ausgeführt.");
        List<Frog> frogs = playerFrogs.getOrDefault(spieler, Collections.emptyList()); // Korrigierte Variable
        List<Color> colors = new ArrayList<>();

        for (Frog frog : frogs) {
            colors.add(frog.getColor());
            System.out.println("Frosch: " + frog.getColor());
        }
        return colors;
    }


    @Override
    public Set<Position> getBoard() {
        if (!gameStarted && gameRunning) {
            startGame(players().length);
            gameStarted = true;
        }

        System.out.println("getBoard() ausgeführt.");

        return board;
    }

    @Override
    public void clicked(Position position) {
        System.out.println("clicked(" + position + ") ausgeführt.");

        // Führe folgende Schritte nur aus, wenn das Spiel läuft
        if (gameRunning) {
            // Überprüfen, ob die Position auf dem Spielfeld liegt
            if (position.x() < -50 || position.x() >= 50 || position.y() < -50 || position.y() >= 50) {
                throw new IllegalArgumentException("Position außerhalb des Spielfelds");
            }

            // Überprüfen, ob die Position bereits von einem anderen Frosch besetzt ist
            if (board.contains(position)) {
                throw new IllegalArgumentException("Position bereits besetzt");
            }

            // Überprüfen, ob der Spieler einen Frosch in der Hand hat
            if (getFrogsInHand(players[0]).isEmpty()) {
                throw new IllegalArgumentException("Kein Frosch in der Hand");
            }

            List<Color> frogs = getFrogsInHand(players[0]);
            Color frog = frogs.get(0);

            Position frogPosition = new Position(frog, position.x(), position.y(), Color.None);

            board.add(frogPosition);

            // Frosch aus der Hand entfernen
            removeFrogFromHand(currentPlayer, 0);

            // Frosch aus dem Beutel nehmen
            Frog takenFrog = takeFrogFromBag();
            addFrogToHand(currentPlayer, takenFrog);
        }
    }

    @Override
    public void selectedFrogInHand(Color spieler, Color frog) {
        System.out.println("selectedFrogInHand(" + spieler + ", " + frog + ") ausgeführt.");
    }

    @Override
    public Color winner() {
        System.out.println("winner() ausgeführt.");
        return null;
    }

    @Override
    public boolean save(String filename) {
        System.out.println("save(" + filename + ") ausgeführt.");
        return false;
    }

    @Override
    public boolean load(String filename) {
        System.out.println("load(" + filename + ") ausgeführt.");
        return false;
    }

    @Override
    public int frogsInBag() {
        return bag.getNumberOfFrogs();
    }

    /**
     * Diese Methode startet das Spiel mit einer bestimmten Anzahl von Spielern.
     * Sie wird aufgerufen, wenn ein neues Spiel gestartet wird.
     * Sie nimmt für jeden Spieler zwei Frösche aus dem Beutel und fügt sie zum Vorrat hinzu.
     * Sie setzt auch die Spielerliste und den Spielstatus auf den Startzustand.
     *
     * @param spieler Die Anzahl der Spieler, die am Spiel teilnehmen.
     */
    public void startGame(int spieler) {
        if (spieler != 0) {
            System.out.println("startGame(" + spieler + ") ausgeführt.");
            // Jeweils zwei Frösche pro Spieler werden zu Beginn aus dem Beutel genommen
            for (int i = 0; i < spieler; i++) {
                for (int j = 0; j < 2; j++) {
                    Frog frog = bag.takeFrog();
                    addFrogToHand(players[i], frog);
                }
            }


            players = Arrays.copyOfRange(Color.values(), 0, spieler);
            gameStarted = true;
        }

        // Reload the frogsInHand for the gui
        for (Color player : players) {
            getFrogsInHand(player);
        }

        currentPlayer = players[0];
    }

    public Frog takeFrogFromBag() {
        System.out.println("takeFrogFromBag() ausgeführt.");
        return bag.takeFrog();
    }

    /**
     * Diese Methode fügt einen Frosch einer bestimmten Farbe in den Beutel ein.
     * Sie wird aufgerufen, wenn ein Frosch in den Beutel zurückgelegt werden muss.
     *
     * @param color Die Farbe des Frosches, der in den Beutel gelegt wird.
     */
    public void putFrogIntoBag(Color color) {
        Frog frog = new Frog(color, null);

        bag.putFrog(frog);
    }


}
