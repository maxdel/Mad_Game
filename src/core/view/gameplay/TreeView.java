package core.view.gameplay;

import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class TreeView extends GameObjectSolidView {

    public TreeView(GameObject tree) throws SlickException {
        super(tree);
        animation = ResourceManager.getInstance().getAnimation("tree");
    }

}