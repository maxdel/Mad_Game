package core.controller.gameplay;

import core.PauseMenuState;
import core.model.gameplay.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import core.view.gameplay.GamePlayView;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private World world;

    private Map<Class<?>, GameObjectMovingController> controllersMap;

    public GamePlayController(final World world, final GamePlayView gamePlayView) throws SlickException {
        this.world = world;

        // forming controller map
        controllersMap = new HashMap<Class<?>, GameObjectMovingController>();
        controllersMap.put(Hero.class, new HeroController(new HeroManager()));
        controllersMap.put(Enemy.class, new EnemyController(new EnemyManager()));
        // --

    }

    public void update(GameContainer gc, StateBasedGame game, final int delta) {
        /* Enter pause menu */
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(PauseMenuState.getInstance().getID());
        }
        // --

        for (GameObject gameObj : world.getGameObjects()) {
            try {
                controllersMap.get(gameObj.getClass()).update(gc, delta, gameObj);
            }
            catch (NullPointerException npe) {
                continue; // passing, if object type useless
            }
        }
    }

}