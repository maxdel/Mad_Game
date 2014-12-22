package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.GameObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;

public class GolemBossView extends UnitView {

    public GolemBossView(GameObject golem) {
        super(golem);
        animation = ResourceManager.getInstance().getAnimation("golem_boss");
    }

    @Override
    public void render(Graphics g, Camera camera) {
        super.render(g, camera);
    }

}
