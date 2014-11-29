package core.view.gameplay;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class BulletView extends GameObjectView {

    public BulletView(GameObject gameObject, ResourceManager resourceManager) {
        super(gameObject, resourceManager);
        animation = resourceManager.getAnimation("bullet");
    }

}
