package core;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.controller.Controller;
import core.model.World;
import core.view.Renderer;

public class Core extends BasicGame {

    private static Core instance;

    World world;
    Renderer renderer;
    Controller controller;

    private Core(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setVSync(true);

        world = World.getInstance();
        renderer = Renderer.getInstance(world.getGameObjects(), world.getHero());
        controller = Controller.getInstance(world, renderer);
    }

    public static Core getInstance(String getName) {
        if (instance == null) {
            instance = new Core(getName);
        }
        return instance;
    }

    @Override
    public void update(GameContainer gc, final int delta) throws SlickException {
        controller.update(gc, delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        renderer.render(gc, g);
    }

}