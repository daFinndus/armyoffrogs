package steps;

import io.cucumber.java.de.Und;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import io.cucumber.java.de.Angenommen;

import de.fhkiel.tsw.Frog;
import de.fhkiel.tsw.armyoffrogs.Color;

import de.fhkiel.tsw.Bag;
import steps.container.LogicContainer;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AufbauSteps {

    private LogicContainer container;
    private int numberOfPlayers = 0;

    Bag bag;

    public AufbauSteps(LogicContainer container) {
        this.container = container;
        bag = container.logic.bag;
        System.out.println(getClass().getName());
    }

    @Angenommen("das Spiel wird vorbereitet")
    public void das_spiel_wird_vorbereitet() {
        int frogsInBag = bag.getNumberOfFrogs();

        if (frogsInBag == 0) {
            System.out.println("Spiel wird vorbereitet..");
        } else {
            System.out.println("Spiel ist bereits vorbereitet..");
        }
    }

    @Angenommen("es nehmen {int} Spieler teil")
    public void es_nehmen_spieler_teil(Integer anzahl) {
        numberOfPlayers = anzahl;
        container.logic.newGame(anzahl);
        System.out.println("Es spielen " + anzahl + " Spieler..");
    }

    @Wenn("der Beutel befüllt wird")
    public void der_beutel_befüllt_wird() {
        container.logic.startGame(numberOfPlayers);
    }

    @Dann("mit Fröschen der Farbe {word}")
    public void mit_fröschen_der_farbe(String color) {
        List<Frog> frogs = bag.getFrogList();
        Color[] colors = new Color[frogs.size() / 10];

        if (!color.equals("null")) {
            for (int i = 0; i < frogs.size(); i++) {
                Color frogColor = frogs.get(i).getColor();
                if (!Arrays.asList(colors).contains(frogColor)) {
                    // Farbe noch nicht im Array, hinzufügen
                    for (int j = 0; j < colors.length; j++) {
                        if (colors[j] == null) {
                            colors[j] = frogColor;
                            break;
                        }
                    }
                }
            }


            Color colorInstance = null;
            switch (color) {
                case "Rot":
                    colorInstance = Color.Red;
                    break;
                case "Grün":
                    colorInstance = Color.Green;
                    break;
                case "Blau":
                    colorInstance = Color.Blue;
                    break;

                case "Weiss":
                    colorInstance = Color.White;
                    break;
            }

            assertThat(Arrays.asList(colors)).contains(colorInstance);
        }
    }
}