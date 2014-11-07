package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import core.model.World;
import core.view.Renderer;

public class Controller {

    World world;
    Renderer renderer;
    HeroController heroController;

    public Controller(World world, Renderer renderer) throws SlickException{
        this.world = world;
        this.renderer = renderer;

        heroController = new HeroController(world.getHeroManager());
    }

    public void update(GameContainer gc, final int delta) {
        heroController.update(gc, delta);

        renderer.setMouseX(gc.getInput().getMouseX());
        renderer.setMouseY(gc.getInput().getMouseY());
    }

}