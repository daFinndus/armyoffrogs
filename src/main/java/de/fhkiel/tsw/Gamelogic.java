package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

/**
 * Die Gamelogic Klasse implementiert die Spiellogik für das Spiel "Army of Frogs".
 * Sie verwaltet den Spielzustand, einschließlich der Spieler, der Frösche und des Spielbretts.
 * Sie bietet auch Methoden zum Starten eines neuen Spiels, zum Zurücksetzen des Spiels,
 * zum Hinzufügen und Entfernen von Fröschen und zum Speichern und Laden des Spielzustands.
 */
public class Gamelogic implements Game {

    // Das ist die Variable für die Spielerliste und für die Spielerfrösche
    private Color[] players = new Color[0];
    private Map<Color, List<Frog>> playerFrogs = new EnumMap<>(Color.class);
    private Map<Color, Color> selectedPlayerFrog = new EnumMap<>(Color.class);

    // Das sind die Variablen für das Spielbrett und den Beutel
    Set<Position> board = new HashSet<>();

    public Bag bag = new Bag();
    public Gameround round = new Gameround();

    private Rules rules = new Rules();

    // Die Variablen werden genutzt, um den Spielstatus zu speichern
    boolean gameRunning = false;
    boolean gameStarted = false;

    // Die Variable wird genutzt, um den aktuellen Spieler zu speichern
    private Color currentPlayer;

    // Die Variablen werden für die Lognachricht und den Logger genutzt
    private static final String LOG_HELPER = ") ausgefuehrt.";
    private static final System.Logger LOGGER = System.getLogger("Gamelogic");

    @Override
    public boolean newGame(int spieler) {
        try {
            if (gameRunning) {
                resetGame();
            }
        } catch (Exception e) {
            LOGGER.log(System.Logger.Level.ERROR, "Fehler beim Zurücksetzen des Spiels: " + e);
        }

        LOGGER.log(System.Logger.Level.INFO, "newGame(" + spieler + LOG_HELPER);

        // Überprüfen, ob die Anzahl der Spieler gültig ist
        if (2 <= spieler && spieler <= 4) {
            players = Arrays.copyOfRange(Color.values(), 0, spieler);

            // Setzen der Spieler und Frösche im Beutel
            try {
                for (Color player : players) {
                    for (int j = 0; j < 10; j++) {
                        bag.putFrog(new Frog(player, null));
                    }
                }
            } catch (Exception e) {
                LOGGER.log(System.Logger.Level.ERROR, "Fehler beim Befüllen des Beutels: " + e);
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
        LOGGER.log(System.Logger.Level.INFO, "resetGame(" + LOG_HELPER);

        currentPlayer = null;

        players = new Color[0];
        playerFrogs.clear();
        board.clear();

        bag = new Bag();
        round = new Gameround();

        gameRunning = false;
        gameStarted = false;
    }

    @Override
    public Color[] players() {
        LOGGER.log(System.Logger.Level.INFO, "players(" + LOG_HELPER);
        return players;
    }

    @Override
    public String getInfo() {
        LOGGER.log(System.Logger.Level.INFO, "getInfo(" + LOG_HELPER);
        return "Das ist das Venom Projekt von Finn, Jonas und Darren!";
    }

    /**
     * Diese Methode fügt einen Frosch einer bestimmten Farbe dem Vorrat eines Spielers hinzu
     *
     * @param spieler
     */
    public void addFrogToHand(Color spieler, Frog frog) {
        List<Frog> frogs = playerFrogs.getOrDefault(spieler, new ArrayList<>());
        frogs.add(frog);
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
        LOGGER.log(System.Logger.Level.INFO, "getFrogsInHand(" + spieler + LOG_HELPER);
        List<Frog> frogs = playerFrogs.getOrDefault(spieler, Collections.emptyList()); // Korrigierte Variable
        List<Color> colors = new ArrayList<>();

        for (Frog frog : frogs) {
            if (frog != null) {
                colors.add(frog.getColor());
            } else {
                colors.add(Color.Black);
            }
        }
        return colors;
    }


    @Override
    public Set<Position> getBoard() {
        if (!gameStarted && gameRunning) {
            startGame(players.length);
            gameStarted = true;
        }

        LOGGER.log(System.Logger.Level.INFO, "getBoard(" + LOG_HELPER);

        return board;
    }

    @Override
    public void clicked(Position position) {
        LOGGER.log(System.Logger.Level.INFO, "clicked(" + position + LOG_HELPER);

        // Führe folgende Schritte nur aus, wenn das Spiel läuft
        if (gameRunning) {
            // Überprüfe, ob die Position regelrecht ist
            rules.validateClick(position, getFrogsInHand(currentPlayer));

            Color frog = selectedPlayerFrog.getOrDefault(currentPlayer, getFrogsInHand(currentPlayer).get(0));

            if (frog == Color.Black) {
                throw new IllegalArgumentException("Kein Frosch in der Hand");
            }

            // Die Funktion dient dem Platzieren des Frosches auf dem Spielfeld
            placeFrog(position, frog);

            // Beendet den Zug des aktuellen Spielers
            endTurn();
        }
    }


    /**
     * Die Methode markiert einen Frosch auf dem Spielfeld.
     * Das wird relevant, wenn man zum Beispiel Frösche bewegen möchte.
     *
     * @param position Die Position des Frosches, der markiert werden soll.
     * @return
     */
    public Position highlightFrog(Position position) {
        LOGGER.log(System.Logger.Level.INFO, "highlightFrog(" + position + LOG_HELPER);

        Set<Position> newBoard = new HashSet<>();

        // Sicherstellen, dass die Position bereits besetzt ist von einem Frosch der Teamfarbe
        for (Position pos : board) {
            if (pos.equals(position) && pos.frog() == currentPlayer) {
                newBoard.add(new Position(pos.frog(), pos.x(), pos.y(), Color.Black));
            } else {
                newBoard.add(new Position(pos.frog(), pos.x(), pos.y(), Color.None));
            }
        }
        board = newBoard;
        return position;
    }


    /**
     * Diese Methode findet heraus, falls es ein Highlight auf der Map gibt und wo.
     *
     * @return Die Position des Highlights.
     */
    public Set<Position> getHighlight() {
        LOGGER.log(System.Logger.Level.INFO, "getHighlight(" + LOG_HELPER);

        Set<Position> highlightedPositions = new HashSet<>();

        for (Position pos : board) {
            if (pos.border() == Color.Black) {
                highlightedPositions.add(pos);
                break;
            }
        }
        return highlightedPositions;
    }

    /**
     * Diese Methode bewegt einen Frosch auf dem Spielfeld.
     *
     * @param to   Die Position, zu der der Frosch bewegt wird.
     */
    public void moveFrog(Position to) {
        LOGGER.log(System.Logger.Level.INFO, "moveFrog(" + to + LOG_HELPER);


        // Finden Sie den Frosch an der Ausgangsposition
        Position from = highlightFrog(to);
        if (from == null) {
            throw new IllegalArgumentException("Kein Frosch ausgewählt");
        }

        // Überprüfen Sie, ob die Ausgangs- und Zielposition gleich sind
        if (from.equals(to)) {
            throw new IllegalArgumentException("Die Zielposition darf nicht die Ausgangsposition sein");
        }

        // Überprüfen Sie, ob die Bewegung eine Position zwischen zwei Fröschen freilassen würde
        if (isLeavingOpenPosition(to)) {
            throw new IllegalArgumentException("Eine Bewegung darf keine Position zwischen zwei Fröschen freilassen");
        }

        // Entfernen Sie den Frosch von der Ausgangsposition
        board.remove(from);

        // Fügen Sie einen neuen Frosch an der Zielposition hinzu
        Position newPosition = new Position(from.frog(), to.x(), to.y(), Color.None);
        board.add(newPosition);
    }

    private boolean isLeavingOpenPosition( Position to) {
        Position from = highlightFrog(to);
        // Überprüfen Sie die Positionen um die Ausgangsposition herum
        for (Position pos : getSurroundingPositions(from)) {
            // Wenn eine der umliegenden Positionen einen Frosch enthält und nicht die Zielposition ist
            if (!pos.equals(to) && pos.frog() != null) {
                // Überprüfen Sie die Positionen um diese Position herum
                for (Position pos2 : getSurroundingPositions(pos)) {
                    // Wenn eine der umliegenden Positionen einen Frosch enthält und nicht die Ausgangsposition ist
                    if (!pos2.equals(from) && pos2.frog() != null) {
                        // Es gibt zwei Frösche um die Ausgangsposition herum, also würde die Bewegung eine Position freilassen
                        return true;
                    }
                }
            }
        }

        // Es gibt keine zwei Frösche um die Ausgangsposition herum, also würde die Bewegung keine Position freilassen
        return false;
    }

    private List<Position> getSurroundingPositions(Position position) {
        List<Position> surroundingPositions = new ArrayList<>();

        // Fügen Sie die Positionen um die gegebene Position herum zur Liste hinzu
        // Sie müssen die Logik implementieren, um die umliegenden Positionen basierend auf Ihrem Spielbrett zu bestimmen

        return surroundingPositions;
    }

    /**
     * Diese Methode dient dem Platzieren eines Frosches auf dem Spielfeld.
     *
     * @param position Die Position, an der der Frosch platziert werden soll.
     * @param frog     Die Farbe des Frosches, der platziert werden soll.
     */
    public void placeFrog(Position position, Color frog) {
        LOGGER.log(System.Logger.Level.INFO, "placeFrog(" + position + ", " + frog + LOG_HELPER);

        // Erschaffen einer Liste an Farben, die überprüft werden soll
        List<Color> availableColors = new ArrayList<>();
        availableColors.add(currentPlayer);

        // Hinzufügen der Frösche aus dem Vorrat
        for (Color color : getFrogsInHand(currentPlayer)) {
            availableColors.add(color);
        }

        // Überprüfen, ob nur solche Farben auf dem Spielfeld sind
        if (rules.allFrogsAreColor(availableColors, board)) {
            endTurn();
            throw new IllegalArgumentException("Es sind nur Frösche der eigenen Farbe auf dem Spielfeld");
        }

        rules.validatePlace(position, frog, currentPlayer, board);

        Position frogPosition = new Position(frog, position.x(), position.y(), Color.None);
        board.add(frogPosition);

        // Frosch aus der Hand entfernen
        removeFrogFromHand(currentPlayer, 0);

        // Frosch aus dem Beutel nehmen
        addFrogToHand(currentPlayer, takeFrogFromBag());
    }

    @Override
    public void selectedFrogInHand(Color spieler, Color frog) {
        LOGGER.log(System.Logger.Level.INFO, "selectedFrogInHand(" + spieler + ", " + frog + LOG_HELPER);

        if (frog != Color.Black) {
            selectedPlayerFrog.put(spieler, frog);
        }
    }

    @Override
    public Color winner() {
        LOGGER.log(System.Logger.Level.INFO, "winner() ausgefuehrt.");
        return null;
    }

    @Override
    public boolean save(String filename) {
        LOGGER.log(System.Logger.Level.INFO, "save(" + filename + LOG_HELPER);
        return false;
    }

    @Override
    public boolean load(String filename) {
        LOGGER.log(System.Logger.Level.INFO, "load(" + filename + LOG_HELPER);
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
        if (spieler > 0) {
            LOGGER.log(System.Logger.Level.INFO, "startGame(" + spieler + LOG_HELPER);

            // Jeweils zwei Frösche pro Spieler werden zu Beginn aus dem Beutel genommen
            for (int i = 0; i < spieler; i++) {
                for (int j = 0; j < 2; j++) {
                    addFrogToHand(players[i], bag.takeFrog());
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

    /**
     * Die Methode entfernt einen Frosch aus dem Beutel.
     *
     * @return
     */
    public Frog takeFrogFromBag() {
        LOGGER.log(System.Logger.Level.INFO, "takeFrogFromBag(" + LOG_HELPER);
        return bag.takeFrog();
    }

    /**
     * Diese Methode fügt einen Frosch einer bestimmten Farbe in den Beutel ein.
     * Sie wird aufgerufen, wenn ein Frosch in den Beutel zurückgelegt werden muss.
     *
     * @param color Die Farbe des Frosches, der in den Beutel gelegt wird.
     */
    public void putFrogIntoBag(Color color) {
        bag.putFrog(new Frog(color, null));
    }

    /**
     * Diese Methode beendet den Zug des aktuellen Spielers.
     * Dabei wird die Methode von Gameround genutzt.
     */
    public void endTurn() {
        // Setze den nächsten Spieler und clear die selectedPlayerFrog Map
        selectedPlayerFrog.clear();
        currentPlayer = round.endTurn(currentPlayer, players);
        LOGGER.log(System.Logger.Level.INFO, "Der aktuelle Spieler ist nun: " + currentPlayer);
    }
}
