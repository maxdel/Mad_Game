package core.controller.gameplay;

import core.model.gameplay.GameObjectMovingManager;
import core.model.gameplay.World;
import org.newdawn.slick.GameContainer;

public abstract class GameObjectMovingController {

    protected GameObjectMovingManager gameObjectMovingManager;

    public abstract void update(GameContainer gc, World world, final int delta);

}