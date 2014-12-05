package core.view.gameplay;

import core.resource_manager.ResourceManager;
import core.model.gameplay.units.GameObjectSolid;

public class ArrowView extends GameObjectView {

    public ArrowView(GameObjectSolid gameObjectSolid, ResourceManager resourceManager) {
        super(gameObjectSolid, resourceManager);
        animation = resourceManager.getAnimation("arrow");
    }

}
