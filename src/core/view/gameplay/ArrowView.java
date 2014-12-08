package core.view.gameplay;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.units.Obstacle;

public class ArrowView extends GameObjectView {

    public ArrowView(Obstacle obstacle, ResourceManager resourceManager) {
        super(obstacle, resourceManager);
        animation = resourceManager.getAnimation("arrow");
    }

}
