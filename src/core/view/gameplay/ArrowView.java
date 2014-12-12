package core.view.gameplay;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.GameObjectSolid;

public class ArrowView extends GameObjectView {

    public ArrowView(GameObjectSolid gameObjectSolid, ResourceManager resourceManager) {
        super(gameObjectSolid, resourceManager);
        animation = resourceManager.getAnimation("arrow");
    }

}
