package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.List;

/**
 * Dies ist die Klasse Frog, die einen Frosch repräsentiert.
 * Ein Frosch hat eine Farbe und eine Position.
 */
public class Frog {

    private Color color;

    public static Position position;

    public Frog(Color color, Position position) {
        this.color = color;
        this.position = position;
    }


    public Color getColor() {
        return color;
    }

    public static Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}