package core.view.gameplay.unit;

import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class BanditArcherView extends UnitView {

    public BanditArcherView(GameObject banditArcher) throws SlickException {
        super(banditArcher);
        animation = ResourceManager.getInstance().getAnimation("bandit_archer");
    }

}