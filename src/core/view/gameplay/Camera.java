package core.view.gameplay;

public class Camera {

    private double x;
    private double y;
    private double direction;
    private int width;
    private int height;

    public Camera(double x, double y, double direction, int width, int height) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public Camera(int width, int height) {
        this(0, 0, 0, width, height);
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

    /*
    *  Returns camera direction angle in degrees.
    * */
    public float getDirectionAngle() {
        return (float) (direction / Math.PI * 180);
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void update(final int width, final int height, final double x, final double y, final double direction) {
        setWidth(width);
        setHeight(height);

        // smooth change of direction
        double resultDirection = direction + Math.PI / 2;
        if (resultDirection > getDirection() && resultDirection - 0.005 > getDirection()) {
            resultDirection = getDirection() + (resultDirection - getDirection()) / 2;
        }
        if (resultDirection < getDirection() && resultDirection + 0.005 < getDirection()) {
            resultDirection = getDirection() - (getDirection() - resultDirection) / 2;
        }

        setDirection(resultDirection);
        setX(x - getWidth() / 2);
        setY(y - getHeight() / 2);
    }

}