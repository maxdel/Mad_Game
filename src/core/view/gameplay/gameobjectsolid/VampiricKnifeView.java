package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class VampiricKnifeView extends GameObjectSolidView {

    public VampiricKnifeView(GameObject thorn) {
        super(thorn);
        animation = ResourceManager.getInstance().getAnimation("vampiric_knife");
    }

}
