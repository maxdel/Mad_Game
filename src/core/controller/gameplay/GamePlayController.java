package core.controller.gameplay;

import core.PauseMenuState;
import core.model.gameplay.EnemyManager;
import core.model.gameplay.GameObjectMovingManager;
import core.model.gameplay.HeroManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import core.model.gameplay.World;
import core.view.gameplay.GamePlayView;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private World world;
    private GamePlayView gamePlayView;
    private ArrayList<GameObjectMovingController> gameObjectMovingControllers;

    public GamePlayController(final World world, final GamePlayView gamePlayView) throws SlickException {
        this.world = world;
        this.gamePlayView = gamePlayView;

        gameObjectMovingControllers = new ArrayList<GameObjectMovingController>();
        for (GameObjectMovingManager gameObjectMovingManager : world.getGameObjectMovingManagers()) {
            if (gameObjectMovingManager.getClass() == HeroManager.class) {
                gameObjectMovingControllers.add(new HeroController((HeroManager)gameObjectMovingManager));
            } else if (gameObjectMovingManager.getClass() == EnemyManager.class) {
                gameObjectMovingControllers.add(new EnemyController((EnemyManager)gameObjectMovingManager));
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame game, final int delta) {
        /* Must enter to pause menu in future */
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(PauseMenuState.getInstance().getID());
        }

        for (GameObjectMovingController gameObjectMovingController : gameObjectMovingControllers) {
            gameObjectMovingController.update(gc, world, delta);
        }
    }

}