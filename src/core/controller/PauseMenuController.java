package core.controller;

import core.GamePlayState;
import core.StartMenuState;
import core.model.World;
import core.model.MenuManager;
import core.model.MenuPause;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class PauseMenuController {

    private static PauseMenuController instance;

    private MenuPause menuPause;
    MenuManager menuManager;

    private PauseMenuController() {
        menuPause = MenuPause.getInstance();
        menuManager = new MenuManager(menuPause);
    }

    public static PauseMenuController getInstance() {
        if (instance == null) {
            instance = new PauseMenuController();
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
            if (menuPause.getCurrentChoice() == menuPause.getMenuId("Resume")) {
                World.getInstance(false); // resuming existing world
                game.enterState(GamePlayState.getInstance().getID());
            }
            else if (menuPause.getCurrentChoice() == menuPause.getMenuId("Load")) {

            }
            else if (menuPause.getCurrentChoice() == menuPause.getMenuId("Help")) {

            }
            else if (menuPause.getCurrentChoice() == menuPause.getMenuId("Main menu")) {
                game.enterState(StartMenuState.getInstance().getID());
                World.deleteInstance();
            }
        }
    }


}
