package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.HashSet;
import java.util.Set;


public class Movement {
    private Position highlightedPosition = null;
    private Gamelogic logic;

    public Movement(Gamelogic logic) {
        this.logic = logic;
    }


    /**
     * Die Methode markiert einen Frosch auf dem Spielfeld.
     * Das wird relevant, wenn man zum Beispiel Frösche bewegen möchte.
     *
     * @param position Die Position des Frosches, der markiert werden soll.
     */
    public void highlightPosition(Position position) {
        Set<Position> board = logic.getBoard();
        Set<Position> newBoard = board;
        boolean found = false;

        // Sicherstellen, dass die Position bereits besetzt ist von einem Frosch der Teamfarbe
        for (Position pos : board) {
            if (pos.equals(position) && pos.frog() == logic.currentPlayer) {
                // Entfernen des alten Frosches und Hinzufügen des neuen markierten Frosches
                newBoard.remove(new Position(pos.frog(), pos.x(), pos.y(), Color.None));
                newBoard.add(new Position(pos.frog(), pos.x(), pos.y(), Color.Black));
                highlightedPosition = new Position(pos.frog(), pos.x(), pos.y(), Color.Black);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Das ist nicht dein Frosch!");
        }

        logic.setBoard(newBoard);
    }


    /**
     * Diese Methode findet heraus, falls es ein Highlight auf der Map gibt und wo.
     *
     * @return Die Position des Highlights.
     */
    public Set<Position> getHighlight() {
        Set<Position> board = logic.getBoard();
        Set<Position> highlightedPositions = new HashSet<>();

        for (Position pos : board) {
            if (pos.equals(highlightedPosition)) {
                highlightedPositions.add(pos);
                break;
            }
        }

        return highlightedPositions;
    }

    public void removeHighlight() {
        highlightedPosition = null;
    }

    /**
     * Diese Methode überprüft, ob ein Highlight vorhanden ist.
     *
     * @return true, falls ein Highlight vorhanden ist, sonst false.
     */
    public boolean validateHighlight() {
        return highlightedPosition != null;
    }

    /**
     * Diese Methode überprüft, ob die platzierten Frösche alle ungleich der
     * currentPlayer Farbe sind.
     *
     * @return
     */
    public boolean validateBoard() {
        Set<Position> board = logic.getBoard();
        boolean valid = true;

        for (Position pos : board) {
            if (pos.frog() == logic.currentPlayer) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    /**
     * Diese Methode überprüft, ob ein Zug gültig ist.
     *
     * @return true, falls der Zug gültig ist, sonst false.
     */
    public boolean validMove() {
        return true;
    }
}
