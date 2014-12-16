package core.view.gameplay.unit;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.view.gameplay.Camera;
import core.model.gameplay.gameobjects.*;
import core.resourcemanager.ResourceManager;

public class BanditBossView extends UnitView {

    public BanditBossView(GameObject banditBoss) throws SlickException {
        super(banditBoss);
        animation = ResourceManager.getInstance().getAnimation("bandit_boss");
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        super.render(g, camera);
    }

}