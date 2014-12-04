package core.view.gameplay;

import core.model.gameplay.resource_manager.ResourceManager;
import core.model.gameplay.units.GameObjectSolid;

public class FireballView extends GameObjectView {

    public FireballView(GameObjectSolid gameObjectSolid, ResourceManager resourceManager) {
        super(gameObjectSolid, resourceManager);
        animation = resourceManager.getAnimation("fireball");
    }

}
