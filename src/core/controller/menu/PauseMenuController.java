package core.controller.menu;

import core.GamePlayState;
import core.StartMenuState;
import core.model.gameplay.World;
import core.model.menu.MenuManager;
import core.model.menu.PauseMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class PauseMenuController {

    private static PauseMenuController instance;

    private PauseMenu pauseMenu;
    MenuManager menuManager;

    private PauseMenuController() {
        pauseMenu = PauseMenu.getInstance();
        menuManager = new MenuManager(pauseMenu);
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
            if (pauseMenu.getCurrentChoice() == pauseMenu.getMenuId("Resume")) {
                World.getInstance(false); // resuming existing world
                game.enterState(GamePlayState.getInstance().getID());
            }
            else if (pauseMenu.getCurrentChoice() == pauseMenu.getMenuId("Load")) {

            }
            else if (pauseMenu.getCurrentChoice() == pauseMenu.getMenuId("Help")) {

            }
            else if (pauseMenu.getCurrentChoice() == pauseMenu.getMenuId("Main menu")) {
                game.enterState(StartMenuState.getInstance().getID());
            }
        }
    }


}
