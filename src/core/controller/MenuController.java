package core.controller;

import core.gamestates.AuthorsState;
import core.model.Authors;
import core.model.Menu;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.gamestates.GamePlayState;
import core.model.gameplay.World;

/*
* Provides functions to handle user menu navigate events
* */
public class MenuController {

    private static MenuController instance;

    private Menu menu;


    private MenuController() {
        menu = Menu.getInstance();
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }


    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyPressed(Input.KEY_S)) {
            menu.stepDown();
        }

        if (input.isKeyPressed(Input.KEY_W)) {
            menu.stepUp();
        }

        if (!Main.changeFullScreenMode(gc, input)) {
            if (input.isKeyPressed(Input.KEY_ENTER)) {
                if (menu.getCurrentChoice() == menu.getMenuId(Menu.MenuElement.RESUME)) {
                    World.getInstance();
                    game.enterState(GamePlayState.getInstance().getID());
                } else if (menu.getCurrentChoice() == menu.getMenuId(Menu.MenuElement.NEW_GAME)) {
                    World.getInstance(true); // creating new world
                    game.enterState(GamePlayState.getInstance().getID());
                } else if (menu.getCurrentChoice() == menu.getMenuId(Menu.MenuElement.SETTING)) {

                } else if (menu.getCurrentChoice() == menu.getMenuId(Menu.MenuElement.AUTHORS)) {
                    game.enterState(AuthorsState.getInstance().getID());
                } else if (menu.getCurrentChoice() == menu.getMenuId(Menu.MenuElement.EXIT)) {
                    gc.exit();
                }
            }
        } else {
            gc.getInput().clearKeyPressedRecord();
        }


    }

}
