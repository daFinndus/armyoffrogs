package steps;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhkiel.tsw.Gamelogic;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

public class BagSteps {

    Gamelogic logic;
    int frösche;

    @Angenommen("es läuft kein Spiel")
    public void es_läuft_kein_spiel() {
        logic = new Gamelogic();
    }

    @Angenommen("das Spiel ist mit {int} Spielern gestartet")
    public void das_spiel_ist_mit_spielern_gestartet(Integer spieler) {
        logic = new Gamelogic();
        logic.startGame(spieler);
    }

    @Angenommen("der (erste)(zweite)(dritter)(vierter) Spieler hat {int} (Frösche)(Frosch) gezogen")
    public void der_erste_spieler_hat_frösche_gezogen(Integer inSackGegriffen) {
        for (int i = 0; i < inSackGegriffen; ++i) {
            logic.takeFrogFromBag();
        }
    }

    @Wenn("der Beutelinhalt abgefragt wird")
    public void der_beutelinhalt_abgefragt_wird() {
        frösche = logic.frogsInBag();
    }

    @Dann("sind {int} Frösche im Beutel")
    public void sind_frösche_im_beutel(Integer erwarteteFrösche) {
        assertThat(frösche).isEqualTo(erwarteteFrösche);
    }

}
