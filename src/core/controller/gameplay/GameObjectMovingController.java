package core.controller.gameplay;

import core.model.gameplay.GameObject;
import core.model.gameplay.GameObjectMovingManager;
import org.newdawn.slick.GameContainer;

public abstract class GameObjectMovingController {

    protected GameObjectMovingManager gameObjectMovingManager;

    public abstract void update(GameContainer gc, final int delta, GameObject gameObj);

}