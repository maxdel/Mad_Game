package core.view.gameplay;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class ArrowView extends GameObjectView {

    public ArrowView(GameObject gameObject, ResourceManager resourceManager) {
        super(gameObject, resourceManager);
        animation = resourceManager.getAnimation("arrow");
    }

}
