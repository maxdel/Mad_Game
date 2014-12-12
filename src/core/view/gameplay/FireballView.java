package core.view.gameplay;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class FireballView extends GameObjectSolidView {

    public FireballView(GameObject fireball) {
        super(fireball);
        animation = ResourceManager.getInstance().getAnimation("fireball");
    }

}
