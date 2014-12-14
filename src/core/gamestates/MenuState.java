package core.gamestates;

import core.model.Menu;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.MenuController;
import core.view.MenuView;

/*
* Execute start menu
* */
public class MenuState extends BasicGameState {

    private static MenuState instance;

    private final GameState STATE_ID = GameState.MENU;

    private MenuController menuController;
    private MenuView menuView;

    private MenuState() {
    }

    public static MenuState getInstance() {
        if (instance == null) {
            instance = new MenuState();
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
        menuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().load(STATE_ID);

        menuController = MenuController.getInstance();
        menuView = new MenuView(Menu.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();
        gc.getInput().clearKeyPressedRecord();

        menuController = null;
        menuView = null;
        System.gc();
    }

}