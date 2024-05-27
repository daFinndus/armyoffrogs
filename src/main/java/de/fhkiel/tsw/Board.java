package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.List;

public class Board {
    private static final int width_min = -50;
    private static final int width_max = 50;
    private static final int height_min = -50;
    private static final int height_max = 50;
    private Frog[][] board;

    public void placeFrogOnBoard(Color frog, Position position) {
        // Überprüfen Sie, ob die Position innerhalb der Grenzen des Spielfelds liegt
        if (position.x() < width_min || position.x() >= width_max || position.y() < height_min || position.y() >= height_max) {
            throw new IllegalArgumentException("Position außerhalb des Spielfelds");
        }

        // Überprüfen Sie, ob die Position bereits von einem anderen Frosch besetzt ist
        if (board[position.x()][position.x()] != null) {
            throw new IllegalArgumentException("Position bereits besetzt");
        }

        // Platzieren Sie den Frosch an der angegebenen Position
        board[position.x()][position.x()] = null;
    }

    public List<Object> getBoard() {
        return List.of();
    }
}
