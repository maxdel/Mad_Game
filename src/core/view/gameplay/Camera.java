package core.view.gameplay;

public class Camera {

    private double x;
    private double y;
    private double direction;
    private int width;
    private int height;
    private double centerX;
    private double centerY;

    public Camera(double x, double y, double direction, int width, int height) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.centerX = 0;
        this.centerY = 0;
    }

    public Camera(int width, int height) {
        this(0, 0, 0, width, height);
    }

    public void update(int width, int height, double x, double y, double direction) {
        this.width = width;
        this.height = height;

        // Smooth change of direction
        double resultDirection = direction + Math.PI / 2;
        if (resultDirection > this.direction && resultDirection - 0.005 > this.direction) {
            resultDirection = this.direction + (resultDirection - this.direction) / 2;
        }
        if (resultDirection < this.direction && resultDirection + 0.005 < this.direction) {
            resultDirection = this.direction - (this.direction - resultDirection) / 2;
        }

        this.direction = resultDirection;
        centerX = this.width / 2;
        centerY = 2 * this.height / 3;
        setX(x - centerX);
        setY(y - centerY);
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
    public float getDirectionDegrees() {
        return (float) (direction / Math.PI * 180);
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

}