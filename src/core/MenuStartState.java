package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.model.menu.MenuStart;
import core.view.ResourceManager;
import core.controller.menu.MenuStartController;
import core.view.menu.MenuView;

/*
* Execute start menu
* */
public class MenuStartState extends BasicGameState {

    private static MenuStartState instance;

    private final GameState STATE_ID = GameState.MENUSTART;

    private MenuStartController menuStartController;
    private MenuView menuView;

    private MenuStartState() {
    }

    public static MenuStartState getInstance() {
        if (instance == null) {
            instance = new MenuStartState();
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
    public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
        menuView.render(gc);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        menuStartController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.load(STATE_ID);
        gc.getInput().clearKeyPressedRecord();
        menuStartController = MenuStartController.getInstance();
        menuView = new MenuView(MenuStart.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();
        menuStartController = null;
        menuView = null;
        System.gc();
    }
}