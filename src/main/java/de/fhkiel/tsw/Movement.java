package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.fhkiel.tsw.Frog.position;


public class Movement extends Gamelogic {

    private Position highlightedPosition;
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
    public Set<Position> getHighlight() {
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

    /**
     * Diese Methode bewegt einen Frosch auf dem Spielfeld.
     *
     * @param to   Die Position, zu der der Frosch bewegt wird.
     */
    public void moveFrog(Position to) {
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
        Position newPosition = new Position(from.frog(), to.x(), to.y(), Color.None);
        board.add(newPosition);

        highlightedPosition = null;
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
}
