package core.view.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.model.gameplay.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.ResourceManager;
import org.newdawn.slick.tiled.TiledMap;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObject> gameObjects;
    private List<GameObjectView> gameObjectViews;
    private List<Loot> lootList;
    private List<LootView> lootViewList;
    private Hero hero;
    private Camera camera;
    private ResourceManager resourceManager;
    private InventoryView inventoryView;
    private TiledMap tiledMap;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjects, Hero hero, List<Loot> lootList,
                        ResourceManager resourceManager, TiledMap tiledMap) throws SlickException {
        this.resourceManager = resourceManager;
        this.gameObjects = gameObjects;
        this.hero = hero;
        this.lootList = lootList;
        inventoryView = new InventoryView(hero.getInventory());
        this.tiledMap = tiledMap;
        tileView = new TileView(tiledMap, hero);

        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Enemy.class) {
                gameObjectViews.add(new EnemyView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Hero.class) {
                gameObjectViews.add(new HeroView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Tree.class) {
                gameObjectViews.add(new TreeView(gameObject, resourceManager));
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
            for (GameObject gameObject : gameObjects) {
                if (gameObjectView.gameObject == gameObject) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

        // looking for new gameObjects
        for (GameObject gameObject : gameObjects) {
            boolean found = false;
            for (GameObjectView gameObjectView : gameObjectViews) {
                if (gameObject == gameObjectView.gameObject) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (gameObject.getClass() == Wall.class) {
                    gameObjectViews.add(new WallView(gameObject, resourceManager));
                } else if (gameObject.getClass() == Enemy.class) {
                    gameObjectViews.add(new EnemyView(gameObject, resourceManager));
                } else if (gameObject.getClass() == Hero.class) {
                    gameObjectViews.add(new HeroView(gameObject, resourceManager));
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