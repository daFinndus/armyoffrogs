package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Gamelogic implements Game {
    private Color[] players;

    @Override
    public boolean newGame(int spieler) {
        // Check if the number of players is allowed
        if (2 <= spieler && spieler <= 4) {
            players = new Color[spieler];

            // Fill the players array with the colors of the players
            Color[] colorOrder = {Color.Red, Color.Green, Color.Blue, Color.White};
            players = Arrays.copyOfRange(colorOrder, 0, spieler);

            // Start the game
            startGame(spieler);
            return true;
        } else {
            players = new Color[0];
            return false;
        }
    }

    @Override
    public Color[] players() {
        return players;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public List<Color> getFrogsInHand(Color spieler) {
        return null;
    }

    @Override
    public Set<Position> getBoard() {
        return null;
    }

    @Override
    public void clicked(Position position) {
        System.out.println("Clicked on something..");
    }

    @Override
    public void selectedFrogInHand(Color spieler, Color frog) {
        System.out.println("Selected frog in hand..");
    }

    @Override
    public Color winner() {
        return null;
    }

    @Override
    public boolean save(String filename) {
        return false;
    }

    @Override
    public boolean load(String filename) {
        return false;
    }

    private Bag bag = new Bag(40);

    @Override
    public int frogsInBag() {
        return bag.getNumberOfFrogs();
    }

    public void startGame(int spieler) {
        bag = new Bag(10 * spieler);

        for (int i = 0; i < (2 * spieler); ++i) {
            bag.takeFrog();
        }

        players = Arrays.copyOfRange(Color.values(), 0, spieler);

    }

    public void takeFrogFromBag() {
        bag.takeFrog();
    }
}