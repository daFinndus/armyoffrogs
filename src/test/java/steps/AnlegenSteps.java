package steps;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import steps.container.LogicContainer;

public class AnlegenSteps {

    private LogicContainer container;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }

    @Wenn("er die Aktion Anlegen überspringen will")
    public void er_die_aktion_anlegen_überspringen_will() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dann("müssen alle bereits gelegten Spielsteine und die in seinem Vorrat seine Teamfarbe haben")
    public void müssen_alle_bereits_gelegten_spielsteine_und_die_in_seinem_vorrat_seine_teamfarbe_haben() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
