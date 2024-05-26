package de.fhkiel.tsw;

public class Bag {
    private int numberOfFrogs = 0;

    public Bag(int numberOfFrogs) {
        this.numberOfFrogs = numberOfFrogs;
    }

    // Returns the number of frogs in the bag
    public int getNumberOfFrogs() {
        return numberOfFrogs;
    }

    // Removes a frog from the bag
    public void takeFrog() {
        if (numberOfFrogs > 0) {
            numberOfFrogs = numberOfFrogs - 1;
        }
    }

    // Adds a frog to the bag
    public void putFrog() {
        if (numberOfFrogs < 40) {
            numberOfFrogs = numberOfFrogs + 1;
        }
    }
}
