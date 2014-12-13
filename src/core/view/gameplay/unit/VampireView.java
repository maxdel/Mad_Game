package core.view.gameplay.unit;

import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class VampireView extends UnitView {

    public VampireView(GameObject vampire) throws SlickException {
        super(vampire);
        animation = ResourceManager.getInstance().getAnimation("vampire");
    }

}