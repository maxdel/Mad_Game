package core.view.gameplay;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class ArrowView extends GameObjectSolidView {

    public ArrowView(GameObject arrow) {
        super(arrow);
        animation = ResourceManager.getInstance().getAnimation("arrow");
    }

}
