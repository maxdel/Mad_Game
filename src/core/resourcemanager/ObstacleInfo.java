package core.resourcemanager;

import org.newdawn.slick.geom.Shape;

public class ObstacleInfo {

    private String mask;

    public ObstacleInfo(String mask) {
        this.mask = mask;
    }

    public Shape getMask() {
        return ResourceManager.getInstance().getMask(mask);
    }

}