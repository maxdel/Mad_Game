package core.controller.startmenu;

import core.GamePlayState;
import core.model.startmenu.StartMenu;
import core.model.startmenu.StartMenuManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/*
* Provides functions to handle user menu navigate events
* */
public class StartMenuController {

    private static StartMenuController instance;

    private StartMenu startMenu;

    private StartMenuController() {
        startMenu = StartMenu.getInstance();
    }

    public static StartMenuController getInstance() {
        if (instance == null) {
            instance = new StartMenuController();
        }
        return instance;
    }

    StartMenuManager startMenuManager = new StartMenuManager();

    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_S)) {
            startMenuManager.stepDown();
        }

        if (input.isKeyPressed(Input.KEY_W)) {
            startMenuManager.stepUp();
        }

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            if (startMenu.getCurrentChoice() == startMenu.getMenuId("Start")) {
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
