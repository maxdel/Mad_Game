package core.model.gameplay.skills;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.items.ItemInstanceKind;

public class BarrierSkill extends Skill {

    private double hp;
    private int lifeTime;

    public BarrierSkill(String name, String description, int castTime, int postApplyTime, int cooldownTime,
                        ItemInstanceKind requiredItemKind, double requiredHP, double requiredMP, double hp, int lifeTime,
                        SkillInstanceKind kind) {
        super(name, description, castTime, postApplyTime, cooldownTime, requiredItemKind, requiredHP, requiredMP, kind);
        this.hp = hp;
        this.lifeTime = lifeTime;
    }

    @Override
    protected void apply(Unit owner) {
        Obstacle barrier = new Obstacle(owner.getX(), owner.getY(), owner.getDirection(), GameObjInstanceKind.ICEWALL, hp, lifeTime);
        double distance = owner.getMask().getBoundingCircleRadius() + barrier.getMask().getWidth() / 2;
        double lengthDirX = MathAdv.lengthDirX(owner.getDirection(), distance);
        double lengthDirY = MathAdv.lengthDirY(owner.getDirection(), distance);
        barrier.changeX(lengthDirX);
        barrier.changeY(lengthDirY);
        GameObjectSolid collisionObject = CollisionManager.getInstance().collidesWith(barrier, barrier.getX(), barrier.getY());
        // Change condition to allow barrier block bots
        if (collisionObject == null || collisionObject instanceof Bullet &&
                !CollisionManager.getInstance().isCollidesWith(barrier, Bot.class, barrier.getX(), barrier.getY())) {
            World.getInstance().getGameObjectToAddList().add(barrier);
        }
    }

}