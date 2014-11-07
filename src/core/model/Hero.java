package core.model;

public class Hero extends GameObject {

    private char lastDirection;

    public Hero(double x, double y, double direction, double speed) {
        super(x, y, direction, speed);
    }

    public char getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(char lastDirection) {
        this.lastDirection = lastDirection;
    }
}