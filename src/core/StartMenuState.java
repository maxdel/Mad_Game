package core;

import core.model.MenuStart;
import core.view.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.StartMenuController;
import core.view.MenuView;

/*
* Execute start menu
* */
public class StartMenuState extends BasicGameState {

    private static StartMenuState instance;

    private final GameState STATE_ID = GameState.STARTMENU;

    private StartMenuController startMenuController;
    private MenuView menuView;

    private StartMenuState() {
    }

    public static StartMenuState getInstance() {
        if (instance == null) {
            instance = new StartMenuState();
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
        startMenuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.load(STATE_ID);
        gc.getInput().clearKeyPressedRecord();
        startMenuController = StartMenuController.getInstance();
        menuView = new MenuView(MenuStart.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();
        startMenuController = null;
        menuView = null;
        System.gc();
    }
}