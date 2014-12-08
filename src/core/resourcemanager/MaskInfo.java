package core.resourcemanager;

import core.model.gameplay.units.ObstacleType;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class MaskInfo {

    private Shape mask;
    private int width;
    private int height;
    private int radius;


    public MaskInfo(String shapeName, int width, int height, int radius) {
        this.width = width;
        this.height = height;
        this.radius = radius;

        /* Forming the mask */
        if (shapeName.equals("circle")) {
            mask = new Circle(0, 0, radius);
        } else if (shapeName.equals("rectangle")) {
            mask = new Rectangle(-width / 2, -height / 2, width, height);
        }
    }

    public Shape getMask() {
        return mask;
    }

}