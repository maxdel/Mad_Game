package core.gamestates;

import core.model.Menu;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.GamePlayController;
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
        gc.setMouseGrabbed(true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        if (gc.isPaused()) {
            g.drawString("Pause", gc.getWidth() / 2 - 30, gc.getHeight() / 2 - 30);
            g.drawString("(Press 'P' key to resume)", gc.getWidth() / 2 - 120, gc.getHeight() / 2);
        } else {
            gamePlayView.render(gc, g);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        gamePlayController.update(gc, game);
        world.update(delta);
        gamePlayView.update(delta);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().load(STATE_ID);

        world = World.getInstance();
        gamePlayView = new GamePlayView(gc, world.getGameObjectList(), world.getTiledMap());
        gamePlayController = new GamePlayController(world, gamePlayView);
        render(gc,game, gc.getGraphics());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        gc.pause();
        ResourceManager.getInstance().unload();
        world = null;
        gamePlayView = null;
        gamePlayController = null;
        Menu.getInstance().setCurrentChoice(0);
        System.gc();
    }
}