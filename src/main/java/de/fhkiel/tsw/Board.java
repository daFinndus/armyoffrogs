package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import java.util.List;

public class Board {
    private static final int width_min = -50;
    private static final int width_max = 50;
    private static final int height_min = -50;
    private static final int height_max = 50;
    private Frog[][] board;

    public void placeFrogOnBoard(Color frog, Position position) {
        // Überprüfen Sie, ob die Position innerhalb der Grenzen des Spielfelds liegt
        if (position.getX() < width_min || position.getX() >= width_max || position.getY() < height_min || position.getY() >= height_max) {
            throw new IllegalArgumentException("Position außerhalb des Spielfelds");
        }

        // Überprüfen Sie, ob die Position bereits von einem anderen Frosch besetzt ist
        if (board[position.getX()][position.getY()] != null) {
            throw new IllegalArgumentException("Position bereits besetzt");
        }

        // Platzieren Sie den Frosch an der angegebenen Position
        board[position.getX()][position.getY()] = null;
    }

    public List<Object> getBoard() {
        return List.of();
    }
}
