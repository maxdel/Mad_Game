package core.controller.gameplay;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.view.gameplay.GamePlayView;
import core.PauseMenuState;
import core.model.gameplay.*;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private World world;
    private Map<String, GameObjectMovingController> controllersMap;

    public GamePlayController(final World world, final GamePlayView gamePlayView) throws SlickException {
        this.world = world;

        // forming controller map
        controllersMap = new HashMap<String, GameObjectMovingController>();
        controllersMap.put(Hero.class.getSimpleName(), new HeroController(new HeroManager()));
        controllersMap.put(Enemy.class.getSimpleName(), new EnemyController(new EnemyManager()));
        // --

    }

    public void update(GameContainer gc, StateBasedGame game, final int delta) {
        // Enter pause menu
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(PauseMenuState.getInstance().getID());
        }
        // --

        for (GameObject gameObject : world.getGameObjects()) {
            try {
                controllersMap.get(gameObject.getClass().getSimpleName()).update(gc, delta, gameObject);
            }
            catch (NullPointerException npe) {
                continue; // passing, if object type useless
            }
        }
    }

}