package de.fhkiel.tsw;

public class Bag {
    private int numberOfFrogs = 40;

    public Bag(int numberOfFrogs) {
        this.numberOfFrogs = numberOfFrogs;
    }

    public int getNumberOfFrogs() {
        return numberOfFrogs;
    }

    public void takeFrog() {
        if (numberOfFrogs > 0) {
            numberOfFrogs = numberOfFrogs - 1;
        }
    }
}
