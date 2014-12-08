package core.model.gameplay.gameobjects;

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
                    double mAttack, GameObjectSolidType type) {
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

        lengthDirX = lengthDirX(direction, length);
        lengthDirY = lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            setX(getX() + lengthDirX);
            setY(getY() + lengthDirY);
            currentDistance += length;
        } else {
            GameObjectSolid other = CollisionManager.getInstance().collidesWith(this, getX() + lengthDirX, getY() + lengthDirY);
            if (other == owner) {
                setX(getX() + lengthDirX);
                setY(getY() + lengthDirY);
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
                    // TODO for fun
                    if (owner.getClass() == Hero.class) {
                        if (otherMoving instanceof Bandit) {
                            ((Bandit)otherMoving).setTargetHero(true);
                        }
                        if (otherMoving instanceof BanditArcher) {
                            ((BanditArcher)otherMoving).setTargetHero(true);
                        }
                        if (otherMoving instanceof Skeleton) {
                            ((Skeleton)otherMoving).setTargetHero(true);
                        }
                        if (otherMoving instanceof SkeletonMage) {
                            ((SkeletonMage)otherMoving).setTargetHero(true);
                        }
                    }
                }
                World.getInstance().getToDeleteList().add(this);
            }
        }

        if (currentDistance >= maximumDistance) {
            World.getInstance().getToDeleteList().add(this);
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

}
