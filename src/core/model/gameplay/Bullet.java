package core.model.gameplay;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Bullet extends GameObject {

    private GameObjectMoving owner;
    private double speed;
    private double pAttack;
    private double mAttack;

    public Bullet(GameObjectMoving owner, double x, double y, double direction, double speed, double pAttack,
                  double mAttack) {
        this(x, y, direction);
        this.owner = owner;
        this.speed = speed;
        int width = 20;
        int height = 3;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        setMask(new Rectangle(- width / 2, - height / 2, width, height));
    }

    private Bullet(double x, double y, double direction) {
        super(x, y, direction);
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
        } else {
            GameObject other = CollisionManager.getInstance().collidesWith(this, getX() + lengthDirX, getY() + lengthDirY);
            if (other == owner) {
                setX(getX() + lengthDirX);
                setY(getY() + lengthDirY);
            } else {
                if (other instanceof GameObjectMoving) {
                    GameObjectMoving otherMoving = (GameObjectMoving) other;
                    double damage = pAttack + owner.getAttribute().getPAttack() - otherMoving.getAttribute().getPArmor();
                    if (damage <= 1) {
                        damage = 1;
                    }
                    otherMoving.getAttribute().setCurrentHP(otherMoving.getAttribute().getCurrentHP() - damage);
                }
                World.getInstance().getToDeleteList().add(this);
            }
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

}