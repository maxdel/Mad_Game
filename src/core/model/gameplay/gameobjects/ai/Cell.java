package core.model.gameplay.gameobjects.ai;

public class Cell {
    public double f;
    public double g;
    public double h;
    public int x;
    public int y;
    public Cell cameFrom;

    public Cell(double f, double g, double h, double x, double y) {
        this(f, g, h, x, y, null);
    }

    public Cell(double f, double g, double h, double x, double y, Cell cameFrom) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.x = (int) x;
        this.y = (int) y;
        this.cameFrom = cameFrom;
    }
}
