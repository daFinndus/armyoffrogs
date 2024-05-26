package steps;

import static org.assertj.core.api.Assertions.assertThat;

import steps.container.LogicContainer;
import de.fhkiel.tsw.Gamelogic;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

public class BagSteps {

    private LogicContainer container;

    public BagSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Angenommen("es läuft kein Spiel")
    public void es_läuft_kein_spiel() {
        container.logic = new Gamelogic();
    }

    int frösche;

    @Angenommen("der (erste)(zweite)(dritter)(vierter) Spieler hat {int} (Frösche)(Frosch) gezogen")
    public void der_erste_spieler_hat_frösche_gezogen(Integer inSackGegriffen) {
        for (int i = 0; i < inSackGegriffen; ++i) {
            container.logic.takeFrogFromBag();
        }
    }

    @Wenn("der Beutelinhalt abgefragt wird")
    public void der_beutelinhalt_abgefragt_wird() {
        frösche = container.logic.frogsInBag();
    }

    @Dann("sind {int} Frösche im Beutel")
    public void sind_frösche_im_beutel(Integer erwarteteFrösche) {
        assertThat(frösche).isEqualTo(erwarteteFrösche);
        System.out.println("Es sind " + frösche + " Frösche im Beutel bei " + erwarteteFrösche + " erwarteten Fröschen.");
    }

}
