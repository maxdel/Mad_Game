package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;

public class IceWallView extends GameObjectSolidView {

    public IceWallView(GameObject wall) {
        super(wall);
        animation = ResourceManager.getInstance().getAnimation("ice_wall");
    }

}