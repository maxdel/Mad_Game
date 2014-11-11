package core;

import core.controller.menu.PauseMenuController;
import core.model.menu.PauseMenu;
import core.view.ResourceManager;
import core.view.menu.MenuView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseMenuState extends BasicGameState {

    private static PauseMenuState instance;

    private final GameState STATE_ID = GameState.PAUSEMENU;

    private PauseMenuController pauseMenuController;
    private MenuView menuView;

    private PauseMenuState() {
    }

    public static PauseMenuState getInstance() {
        if (instance == null) {
            instance = new PauseMenuState();
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
        pauseMenuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.load(STATE_ID);
        gc.getInput().clearKeyPressedRecord();
        pauseMenuController = PauseMenuController.getInstance();
        menuView = new MenuView(PauseMenu.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();
        pauseMenuController = null;
        menuView = null;
        System.gc();
    }
}