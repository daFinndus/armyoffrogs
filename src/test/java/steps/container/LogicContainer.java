package steps.container;

import de.fhkiel.tsw.Bag;
import de.fhkiel.tsw.Gamelogic;
import de.fhkiel.tsw.Gameround;
import de.fhkiel.tsw.armyoffrogs.Color;

// This class is being used as a container for necessary logic classes w
public class LogicContainer {
    public Gamelogic logic;
    public Gameround round;
    public Bag bag;

    public LogicContainer() {
        logic = new Gamelogic();
        round = new Gameround();

        System.out.println(getClass().getName());
    }
}
