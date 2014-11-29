package core.view.gameplay;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class FireballView extends GameObjectView {

    public FireballView(GameObject gameObject, ResourceManager resourceManager) {
        super(gameObject, resourceManager);
        animation = resourceManager.getAnimation("fireball");
    }

}
