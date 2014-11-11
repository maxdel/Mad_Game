package core;

import core.controller.menu.PauseMenuController;
import core.model.menu.PauseMenu;
import core.view.menu.MenuRenderer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseMenuState extends BasicGameState {

    private static PauseMenuState instance;

    private final int STATE_ID = 2;

    private PauseMenuController pauseMenuController;
    private MenuRenderer menuRenderer;

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
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
        menuRenderer.render(gc);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        pauseMenuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        pauseMenuController = PauseMenuController.getInstance();
        menuRenderer = new MenuRenderer(PauseMenu.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        pauseMenuController = null;
        menuRenderer = null;
        System.gc();
    }
}
