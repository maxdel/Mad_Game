package core.view.gameplay;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.GameObjectSolid;

public class FireballView extends GameObjectView {

    public FireballView(GameObjectSolid gameObjectSolid, ResourceManager resourceManager) {
        super(gameObjectSolid, resourceManager);
        animation = resourceManager.getAnimation("fireball");
    }

}
