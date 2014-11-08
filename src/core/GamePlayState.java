package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.gameplay.GamePlayController;
import core.model.gameplay.World;
import core.view.gameplay.GamePlayRenderer;

/*
* Execute game play
* */
public class GamePlayState extends BasicGameState {

    private static GamePlayState instance;

    private final int STATE_ID = 1;

    private World world;
    private GamePlayRenderer gamePlayRenderer;
    private GamePlayController gamePlayController;

    private GamePlayState() {

    }

    public static GamePlayState getInstance() {
        if (instance == null) {
            instance = new GamePlayState();
        }
        return instance;
    }

    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        gamePlayRenderer.render(gc, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        gamePlayController.update(gc, game, delta);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.setVSync(true);

        world = World.getInstance();
        gamePlayRenderer = GamePlayRenderer.getInstance(world.getGameObjects(), world.getHero());
        gamePlayController = GamePlayController.getInstance(world, gamePlayRenderer);
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        world = null;
        gamePlayRenderer = null;
        gamePlayController = null;
        System.gc();
    }
}