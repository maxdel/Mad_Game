package core;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.menu.MenuPauseController;
import core.model.menu.MenuPause;
import core.view.menu.MenuView;

public class MenuPauseState extends BasicGameState {

    private static MenuPauseState instance;

    private final GameState STATE_ID = GameState.MENUPAUSE;

    private MenuPauseController menuPauseController;
    private MenuView menuView;

    private MenuPauseState() {
    }

    public static MenuPauseState getInstance() {
        if (instance == null) {
            instance = new MenuPauseState();
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
        menuPauseController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().load(STATE_ID);

        gc.getInput().clearKeyPressedRecord();
        menuPauseController = MenuPauseController.getInstance();
        menuView = new MenuView(MenuPause.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();

        menuPauseController = null;
        menuView = null;
        System.gc();
    }

}