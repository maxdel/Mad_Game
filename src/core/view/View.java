package core.view;

public class View {

    private double x;
    private double y;

    public View() {
        x = 0;
        y = 0;
    }

    public View(int x, int y) {
        this.x = x;
        this.y = y;
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
}