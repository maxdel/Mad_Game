package core.view.gameplay;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.units.Obstacle;

public class FireballView extends GameObjectView {

    public FireballView(Obstacle obstacle, ResourceManager resourceManager) {
        super(obstacle, resourceManager);
        animation = resourceManager.getAnimation("fireball");
    }

}
