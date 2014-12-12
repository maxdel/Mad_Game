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

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected void setDirection(double direction) {
        this.direction = direction;
    }

    protected void changeDirection(double directionDelta) {
        direction += directionDelta;
    }

    protected void changeX(double xDelta) {
        x += xDelta;
    }

    protected void changeY(double yDelta) {
        y += yDelta;
    }

}
