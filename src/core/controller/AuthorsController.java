package core.controller;


import core.gamestates.MenuState;
import core.model.Menu;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class AuthorsController {

    private static AuthorsController instance;

    private AuthorsController() {}

    public static AuthorsController getInstance() {
        if (instance == null) {
            instance = new AuthorsController();
        }
        return instance;
    }

    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();

        Main.changeFullScreenMode(gc, input);

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(MenuState.getInstance().getID());
        }
    }
}
