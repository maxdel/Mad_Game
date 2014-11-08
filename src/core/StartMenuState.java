package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.startmenu.StartMenuController;
import core.view.startmenu.StartMenuRenderer;

/*
* Execute start menu
* */
public class StartMenuState extends BasicGameState {

    private static StartMenuState instance;

    private final int STATE_ID = 0;

    private StartMenuController startMenuController;
    private StartMenuRenderer startMenuRenderer;

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
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
        startMenuRenderer.render();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        startMenuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        startMenuController = StartMenuController.getInstance();
        startMenuRenderer = StartMenuRenderer.getInstance();
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        startMenuController = null;
        startMenuRenderer = null;
        System.gc();
    }
}

