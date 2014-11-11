package core.view.gameplay;

public class Camera {

    private double x;
    private double y;
    private double direction;
    private int width;
    private int height;

    public Camera() {
        x = 0;
        y = 0;
        direction = 0;
        width = 0;
        height = 0;
    }

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void update(final double x, final double y, final double direction) {
        setDirection(direction + Math.PI / 2);
        setX(x - getWidth() / 2);
        setY(y - getHeight() / 2);
    }

}