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

    public static final int STATE_ID = 1;

    World world;
    GamePlayRenderer gamePlayRenderer;
    GamePlayController gamePlayController;

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
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        gamePlayRenderer.render(gc, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        gamePlayController.update(gc, stateBasedGame, delta);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

        world = World.getInstance();
        gamePlayRenderer = GamePlayRenderer.getInstance(world.getGameObjects(), world.getHero());
        gamePlayController = GamePlayController.getInstance(world, gamePlayRenderer);

        container.setVSync(true);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        world = null;
        gamePlayRenderer = null;
        gamePlayController = null;

        container.setVSync(false); // to check, that it method works

        System.gc();
    }
}