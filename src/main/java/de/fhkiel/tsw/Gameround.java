package de.fhkiel.tsw;

import de.fhkiel.tsw.armyoffrogs.Color;


public class Gameround {
    private int round = 0;
    private Color currentPlayer;

    // Funktion für das Beenden eines Zuges
    public void endTurn(Color spieler, Color[] players) {
        int numberOfPlayers = players.length;


        // Herausfinden des Index' des aktuellen Spielers
        int currentIndex = -1;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players[i].equals(spieler)) {
                currentIndex = i;
                break;
            }
        }

        // Berechnen des Index' des nächsten Spielers
        int nextIndex = (currentIndex + 1) % numberOfPlayers;

        // Festsetzen des nächsten Spielers
        currentPlayer = players[nextIndex];

        // Rundenzähler hochzählen
        round = round + 1;
    }

    // Getter for currentPlayer, if needed
    public Color getCurrentPlayer() {
        return currentPlayer;
    }




    // Getter for round, if needed
    public int getRound() {
        return round;
    }
}

