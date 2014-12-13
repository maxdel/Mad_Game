package core.model.gameplay.items;


import core.model.gameplay.gameobjects.Unit;

interface IBonusGiver {

    /**
     * Sets some bonuses of item to target
     * @param target is an unit, which attributes will change
     */
    abstract public void setBonuses(Unit target);

    /**
     * Unsets some bonuses of item to target
     * @param  target is an unit, which attributes will change
     */
    abstract public void unsetBonuses(Unit target);

}
