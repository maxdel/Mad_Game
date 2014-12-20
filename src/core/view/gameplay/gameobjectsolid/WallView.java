package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;

public class WallView extends GameObjectSolidView {

    public WallView(GameObject wall) {
        super(wall);
        animation = ResourceManager.getInstance().getAnimation("wall");
    }

}