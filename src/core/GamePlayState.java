package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.gameplay.GamePlayController;
import core.model.gameplay.World;
import core.view.gameplay.GamePlayView;

/*
* Execute game play
* */
public class GamePlayState extends BasicGameState {

    private static GamePlayState instance;

    private final int STATE_ID = 0;

    private World world;
    private GamePlayView gamePlayView;
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
        gamePlayView.render(gc, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        gamePlayController.update(gc, game, delta);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        world = World.getInstance(false);
        gamePlayView = new GamePlayView(gc, world.getGameObjects(), world.getHero());
        gamePlayController = new GamePlayController(world, gamePlayView);
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        world = null;
        gamePlayView = null;
        gamePlayController = null;
        System.gc();
    }

}