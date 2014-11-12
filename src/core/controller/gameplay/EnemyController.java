package core.controller.gameplay;

import org.newdawn.slick.GameContainer;

import core.model.gameplay.EnemyManager;
import core.model.gameplay.World;

public class EnemyController {

    private EnemyManager enemyManager;

    public EnemyController(final EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void update(GameContainer gc, World world, final int delta) {
        enemyManager.followTarget(world.getHero().getX(), world.getHero().getY(), delta);

        enemyManager.update(delta);
    }

}