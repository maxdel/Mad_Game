package core.controller;

import org.newdawn.slick.GameContainer;

import core.model.World;
import core.view.Renderer;

public class Controller {

    World world;
    Renderer renderer;
    HeroController heroController;

    public Controller(World world, Renderer renderer) {
        this.world = world;
        this.renderer = renderer;

        heroController = new HeroController(world.getHeroManager());
    }

    public void update(GameContainer gc) {
        heroController.update(gc);
    }

}
