package core.model.gameplay.gameobjects;

public abstract class GameObject {

    private double x;
    private double y;
    private double direction;
    private GameObjInstanceKind type;

    public GameObject(double x, double y, double direction, GameObjInstanceKind type) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
    }

    public abstract void update(int delta);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public GameObjInstanceKind getType() {
        return type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void setDirection(double direction) {
        this.direction = direction;
    }

    protected void changeDirection(double directionDelta) {
        direction += directionDelta;
    }

    public void changeX(double xDelta) {
        x += xDelta;
    }

    public void changeY(double yDelta) {
        y += yDelta;
    }

}
