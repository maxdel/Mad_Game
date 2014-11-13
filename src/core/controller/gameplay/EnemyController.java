package core.controller.gameplay;

import org.newdawn.slick.GameContainer;

import core.model.gameplay.EnemyManager;
import core.model.gameplay.World;

public class EnemyController extends GameObjectMovingController {

    public EnemyController(final EnemyManager enemyManager) {
        this.gameObjectMovingManager = enemyManager;
    }

    public void update(GameContainer gc, World world, final int delta) {
        EnemyManager enemyManager = (EnemyManager)gameObjectMovingManager;

        enemyManager.followTarget(world.getHero().getX(), world.getHero().getY(), delta);

        enemyManager.update(delta);
    }

}