package core.model.gameplay.skills.bullets;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.units.*;
import org.newdawn.slick.geom.Circle;

public class Fireball extends Bullet {

    public Fireball(Unit owner, double x, double y, double direction, double speed, double pAttack, double mAttack) {
        super(owner, x, y, direction, speed, pAttack, mAttack);
        setMask(ResourceManager.getInstance().getMask(ObstacleType.FIREBALL));
    }

}