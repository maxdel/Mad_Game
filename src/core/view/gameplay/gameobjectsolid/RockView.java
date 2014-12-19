package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class RockView extends GameObjectSolidView {

    public RockView(GameObject rock) {
        super(rock);
        animation = ResourceManager.getInstance().getAnimation("rock");
    }

}