package core.controller.menu;

import core.model.menu.MenuManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.GamePlayState;
import core.model.gameplay.World;
import core.model.menu.MenuStart;

/*
* Provides functions to handle user menu navigate events
* */
public class MenuStartController {

    private static MenuStartController instance;

    private MenuStart menuStart;

    MenuManager menuManager;

    private MenuStartController() {
        menuStart = MenuStart.getInstance();
        menuManager = new MenuManager(menuStart);
    }

    public static MenuStartController getInstance() {
        if (instance == null) {
            instance = new MenuStartController();
        }
        return instance;
    }


    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_S)) {
            menuManager.stepDown();
        }

        if (input.isKeyPressed(Input.KEY_W)) {
            menuManager.stepUp();
        }

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            if (menuStart.getCurrentChoice() == menuStart.getMenuId("Start")) {
                World.getInstance(true); // creating new world
                game.enterState(GamePlayState.getInstance().getID());
            }
            else if (menuStart.getCurrentChoice() == menuStart.getMenuId("Load")) {

            }
            else if (menuStart.getCurrentChoice() == menuStart.getMenuId("Help")) {

            }
            else if (menuStart.getCurrentChoice() == menuStart.getMenuId("Exit")) {
                gc.exit();
            }
        }
    }

}
