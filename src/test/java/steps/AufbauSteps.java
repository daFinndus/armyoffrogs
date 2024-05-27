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

    // Potentiell das hier umschreiben in was cooleres
    @Angenommen("Das Spiel ist initialisiert")
    public void das_spiel_ist_initialisiert() {
        System.out.println("Das Spiel ist initialisiert..");
    }

    @Angenommen("es nehmen {int} Spieler teil")
    public void es_nehmen_spieler_teil(Integer anzahl) {
        numberOfPlayers = anzahl;
        container.logic.newGame(anzahl);
        System.out.println("Es spielen " + anzahl + " Spieler..");
    }

    @Angenommen("die Spielerfarben wurden vergeben")
    public void die_spielerfarben_wurden_vergeben() {
        container.logic.newGame(numberOfPlayers);
        System.out.println("Die Spielerfarben wurden vergeben..");
    }

    @Wenn("der Beutel befüllt wird")
    public void der_beutel_befüllt_wird() {
        if (bag.getNumberOfFrogs() == numberOfPlayers * 10) {
            System.out.println("Der Beutel ist bereits befüllt..");
        } else {
            container.logic.newGame(numberOfPlayers);
            System.out.println("Der Beutel wird befüllt..");
        }
    }

    @Wenn("die Spieler ihre ersten zwei Steine erhalten")
    public void die_spieler_ihre_ersten_zwei_steine_erhalten() {
        container.logic.startGame(numberOfPlayers);
    }

    @Wenn("die Spielerauswahl getroffen wurde")
    public void die_spielerauswahl_getroffen_wurde() {
        assertThat(numberOfPlayers).isGreaterThanOrEqualTo(2);
        assertThat(numberOfPlayers).isLessThanOrEqualTo(4);
        System.out.println("Die Spielerauswahl wurde getroffen..");
    }

    @Wenn("nicht zwei unterschreitet")
    public void nicht_zwei_unterschreitet() {
        assertThat(numberOfPlayers).isGreaterThanOrEqualTo(2);
    }

    @Wenn("nicht vier überschreitet")
    public void nicht_vier_überschreitet() {
        assertThat(numberOfPlayers).isLessThanOrEqualTo(4);
    }

    @Dann("mit Fröschen der Farbe {word}")
    public void mit_fröschen_der_farbe(String color) {
        List<Frog> frogs = bag.getFrogList();
        Color[] colors = new Color[container.logic.players().length];

        System.out.println("Frog size: " + frogs.size());
        System.out.println("Color size: " + colors.length);

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

            System.out.println("Farben: " + Arrays.toString(colors) + " (Erwartet: " + color + ")");


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


    @Dann("muss jeder Spieler zwei Steine aus dem Beutel in ihren Vorrat bekommen")
    public void muss_jeder_spieler_zwei_steine_aus_dem_beutel_in_ihren_vorrat_bekommen() {
        for (int i = 0; i < numberOfPlayers; i++) {
            int frogsInHand = container.logic.getFrogsOfPlayer(container.logic.players()[i]).length;

            assertThat(frogsInHand).isEqualTo(2);
        }
    }


    @Dann("wird das Spiel mit {int} Spielern gestartet")
    public void wird_das_spiel_mit_spielern_gestartet(Integer anzahl) {
        assertThat(numberOfPlayers).isEqualTo(anzahl);
        System.out.println("Das Spiel wird mit " + anzahl + " Spielern gestartet..");
    }

    @Dann("der Beutel enthält jetzt {int} Steine weniger")
    public void der_beutel_enthaelt_jetzt_steine_weniger(Integer anzahl) {
        int frogsInBag = bag.getNumberOfFrogs();
        assertThat(frogsInBag).isEqualTo((numberOfPlayers * 10) - anzahl);
    }

    @Dann("sollten {int} Spielsteine im Beutel liegen")
    public void sollten_spielsteine_im_beutel_liegen(Integer anzahl) {
        int frogsInBag = bag.getNumberOfFrogs();
        assertThat(frogsInBag).isEqualTo(anzahl);
    }
}
