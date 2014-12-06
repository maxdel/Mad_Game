package core.view.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.model.gameplay.skills.bullets.Arrow;
import core.model.gameplay.skills.bullets.Fireball;
import core.model.gameplay.items.Loot;
import core.model.gameplay.units.*;
import core.resource_manager.MadTiledMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resource_manager.ResourceManager;
import org.newdawn.slick.tiled.TiledMap;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObjectSolid> gameObjectSolids;
    private List<GameObjectView> gameObjectViews;
    private List<Loot> lootList;
    private List<LootView> lootViewList;
    private Hero hero;
    private Camera camera;
    private ResourceManager resourceManager;
    private InventoryView inventoryView;
    private MadTiledMap tiledMap;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<GameObjectSolid> gameObjectSolids, Hero hero, List<Loot> lootList,
                        ResourceManager resourceManager, MadTiledMap tiledMap) throws SlickException {
        this.resourceManager = resourceManager;
        this.gameObjectSolids = gameObjectSolids;
        this.hero = hero;
        this.lootList = lootList;
        inventoryView = new InventoryView(hero.getInventory());
        this.tiledMap = tiledMap;
        tileView = new TileView(tiledMap, hero);

        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
            if (gameObjectSolid.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObjectSolid, resourceManager));
            } else if (gameObjectSolid.getClass() == Bandit.class) {
                gameObjectViews.add(new BanditView(gameObjectSolid, resourceManager));
            } else if (gameObjectSolid.getClass() == Hero.class) {
                gameObjectViews.add(new HeroView(gameObjectSolid, resourceManager));
            } else if (gameObjectSolid.getClass() == Tree.class) {
                gameObjectViews.add(new TreeView(gameObjectSolid, resourceManager));
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
            for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
                if (gameObjectView.gameObjectSolid == gameObjectSolid) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

        // looking for new gameObjects
        for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
            boolean found = false;
            for (GameObjectView gameObjectView : gameObjectViews) {
                if (gameObjectSolid == gameObjectView.gameObjectSolid) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (gameObjectSolid.getClass() == Wall.class) {
                    gameObjectViews.add(new WallView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == Bandit.class) {
                    gameObjectViews.add(new BanditView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == Hero.class) {
                    gameObjectViews.add(new HeroView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == Arrow.class) {
                    gameObjectViews.add(new ArrowView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == Fireball.class) {
                    gameObjectViews.add(new FireballView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == BanditArcher.class) {
                    gameObjectViews.add(new BanditArcherView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == Skeleton.class) {
                    gameObjectViews.add(new SkeletonView(gameObjectSolid, resourceManager));
                } else if (gameObjectSolid.getClass() == SkeletonMage.class) {
                    gameObjectViews.add(new SkeletonMageView(gameObjectSolid, resourceManager));
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