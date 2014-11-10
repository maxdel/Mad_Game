package core.view.gameplay;

public class View {

    private double x;
    private double y;
    private double direction;
    private int width;
    private int height;

    public View() {
        x = 0;
        y = 0;
        direction = 0;
        width = 0;
        height = 0;
    }

    public View(int width, int height) {
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

}