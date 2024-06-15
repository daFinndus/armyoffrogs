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
    private Set<Position> board = new HashSet<>();

    private Bag bag = new Bag();
    private Gameround round = new Gameround();

    private Rules rules = new Rules();
    private Movement movement = new Movement(this);

    // Die Variablen werden genutzt, um den Spielstatus zu speichern
    boolean gameRunning = false;
    boolean gameStarted = false;

    // Die Variable wird genutzt, um den aktuellen Spieler zu speichern
    // Die andere um die aktuelle Phase zu speichern
    protected Color currentPlayer;
    protected Gamephase currentPhase = Gamephase.PLACE_FROG;

    // Die Variablen werden für die Lognachricht und den Logger genutzt
    public static final String LOG_HELPER = ") ausgefuehrt.";
    public static final System.Logger LOGGER = System.getLogger("Gamelogic");


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
        LOGGER.log(System.Logger.Level.INFO, "removeFrogFromHand(" + spieler + ", " + index + LOG_HELPER);

        List<Frog> frogs = playerFrogs.get(spieler);
        if (frogs != null && index != null && index >= 0 && index < frogs.size()) {
            frogs.remove((int) index);
        } else {
            throw new IllegalArgumentException("Frosch konnte nicht aus der Hand entfernt werden");
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
    public void clicked(Position position) {
        LOGGER.log(System.Logger.Level.INFO, "clicked(" + position + LOG_HELPER);

        // Führe folgende Schritte nur aus, wenn das Spiel läuft
        if (gameRunning) {
            // Überprüfe, ob die Position regelrecht ist
            rules.validateClick(position, getFrogsInHand(currentPlayer));

            switch (currentPhase) {
                case MOVE_FROG:
                    if (movement.validateHighlight()) {
                        moveFrog(position);
                    } else {
                        movement.highlightPosition(position);
                    }
                    break;
                case PLACE_FROG:
                    // Kontrollieren, ob ein Frosch ausgewählt ist, wenn nicht, wird der erste Frosch ausgewählt
                    Color frog = selectedPlayerFrog.getOrDefault(currentPlayer, getFrogsInHand(currentPlayer).get(0));

                    // Überprüfen, ob man noch Frösche auf der Hand hat
                    if (frog == Color.Black) {
                        throw new IllegalArgumentException("Kein Frosch in der Hand");
                    }

                    placeFrog(position, frog);

                    break;
                default:
                    throw new IllegalStateException("Ungültige Spielphase: " + currentPhase);
            }
        }
    }

    /**
     * Diese Methode bewegt einen Frosch auf dem Spielfeld.
     *
     * @param to Die Position, zu der der Frosch bewegt wird.
     */
    public void moveFrog(Position to) {
        LOGGER.log(System.Logger.Level.INFO, "moveFrog(" + to + LOG_HELPER);

        // Auswählen eines markierten Frosches auf dem Spielfeld
        Position from = movement.getHighlight().stream().findFirst().orElse(null);
        LOGGER.log(System.Logger.Level.INFO, "Der Frosch, der bewegt wird, ist: " + from);

        if (from == null) {
            throw new IllegalArgumentException("Kein Frosch ausgewählt");
        }

        if (movement.validateBoard()) {
            currentPhase = Gamephase.PLACE_FROG;
            throw new IllegalArgumentException("Das Spielfeld besteht nur aus Fröschen anderer Farbe");
        }

        // Überprüfen, ob die Ausgangs- und Zielposition gleich sind
        if (!board.remove(from) || from.equals(to)) {
            throw new IllegalArgumentException("Der Frosch konnte nicht von der Ausgangsposition entfernt werden");
        }

        // Fügen Sie einen neuen Frosch an der Zielposition hinzu
        Position newPosition = new Position(from.frog(), to.x(), to.y(), currentPlayer);
        board.add(newPosition);

        movement.removeHighlight();

        currentPhase = Gamephase.PLACE_FROG;
        LOGGER.log(System.Logger.Level.INFO, "Die aktuelle Phase ist nun: " + currentPhase);
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
        availableColors.addAll(getFrogsInHand(currentPlayer));

        // Überprüfen, ob nur solche Farben auf dem Spielfeld sind
        if (rules.allFrogsAreColor(availableColors, board)) {
            endTurn();
            throw new IllegalArgumentException("Es sind nur Frösche der eigenen Farbe auf dem Spielfeld");
        }

        rules.validatePlace(position, frog, currentPlayer, board);

        Position frogPosition = new Position(frog, position.x(), position.y(), Color.None);
        board.add(frogPosition);

        // Frosch aus der Hand entfernen
        LOGGER.log(System.Logger.Level.INFO, "Der Spieler " + currentPlayer + " hat die Frösche  " + getFrogsInHand(currentPlayer));
        int index = indexOfFrog(getFrogsInHand(currentPlayer), frog);
        removeFrogFromHand(currentPlayer, index);

        // Frosch aus dem Beutel nehmen
        addFrogToHand(currentPlayer, takeFrogFromBag());

        // Beendet den Zug des aktuellen Spielers
        endTurn();
    }

    /**
     * Diese Methode beendet den Zug des aktuellen Spielers.
     * Dabei wird die Methode von Gameround genutzt.
     * Ebenfalls setzt sie, wenn möglich, die Phase auf MOVE_FROG.
     */
    public void endTurn() {
        // Setze den nächsten Spieler und clear die selectedPlayerFrog Map
        selectedPlayerFrog.clear();
        currentPlayer = round.endTurn(currentPlayer, players);
        LOGGER.log(System.Logger.Level.INFO, "Der aktuelle Spieler ist nun: " + currentPlayer);


        // If enough frogs are placed, change the phase
        if (board.size() >= 2) {
            currentPhase = Gamephase.MOVE_FROG;
        } else {
            currentPhase = Gamephase.PLACE_FROG;
        }

        LOGGER.log(System.Logger.Level.INFO, "Die aktuelle Phase ist nun: " + currentPhase);
    }

    /**
     * Diese Methode gibt den Index eines Frosches in einer Liste von Fröschen zurück.
     *
     * @param frogsInHand Die Liste der Frösche, in der der Frosch gesucht wird.
     * @param frog        Der Frosch, der gesucht wird.
     * @return Der Index des Frosches in der Liste, -1 wenn der Frosch nicht gefunden wurde.
     */
    public int indexOfFrog(List<Color> frogsInHand, Color frog) {
        LOGGER.log(System.Logger.Level.INFO, "indexOfFrog(" + frogsInHand + ", " + frog + LOG_HELPER);

        int indexToRemove = -1;

        for (int i = 0; i < frogsInHand.size(); i++) {
            if (frogsInHand.get(i) == frog) {
                indexToRemove = i;
                break;
            }
        }

        LOGGER.log(System.Logger.Level.INFO, "Der Index des Frosches ist: " + indexToRemove);
        return indexToRemove;
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
            currentPhase = Gamephase.PLACE_FROG;
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

    @Override
    public Set<Position> getBoard() {
        if (!gameStarted && gameRunning) {
            startGame(players.length);
            gameStarted = true;
        }

        LOGGER.log(System.Logger.Level.INFO, "getBoard is currently: " + board);

        return new HashSet<>(board);
    }

    public Set<Position> setBoard(Set<Position> board) {
        this.board = board;
        return board;
    }

    public Bag getBag() {
        return bag;
    }

    public Gameround getRound() {
        return round;
    }

    public Map<Color, List<Frog>> getPlayerFrogs() {
        return playerFrogs;
    }

    public Map<Color, List<Frog>> setPlayerFrogs(Map<Color, List<Frog>> playerFrogs) {
        this.playerFrogs = playerFrogs;
        return playerFrogs;
    }

    public Color setCurrentPlayer(Color currentPlayer) {
        LOGGER.log(System.Logger.Level.INFO, "setCurrentPlayer(" + currentPlayer + LOG_HELPER);
        this.currentPlayer = currentPlayer;
        this.round.setCurrentPlayer(currentPlayer);
        return currentPlayer;
    }
}
