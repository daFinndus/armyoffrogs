package de.fhkiel.tsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The Bag class represents the bag of frogs.
 */
public class Bag {
  Random random = new Random();
  private List<Frog> frogs;

  boolean gameRunning = false;


  public Bag() {
    this.frogs = new ArrayList<>();
  }

  public void setGameRunning(boolean gameRunning) {
    this.gameRunning = gameRunning;
  }

  /**
   * Returns the number of frogs in the bag.
   */
  // Returns the number of frogs in the bag
  public int getNumberOfFrogs() {
    if (gameRunning) {
      return frogs.size();
    } else {
      return 40;
    }
  }

  /**
   * Returns the list of frogs in the bag.
   */
  public List<Frog> getFrogList() {
    return frogs;
  }

  /**
   * Removes a frog from the bag and returns it.
   */
  // Removes a frog from the bag and returns it
  public Frog takeFrog() {
    if (!frogs.isEmpty()) {
      Frog frog = frogs.get(random.nextInt(frogs.size()));
      frogs.remove(random.nextInt(frogs.size()));

      return frog;
    }
    return null;
  }

  /**
   * Adds a frog to the bag.
   */
  // Adds a frog to the bag
  public void putFrog(Frog frog) {
    if (frogs.size() < 40) {
      frogs.add(frog);
    }
  }
}
