package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.World;
import core.view.Renderer;

public class Controller {

    World world;
    Renderer renderer;

    public Controller(World world, Renderer renderer) {
        this.world = world;
        this.renderer = renderer;
    }

    public void update(GameContainer gc) {
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            world.getHeroController().move(0);
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            world.getHeroController().move(1);
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            world.getHeroController().move(2);
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            world.getHeroController().move(3);
        }
    }

}
