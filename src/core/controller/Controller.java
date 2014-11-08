package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import core.model.World;
import core.view.Renderer;

/**
 * Main controller class, that uses game object's controllers to process external events (like user input).
 */
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
    }

}