package core.model.gameplay;

public class GameObjectMoving extends GameObject {

    private double relativeDirection;
    private GameObjectState currentState;
    private Attribute attribute;

    public GameObjectMoving(double x, double y, double maximumSpeed) {
        super(x, y, 0);

        this.relativeDirection = 0;
        this.currentState = GameObjectState.STAND;

        attribute = new Attribute(100, 50, maximumSpeed);
    }

    @Override
    public void update(int delta) {
        double length, direction, lengthDirX, lengthDirY;
        length = attribute.getCurrentSpeed() * delta;
        direction = getDirection() + getRelativeDirection();

        lengthDirX = lengthDirX(direction, length);
        lengthDirY = lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            setX(getX() + lengthDirX);
            setY(getY() + lengthDirY);
        } else {
            double stepAngle = 10 * Math.PI / 180;
            double altDirection = direction;
            for (int i = 0; Math.abs(altDirection - direction) < Math.PI / 2; ++i) {
                altDirection += i * stepAngle * (i % 2 == 0 ? 1 : -1);
                lengthDirX = lengthDirX(altDirection, length * Math.cos(altDirection - direction));
                lengthDirY = lengthDirY(altDirection, length * Math.cos(altDirection - direction));
                if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
                    setX(getX() + lengthDirX);
                    setY(getY() + lengthDirY);
                    break;
                }
            }
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public void setRelativeDirection(double relativeDirection) {
        this.relativeDirection = relativeDirection;
    }

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameObjectState currentState) {
        this.currentState = currentState;
    }

}