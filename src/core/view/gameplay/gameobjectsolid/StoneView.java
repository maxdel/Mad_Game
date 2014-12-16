package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class StoneView extends GameObjectSolidView {

    public StoneView(GameObject stone) {
        super(stone);
        animation = ResourceManager.getInstance().getAnimation("stone");
    }

}
