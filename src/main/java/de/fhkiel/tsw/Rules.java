package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

/**
 * Diese Klasse dient primär bestimmten Überprüfungen, die im Spielverlauf benötigt werden.
 * Dazu gehören die Überprüfung der Gültigkeit einer Position und die Überprüfung, ob die Frösche
 * auf dem Spielfeld die gleiche Farbe haben.
 */
public class Rules {

    /**
     * Diese Methode überprüft, ob der Klick auf eine Position gültig ist.
     *
     * @param position    Die Position, auf die geklickt wurde.
     * @param frogsInHand Die Frösche, die der Spieler in der Hand hat.
     */
    public void validateClick(Position position, List<Color> frogsInHand) {
        if (position.x() < -50 || position.x() > 50 || position.y() < -50 || position.y() > 50) {
            throw new IllegalArgumentException("Position außerhalb des Spielfelds");
        }

        if (frogsInHand.isEmpty()) {
            throw new IllegalArgumentException("Kein Frosch in der Hand");
        }
    }

    /**
     * Diese Methode überprüft, ob die Position, an der der Frosch platziert werden soll, gültig ist.
     *
     * @param position Die Position, an der der Frosch platziert werden soll.
     * @param frog     Die Farbe des Frosches, der platziert werden soll.
     * @param player   Die Farbe des Spielers, der den Frosch platziert.
     * @param board    Das Spielfeld, auf dem der Frosch platziert werden soll.
     */
    public void validatePlace(Position position, Color frog, Color player, Set<Position> board) {
        for (Position pos : board) {
            if (pos.x() == position.x() && pos.y() == position.y()) {
                throw new IllegalArgumentException("Position bereits belegt");
            }
        }

        if (neighborsAreSameColor(player, frog, position, board)) {
            throw new IllegalArgumentException("Du kannst nicht an einen eigenen Frosch mit einem eigenen Frosch anlegen");
        }

        // Überprüfen, dass der Frosch an einem anderen Frosch hängt, wenn das Spielfeld nicht leer ist
        if (!board.isEmpty() && !linkExists(position, frog, player, board)) {
            throw new IllegalArgumentException("Der Frosch haengt an keinem anderen Frosch an");
        }
    }

    /**
     * Diese Methode überprüft, ob der zwei Felder aneinander liegen oder nicht.
     *
     * @param position Die Position, die überprüft werden soll.
     * @param other    Die Position, die mit der ersten Position verglichen wird.
     * @return true, wenn die beiden Positionen aneinander liegen, sonst false.
     */
    public boolean isNeighbor(Position position, Position other) {
        // Überprüfe, ob die Differenz der x- und y-Koordinaten jeweils höchstens 1 beträgt
        int diffX = Math.abs(position.x() - other.x());
        int diffY = Math.abs(position.y() - other.y());

        return diffX <= 1 && diffY <= 1;
    }


    /**
     * Diese Methode überprüft, ob ein Link zwischen zwei Positionen existiert.
     *
     * @param position Die Position, die überprüft werden soll.
     * @param frog     Die Farbe des Frosches, der überprüft werden soll.
     * @param player   Die Farbe des Spielers, der den Frosch platziert hat.
     * @param board    Das Spielfeld, auf dem der Frosch platziert wurde.
     * @return true, wenn ein Link existiert, sonst false.
     */
    public boolean linkExists(Position position, Color frog, Color player, Set<Position> board) {
        for (Position pos : board) {
            // Überspringe die aktuelle Position
            if (pos.x() == position.x() && pos.y() == position.y()) {
                continue;
            }

            // Überprüfe, ob die Positionen benachbart sind
            if (isNeighbor(pos, position)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Diese Methode überprüft, ob die Frösche um eine Position herum die gleiche Farbe haben.
     * Das soll verhindern, dass ein Spieler einen eigenen Frosch an einen eigenen Frosch anlegt.
     *
     * @param position Die Position, um die herum die Frösche überprüft werden sollen.
     * @param board    Das Spielfeld, auf dem die Frösche platziert sind.
     * @return true, wenn die Frösche um die Position herum die gleiche Farbe haben, sonst false.
     */
    public boolean neighborsAreSameColor(Color player, Color color, Position position, Set<Position> board) {
        if (!board.isEmpty()) {
            int[] dx = {-1, 0, 1};
            int[] dy = {-1, 0, 1};

            for (int x : dx) {
                for (int y : dy) {

                    // Überspringe die eigene Position
                    if (x == 0 && y == 0) {
                        continue;
                    }

                    Position neighbor = new Position(color, position.x() + x, position.y() + y, null);

                    // Wenn die Position mit dem Nachbar übereinstimmt und der Frosch die gleiche Farbe hat
                    for (Position pos : board) {
                        if (pos.x() == neighbor.x() && pos.y() == neighbor.y() && pos.frog() == color && pos.frog() == player) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Durch diese Funktion wird überprüft, ob alle Frösche auf dem Spielfeld die gleiche Farbe haben.
     *
     * @param color Die Farbe, die die Frösche haben sollen.
     * @param board Das Spielfeld, auf dem die Frösche platziert sind.
     * @return true, wenn alle Frösche die gleiche Farbe haben, sonst false.
     */
    public boolean allFrogsAreColor(List<Color> colors, Set<Position> board) {
        if (board.isEmpty()) {
            return false;
        } else {
            for (Color color : colors) {
                for (Position pos : board) {
                    if (pos.frog() != color) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
