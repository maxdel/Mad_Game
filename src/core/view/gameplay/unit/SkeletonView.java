package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class SkeletonView extends UnitView {

    public SkeletonView(GameObject skeleton) throws SlickException {
        super(skeleton);
        animation = ResourceManager.getInstance().getAnimation("skeleton");
    }

}