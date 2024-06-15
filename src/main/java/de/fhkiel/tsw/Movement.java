package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class Movement extends Gamelogic {

    private static Position highlightedPosition;
    /**
     * Die Methode markiert einen Frosch auf dem Spielfeld.
     * Das wird relevant, wenn man zum Beispiel Frösche bewegen möchte.
     *
     * @param position Die Position des Frosches, der markiert werden soll.
     */
    public static Position highlightFrog(Position position) {
        LOGGER.log(System.Logger.Level.INFO, "highlightFrog(" + position + LOG_HELPER);

        Set<Position> newBoard = new HashSet<>();

        // Sicherstellen, dass die Position bereits besetzt ist von einem Frosch der Teamfarbe
        for (Position pos : board) {
            if (pos.equals(position) && pos.frog() == currentPlayer) {
                newBoard.add(new Position(pos.frog(), pos.x(), pos.y(), Color.Black));
                highlightedPosition = pos;
            } else {
                newBoard.add(new Position(pos.frog(), pos.x(), pos.y(), Color.None));
            }
        }
        board = newBoard;
        return highlightedPosition;
    }


    /**
     * Diese Methode findet heraus, falls es ein Highlight auf der Map gibt und wo.
     *
     * @return Die Position des Highlights.
     */
    public static Set<Position> getHighlight() {
        LOGGER.log(System.Logger.Level.INFO, "getHighlight(" + LOG_HELPER);

        Set<Position> highlightedPositions = new HashSet<>();

        for (Position pos : board) {
            if (pos.equals(highlightedPosition)) {
                highlightedPositions.add(pos);
                break;
            }
        }
        return highlightedPositions;
    }

    public static void removeHighlight() {
        highlightedPosition = null;
    }

    /**
     * Diese Methode bewegt einen Frosch auf dem Spielfeld.
     *
     * @param to   Die Position, zu der der Frosch bewegt wird.
     */
    public static void moveFrog(Position to) {
        LOGGER.log(System.Logger.Level.INFO, "moveFrog(" + to + LOG_HELPER);


        // Finden Sie den Frosch an der Ausgangsposition
        Position from = getHighlight().stream().findFirst().orElse(null);
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
        Position newPosition = new Position(from.frog(), to.x(), to.y(), currentPlayer);
        board.add(newPosition);

        highlightedPosition = null;
    }

    private static boolean isLeavingOpenPosition(Position to) {
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

    private static List<Position> getSurroundingPositions(Position position) {
        List<Position> surroundingPositions = new ArrayList<>();

        // Fügen Sie die Positionen um die gegebene Position herum zur Liste hinzu
        // Sie müssen die Logik implementieren, um die umliegenden Positionen basierend auf Ihrem Spielbrett zu bestimmen

        return surroundingPositions;
    }

    public static boolean isValidMove(Position from, Position to) {
        // Überprüfen Sie, ob die Bewegung in einer geraden Linie erfolgt
        if (from.x() != to.x() && from.y() != to.y()) {
            return false;
        }

        // Überprüfen Sie, ob der Frosch über andere Frösche springt
        boolean frogJumped = false;
        for (Position pos : board) {
            if (pos.frog() != null && pos != from && pos != to) {
                if ((from.x() == to.x() && pos.x() == from.x() && Math.min(from.y(), to.y()) < pos.y() && pos.y() < Math.max(from.y(), to.y())) ||
                        (from.y() == to.y() && pos.y() == from.y() && Math.min(from.x(), to.x()) < pos.x() && pos.x() < Math.max(from.x(), to.x()))) {
                    frogJumped = true;
                    break;
                }
            }
        }
        if (!frogJumped) {
            return false;
        }

        // Überprüfen Sie, ob der Frosch auf dem nächsten freien Platz landet
        for (Position pos : board) {
            if (pos.frog() == null && pos != to) {
                if ((from.x() == to.x() && pos.x() == from.x() && Math.min(from.y(), to.y()) < pos.y() && pos.y() < Math.max(from.y(), to.y())) ||
                        (from.y() == to.y() && pos.y() == from.y() && Math.min(from.x(), to.x()) < pos.x() && pos.x() < Math.max(from.x(), to.x()))) {
                    return false;
                }
            }
        }

        return true;
    }
}
