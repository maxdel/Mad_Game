package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.view.ResourceManager;
import core.controller.gameplay.GamePlayController;
import core.model.gameplay.World;
import core.view.gameplay.GamePlayView;

/*
* Execute game play
* */
public class GamePlayState extends BasicGameState {

    private static GamePlayState instance;

    private final GameState STATE_ID = GameState.GAMEPLAY;

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
        return STATE_ID.getValue();
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
        gamePlayController.update(gc, game);

        /* Flow of inner game world process*/
        World.getInstance().update(delta);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.load(STATE_ID);
        world = World.getInstance(false);
        gamePlayView = new GamePlayView(gc, world.getGameObjects(), world.getHero(), resourceManager);
        gamePlayController = new GamePlayController(world, gamePlayView);
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();
        World.deleteInstance(); // no sense - in field world we still have object reference
        gamePlayView = null;
        gamePlayController = null;
        System.gc();
    }

}