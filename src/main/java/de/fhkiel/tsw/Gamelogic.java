package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Game;
import de.fhkiel.tsw.armyoffrogs.Color;
import de.fhkiel.tsw.armyoffrogs.Position;

import java.util.*;

public class Gamelogic implements Game {

    private Color[] players = new Color[0];
    private Map<Color, List<Frog>> playerFrogs = new HashMap<>();

    public Bag bag = new Bag();

    @Override
    public boolean newGame(int spieler) {
        System.out.println("newGame(" + spieler + ") ausgeführt.");
        // Check if the number of players is allowed
        if (2 <= spieler && spieler <= 4) {
            players = new Color[spieler];

            // Fill the players array with the colors of the players
            Color[] colorOrder = {Color.Red, Color.Green, Color.Blue, Color.White};
            players = Arrays.copyOfRange(colorOrder, 0, spieler);

            // Beutel wird befüllt
            for (int i = 0; i < players.length; i++) {
                for (int j = 0; j < 10; j++) {
                    bag.putFrog(new Frog(players[i], null));
                }
            }

            bag.setGameRunning(true);
            return true;
        } else {
            players = new Color[0];
            return false;
        }
    }

    @Override
    public Color[] players() {
        System.out.println("players() ausgeführt.");
        return players;
    }

    @Override
    public String getInfo() {
        System.out.println("getInfo() ausgeführt.");
        return "Hier kann was gesagt werden.";
    }

    // Funktion um einen Frosch zur Hand hinzuzufügen
    public void addFrogToHand(Color spieler, Frog frog) {
        playerFrogs.computeIfAbsent(spieler, k -> new ArrayList<>()).add(frog);
    }

    // Funktion um einen Frosch an einem bestimmten Index zu entfernen
    public void removeFrogFromHand(Color spieler, Integer index) {
        List<Frog> frogs = playerFrogs.get(spieler);
        if (frogs != null) {
            playerFrogs.get(spieler).remove((int) index);
        }
    }

    @Override
    public List<Color> getFrogsInHand(Color spieler) {
        System.out.println("getFrogsInHand(" + spieler + ") ausgeführt.");
        List<Frog> frogs = playerFrogs.getOrDefault(spieler, Collections.emptyList()); // Korrigierte Variable
        List<Color> colors = new ArrayList<>();

        for (Frog frog : frogs) {
            colors.add(frog.getColor());
        }
        return colors;
    }


    @Override
    public Set<Position> getBoard() {
        System.out.println("getBoard() ausgeführt.");
        return new HashSet<>();
    }

    @Override
    public void clicked(Position position) {
        System.out.println("clicked(" + position + ") ausgeführt.");
    }

    @Override
    public void selectedFrogInHand(Color spieler, Color frog) {
        System.out.println("selectedFrogInHand(" + spieler + ", " + frog + ") ausgeführt.");
    }

    @Override
    public Color winner() {
        System.out.println("winner() ausgeführt.");
        return null;
    }

    @Override
    public boolean save(String filename) {
        System.out.println("save(" + filename + ") ausgeführt.");
        return false;
    }

    @Override
    public boolean load(String filename) {
        System.out.println("load(" + filename + ") ausgeführt.");
        return false;
    }

    @Override
    public int frogsInBag() {
        return bag.getNumberOfFrogs();
    }

    public void startGame(int spieler) {
        System.out.println("startGame(" + spieler + ") ausgeführt.");
        // Jeweils zwei Frösche pro Spieler werden zu Beginn aus dem Beutel genommen
        for (int i = 0; i < spieler; i++) {
            for (int j = 0; j < 2; j++) {
                Frog frog = bag.takeFrog();
                addFrogToHand(players[i], frog);
            }
        }


        players = Arrays.copyOfRange(Color.values(), 0, spieler);

    }

    public Frog takeFrogFromBag() {

        return bag.takeFrog();
    }

    public void putFrogIntoBag(Color color) {
        Frog frog = new Frog(color, null);

        bag.putFrog(frog);
    }


}
