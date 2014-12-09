package core.model.gameplay.gameobjects;

public abstract class GameObject {

    private double x;
    private double y;
    private double direction;

    public GameObject(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void changeDirection(double directionDelta) {
        direction += directionDelta;
    }

}
