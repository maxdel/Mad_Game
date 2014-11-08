package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import core.model.World;
import core.view.Renderer;

/**
 * Main controller class, that uses game object's controllers to process external events (like user input).
 */
public class Controller {

    private static Controller instance;
    private World world;
    private Renderer renderer;
    private HeroController heroController;

    private Controller(final World world, final Renderer renderer) throws SlickException {
        this.world = world;
        this.renderer = renderer;

        heroController = new HeroController(world.getHeroManager());
    }

    // Singleton pattern method
    public static Controller getInstance(final World world, final Renderer renderer) throws SlickException {
        if (instance == null) {
            instance = new Controller(world, renderer);
        }
        return instance;
    }


    public void update(GameContainer gc, final int delta) {
        heroController.update(gc, delta);
    }

}