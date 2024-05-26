package steps;

import steps.container.LogicContainer;

public class AnlegenSteps {

    private LogicContainer container;

    public AnlegenSteps(LogicContainer container) {
        this.container = container;
        System.out.println(getClass().getName());
    }


}
