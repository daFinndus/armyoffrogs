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
        if (frogs.size() == 0) {
            return 40;
        } else {
            return frogs.size();
        }
    }

    public List<Frog> getFrogList() {
        return frogs;
    }

    // Removes a frog from the bag and returns it
    public Frog takeFrog() {
        if (!frogs.isEmpty()) {
            Frog frog = frogs.get(random.nextInt(frogs.size()));
            frogs.remove(random.nextInt(frogs.size()));

            return frog;
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
