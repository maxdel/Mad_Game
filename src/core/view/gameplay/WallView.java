package core.view.gameplay;

import core.model.gameplay.gameobjects.GameObject;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.Obstacle;
import core.resourcemanager.ResourceManager;

public class WallView extends GameObjectSolidView {

    public WallView(GameObject wall) throws SlickException {
        super(wall);
        animation = ResourceManager.getInstance().getAnimation("wall");
    }

}