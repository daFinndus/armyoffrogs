package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;

public class Gameround {
    private Gamelogic logic;

    public String action = "bewegen";
    public int round;

    public Color getCurrentPlayer() {
        return logic.players()[0];
    }

    public String getActionName() {
        return action;
    }
}
