package core.controller.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.GamePlayState;
import core.model.gameplay.World;
import core.model.menu.StartMenu;
import core.model.menu.MenuManager;

/*
* Provides functions to handle user menu navigate events
* */
public class StartMenuController {

    private static StartMenuController instance;

    private StartMenu startMenu;

    MenuManager menuManager;

    private StartMenuController() {
        startMenu = StartMenu.getInstance();
        menuManager = new MenuManager(startMenu);
    }

    public static StartMenuController getInstance() {
        if (instance == null) {
            instance = new StartMenuController();
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
            if (startMenu.getCurrentChoice() == startMenu.getMenuId("Start")) {
                World.getInstance(true); // creating new world
                game.enterState(GamePlayState.getInstance().getID());
            }
            else if (startMenu.getCurrentChoice() == startMenu.getMenuId("Load")) {

            }
            else if (startMenu.getCurrentChoice() == startMenu.getMenuId("Help")) {

            }
            else if (startMenu.getCurrentChoice() == startMenu.getMenuId("Exit")) {
                gc.exit();
            }
        }
    }

}
