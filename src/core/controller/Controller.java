package core.controller;

import core.view.HeroRepresentation;
import org.newdawn.slick.GameContainer;

import core.model.World;
import core.view.Renderer;
import org.newdawn.slick.SlickException;

public class Controller {

    World world;
    Renderer renderer;
    HeroController heroController;
    HeroRepresentation heroRepresentation;

    public Controller(World world, Renderer renderer) throws SlickException{
        this.world = world;
        this.renderer = renderer;

        heroRepresentation = renderer.getHeroRepresentation();

        heroController = new HeroController(world.getHeroManager(), heroRepresentation);
    }

    public void update(GameContainer gc, final int delta) {
        heroController.update(gc, delta);
    }

}