package core.model.gameplay.gameobjects;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;

public class Bullet extends GameObjectSolid {

    private Unit owner;
    private double speed;
    private double pAttack;
    private double mAttack;
    private final int maximumDistance = 1000;
    private double currentDistance;

    public Bullet(Unit owner, double x, double y, double direction, double speed, double pAttack,
                    double mAttack, GameObjectType type) {
        super(x, y, direction, type);
        this.owner = owner;
        this.speed = speed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        currentDistance = 0;

    }

    @Override
    public void update(int delta) {
        double length, direction, lengthDirX, lengthDirY;
        length = speed * delta;
        direction = getDirection();

        lengthDirX = MathAdv.lengthDirX(direction, length);
        lengthDirY = MathAdv.lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            changeX(lengthDirX);
            changeY(lengthDirY);
            currentDistance += length;
        } else {
            GameObjectSolid other = CollisionManager.getInstance().collidesWith(this, getX() + lengthDirX, getY() + lengthDirY);
            if (other == owner) {
                changeX(lengthDirX);
                changeY(lengthDirY);
                currentDistance += length;
            } else {
                if (other instanceof Unit) {
                    Unit otherMoving = (Unit) other;
                    if (pAttack > 0) {
                        double pDamage = pAttack + owner.getAttribute().getPAttack() - otherMoving.getAttribute().getPArmor();
                        if (pDamage <= 1) {
                            pDamage = 1;
                        }
                        otherMoving.getAttribute().getHP().damage(pDamage);
                    }
                    if (mAttack > 0) {
                        double mDamage = mAttack + owner.getAttribute().getMAttack() - otherMoving.getAttribute().getMArmor();
                        if (mDamage <= 1) {
                            mDamage = 1;
                        }
                        otherMoving.getAttribute().getHP().damage(mDamage);
                    }
                }
                World.getInstance().getGameObjectToDeleteList().add(this);
            }
        }

        if (currentDistance >= maximumDistance) {
            World.getInstance().getGameObjectToDeleteList().add(this);
        }
    }

}
