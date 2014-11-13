package core.controller.gameplay;

import core.model.gameplay.*;
import org.newdawn.slick.GameContainer;

public class EnemyController extends GameObjectMovingController {

    Enemy enemy;

    public EnemyController(final EnemyManager enemyManager) {
        this.gameObjectMovingManager = enemyManager;
    }

    public void update(GameContainer gc, final int delta, GameObject gameObj) {
        enemy = (Enemy) gameObj;
        EnemyManager enemyManager = (EnemyManager)gameObjectMovingManager;

        enemyManager.followTarget(enemy, World.getInstance(false).getHero().getX(),
                World.getInstance(false).getHero().getY(), delta);

        enemyManager.update(enemy, delta);
    }

}