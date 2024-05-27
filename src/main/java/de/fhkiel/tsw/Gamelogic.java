package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

public class Gamelogic implements Game {

    private Color[] players = new Color[0];
    private Map<Color, List<Frog>> playerFrogs = new HashMap<>();

    Set<Position> board = new HashSet<>();
    public Bag bag = new Bag();

    boolean gameRunning = false;
    boolean gameStarted = false;

    Color currentPlayer;

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

            // Beutel wird befüllt
            System.out.println("Der Beutel hat aktuell " + bag.getNumberOfFrogs() + " Frösche.");
            System.out.println("Beutel wird befüllt..");

            try {
                for (int i = 0; i < players.length; i++) {
                    for (int j = 0; j < 10; j++) {
                        bag.putFrog(new Frog(players[i], null));
                    }
                }
            } catch (Exception e) {
                System.out.println("Fehler beim Befüllen des Beutels: " + e);
            }

            System.out.println("Der Beutel hat jetzt " + bag.getNumberOfFrogs() + " Frösche.");

            // Change necessary variables
            gameRunning = true;
            bag.setGameRunning(gameRunning);

            return true;
        } else {
            players = new Color[0];
            return false;
        }
    }

    // Da gibt es noch Schwierigkeiten
    // Funktion um alle Variablen zurückzusetzen
    public void resetGame() {
        System.out.println("resetGame() ausgeführt.");

        players = new Color[0];
        playerFrogs = new HashMap<>();

        board = new HashSet<>();
        bag = new Bag();

        gameRunning = false;
        gameStarted = false;

        currentPlayer = players[0];

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
        playerFrogs.computeIfAbsent(spieler, k -> new ArrayList<>()).add(frog);
    }

    // Funktion um einen Frosch an einem bestimmten Index zu entfernen
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
            System.out.println("Spiel wird gestartet..");
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
            currentPlayer = players[0];
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

            // Frosch aus dem Beutel nehmen
            takeFrogFromBag();
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
    }

    public Frog takeFrogFromBag() {

        return bag.takeFrog();
    }

    public void putFrogIntoBag(Color color) {
        Frog frog = new Frog(color, null);

        bag.putFrog(frog);
    }


}
