package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class VampireView extends UnitView {

    public VampireView(GameObject vampire) {
        super(vampire);
        animation = ResourceManager.getInstance().getAnimation("vampire");
    }

}