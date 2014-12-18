package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.SlickException;
public class TreeBigView extends GameObjectSolidView {

    public TreeBigView(GameObject tree) throws SlickException {
        super(tree);
        animation = ResourceManager.getInstance().getAnimation("tree_big");
    }

}