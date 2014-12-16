package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.resourcemanager.ResourceManager;

public class ThornView extends GameObjectSolidView {

    public ThornView(GameObject thorn) {
        super(thorn);
        animation = ResourceManager.getInstance().getAnimation("thorn");
    }

}
