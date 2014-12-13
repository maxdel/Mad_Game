package core.resourcemanager;

import org.newdawn.slick.geom.Shape;

public class BulletInfo {

    private String mask;

    public BulletInfo(String mask) {
        this.mask = mask;
    }

    public Shape getMask() {
        return ResourceManager.getInstance().getMask(mask);
    }

}