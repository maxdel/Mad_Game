package core.model.gameplay;

import core.model.gameplay.inventory.Inventory;
import core.model.gameplay.inventory.ItemRecord;

import java.util.ArrayList;
import java.util.List;

public class GameObjectMoving extends GameObject {

    private double relativeDirection;
    private GameObjectState currentState;
    protected ItemRecord usingItem;
    protected Inventory inventory;
    private Attribute attribute;

    protected List<Skill> skillList;
    protected Skill currentSkill;

    protected int useItemCounter;
    protected final int useItemTime = 400;

    public GameObjectMoving(double x, double y, double maximumSpeed) {
        super(x, y, 0);

        this.relativeDirection = 0;
        this.currentState = GameObjectState.STAND;
        inventory = new Inventory(this);
        attribute = new Attribute(100, 50, maximumSpeed);
        skillList = new ArrayList<Skill>();
    }

    public void startUseItem() {
        if (inventory.getSelectedRecord() != null && getCurrentState() != GameObjectState.PICK_ITEM &&
                getCurrentState() != GameObjectState.DROP_ITEM &&
                getCurrentState() != GameObjectState.USE_ITEM) {
            setCurrentState(GameObjectState.USE_ITEM);
            getAttribute().setCurrentSpeed(0);
            usingItem = inventory.getSelectedRecord();
            useItemCounter = useItemTime;
        }
    }

    private void useItem(){
        inventory.useItem(usingItem);
        setCurrentState(GameObjectState.STAND);
        usingItem = null;
    }


    public void startCastSkill(int skillIndex) {
        if (skillList.get(skillIndex) != null && skillList.get(skillIndex).startCast()) {
            currentSkill = skillList.get(skillIndex);
            setCurrentState(GameObjectState.CAST);
            getAttribute().setCurrentSpeed(0);
        }
    }

    protected void castSkill() {
        currentSkill.cast();
        setCurrentState(GameObjectState.STAND);
        currentSkill = null;
    }

    @Override
    public void update(int delta) {
        if (useItemCounter > 0) {
            useItemCounter -= delta;
            if (useItemCounter <= 0) {
                useItemCounter = 0;
                useItem();
            }
        }

        if (currentSkill != null && currentSkill.getCurrentCastTime() > 0) {
            currentSkill.setCurrentCastTime(currentSkill.getCurrentCastTime() - delta);
            if (currentSkill.getCurrentCastTime() <= 0) {
                currentSkill.setCurrentCastTime(0);
                castSkill();
            }
        }

        for (Skill skill : skillList) {
            skill.update(delta);
        }

        double length, direction, lengthDirX, lengthDirY;
        length = attribute.getCurrentSpeed() * delta;
        direction = getDirection() + getRelativeDirection();

        lengthDirX = lengthDirX(direction, length);
        lengthDirY = lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            setX(getX() + lengthDirX);
            setY(getY() + lengthDirY);
        } else {
            double stepAngle = 10 * Math.PI / 180;
            double altDirection = direction;
            for (int i = 0; Math.abs(altDirection - direction) < Math.PI / 2; ++i) {
                altDirection += i * stepAngle * (i % 2 == 0 ? 1 : -1);
                lengthDirX = lengthDirX(altDirection, length * Math.cos(altDirection - direction));
                lengthDirY = lengthDirY(altDirection, length * Math.cos(altDirection - direction));
                if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
                    setX(getX() + lengthDirX);
                    setY(getY() + lengthDirY);
                    break;
                }
            }
        }

        if (attribute.getCurrentHP() <= 0) {
            World.getInstance().getToDeleteList().add(this);
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public void setRelativeDirection(double relativeDirection) {
        this.relativeDirection = relativeDirection;
    }

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameObjectState currentState) {
        this.currentState = currentState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

}