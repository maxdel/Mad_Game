package core.model.gameplay;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains fields to define the hero state
 * */
public class Hero extends MovingGameObject {

    private ArrayList<String> states;
    private String currentState;

    public Hero(double x, double y, double direction, double maximumSpeed, double currentSpeed, double relativeDirection) {
        super(x, y, direction, maximumSpeed);
        this.currentSpeed = currentSpeed;
        this.relativeDirection = relativeDirection;

        states = new ArrayList<String>(Arrays.asList("Stand", "Walk", "Run"));
        currentState = "";
    }

    public Hero(final double x, final double y, final double direction, final double maximumSpeed) {
        this(x, y, direction, maximumSpeed, 0, 0);
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        if (states.contains(currentState)) {
            this.currentState = currentState;
        }
    }

}