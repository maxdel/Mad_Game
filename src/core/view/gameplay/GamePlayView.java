package core.view.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.model.gameplay.skills.bullets.Arrow;
import core.model.gameplay.skills.bullets.Fireball;
import core.model.gameplay.items.Loot;
import core.model.gameplay.units.*;
import core.resourcemanager.MadTiledMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<Obstacle> obstacles;
    private List<GameObjectView> gameObjectViews;
    private List<Loot> lootList;
    private List<LootView> lootViewList;
    private Hero hero;
    private Camera camera;
    private ResourceManager resourceManager;
    private InventoryView inventoryView;
    private MadTiledMap tiledMap;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<Obstacle> obstacles, Hero hero, List<Loot> lootList,
                        ResourceManager resourceManager, MadTiledMap tiledMap) throws SlickException {
        this.resourceManager = resourceManager;
        this.obstacles = obstacles;
        this.hero = hero;
        this.lootList = lootList;
        inventoryView = new InventoryView(hero.getInventory());
        this.tiledMap = tiledMap;
        tileView = new TileView(tiledMap, hero);

        gameObjectViews = new ArrayList<GameObjectView>();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(obstacle, resourceManager));
            } else if (obstacle.getClass() == Bandit.class) {
                gameObjectViews.add(new BanditView(obstacle, resourceManager));
            } else if (obstacle.getClass() == Hero.class) {
                gameObjectViews.add(new HeroView(obstacle, resourceManager));
            } else if (obstacle.getClass() == Tree.class) {
                gameObjectViews.add(new TreeView(obstacle, resourceManager));
            }  else if (obstacle.getClass() == Vampire.class) {
                gameObjectViews.add(new VampireView(obstacle, resourceManager));
            }
        }

        lootViewList = new ArrayList<LootView>();
        for (Loot loot : lootList) {
            lootViewList.add(new LootView(loot));
        }

        camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), hero.getX(), hero.getY(), hero.getDirection());

        tileView.render(gc, g, camera);

        updateViews();
        updateLootViewList();

        for (LootView lootView : lootViewList) {
            lootView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getCenterX(),
                    camera.getCenterY());
        }

        for (GameObjectView gameObjectView : gameObjectViews) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getCenterX(),
                    camera.getCenterY(), hero);
        }

        inventoryView.render(g, camera.getWidth(), camera.getHeight());
    }

    private void updateViews() throws SlickException {
        for (Iterator<GameObjectView> it = gameObjectViews.iterator(); it.hasNext();) {
            GameObjectView gameObjectView = it.next();
            boolean found = false;
            for (Obstacle obstacle : obstacles) {
                if (gameObjectView.obstacle == obstacle) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

        // looking for new gameObjects
        for (Obstacle obstacle : obstacles) {
            boolean found = false;
            for (GameObjectView gameObjectView : gameObjectViews) {
                if (obstacle == gameObjectView.obstacle) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (obstacle.getClass() == Wall.class) {
                    gameObjectViews.add(new WallView(obstacle, resourceManager));
                } else if (obstacle.getClass() == Bandit.class) {
                    gameObjectViews.add(new BanditView(obstacle, resourceManager));
                } else if (obstacle.getClass() == Hero.class) {
                    gameObjectViews.add(new HeroView(obstacle, resourceManager));
                } else if (obstacle.getClass() == Arrow.class) {
                    gameObjectViews.add(new ArrowView(obstacle, resourceManager));
                } else if (obstacle.getClass() == Fireball.class) {
                    gameObjectViews.add(new FireballView(obstacle, resourceManager));
                } else if (obstacle.getClass() == BanditArcher.class) {
                    gameObjectViews.add(new BanditArcherView(obstacle, resourceManager));
                } else if (obstacle.getClass() == Skeleton.class) {
                    gameObjectViews.add(new SkeletonView(obstacle, resourceManager));
                } else if (obstacle.getClass() == SkeletonMage.class) {
                    gameObjectViews.add(new SkeletonMageView(obstacle, resourceManager));
                }
            }
        }
    }

    private void updateLootViewList() throws SlickException {
        for (Iterator<LootView> it = lootViewList.iterator(); it.hasNext();) {
            LootView lootView = it.next();
            boolean found = false;
            for (Loot loot : lootList) {
                if (lootView.getLoot() == loot) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

        // looking for new loot
        for (Loot loot : lootList) {
            boolean found = false;
            for (LootView lootView : lootViewList) {
                if (loot == lootView.getLoot()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                lootViewList.add(new LootView(loot));
            }
        }
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

}