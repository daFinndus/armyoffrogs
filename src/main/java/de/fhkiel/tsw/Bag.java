package de.fhkiel.tsw;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class Bag {
    Random random = new Random();
    private List<Frog> frogs;

    public Bag() {
        this.frogs = new ArrayList<>();
    }

    // Returns the number of frogs in the bag
    public int getNumberOfFrogs() {
        return frogs.size();
    }

    // Removes a frog from the bag
    public Frog takeFrog() {
        if (!frogs.isEmpty()) {
            return frogs.remove(random.nextInt(frogs.size()));
        }
        return null;
    }

    // Adds a frog to the bag
    public void putFrog(Frog frog) {
        if (frogs.size() < 40) {
            frogs.add(frog);
        }
    }
}
