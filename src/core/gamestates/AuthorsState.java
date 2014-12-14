package core.gamestates;

import core.controller.AuthorsController;
import core.model.Authors;
import core.view.AuthorsView;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class AuthorsState extends BasicGameState {
    private static AuthorsState instance;

    private final GameState STATE_ID = GameState.AUTHORS;

    private AuthorsController authorsController;
    private AuthorsView authorsView;

    private AuthorsState() {
    }

    public static AuthorsState getInstance() {
        if (instance == null) {
            instance = new AuthorsState();
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
        authorsView.render(gc);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        authorsController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        authorsController = AuthorsController.getInstance();
        authorsView = new AuthorsView(Authors.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getInput().clearKeyPressedRecord();

        authorsController = null;
        authorsView = null;
        System.gc();
    }

}
