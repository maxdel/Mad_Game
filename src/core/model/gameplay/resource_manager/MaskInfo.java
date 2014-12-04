package core.model.gameplay.resource_manager;

public class MaskInfo {

    private int width;
    private int height;
    private int radius;

    public MaskInfo(int width, int height, int radius) {
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }

}
