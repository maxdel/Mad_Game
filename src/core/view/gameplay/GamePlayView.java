package core.view.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.model.gameplay.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.*;
import core.model.gameplay.items.Loot;
import core.resourcemanager.MadTiledMap;
import core.resourcemanager.ResourceManager;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObjectView> gameObjectViews;
    private List<Loot> lootList;
    private List<LootView> lootViewList;
    private Hero hero;
    private Camera camera;
    private InventoryView inventoryView;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<GameObjectSolid> gameObjectSolids, Hero hero, List<Loot> lootList,
                        MadTiledMap tiledMap) throws SlickException {
        this.hero = hero;
        this.lootList = lootList;
        this.inventoryView = new InventoryView(hero.getInventory());
        this.tileView = new TileView(tiledMap);

        this.gameObjectViews = new ArrayList<>();
        ResourceManager resourceManager = ResourceManager.getInstance();
        for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
            switch (gameObjectSolid.getType()) {
                case WALL:
                    gameObjectViews.add(new WallView(gameObjectSolid, resourceManager));
                    break;
                case TREE:
                    gameObjectViews.add(new TreeView(gameObjectSolid, resourceManager));
                    break;
                case HERO:
                    gameObjectViews.add(new HeroView(gameObjectSolid, resourceManager));
                    break;
                case BANDITARCHER:
                    gameObjectViews.add(new BanditArcherView(gameObjectSolid, resourceManager));
                    break;
                case BANDIT:
                    gameObjectViews.add(new BanditView(gameObjectSolid, resourceManager));
                    break;
                case VAMPIRE:
                    gameObjectViews.add(new VampireView(gameObjectSolid, resourceManager));
                    break;
                case SKELETON:
                    gameObjectViews.add(new SkeletonView(gameObjectSolid, resourceManager));
                    break;
                case SKELETONMAGE:
                    gameObjectViews.add(new SkeletonMageView(gameObjectSolid, resourceManager));
                    break;
            }
        }

        this.lootViewList = new ArrayList<>();
        lootList.forEach(loot -> lootViewList.add(new LootView(loot)));

        this.camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), hero.getX(), hero.getY(), hero.getDirection());

        tileView.render(g, camera);

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
        ResourceManager resourceManager = ResourceManager.getInstance();

        for (GameObjectSolid gameObjectSolid : World.getInstance().getToAddList()) {
            switch (gameObjectSolid.getType()) {
                case WALL:
                    gameObjectViews.add(new WallView(gameObjectSolid, resourceManager));
                    break;
                case BANDIT:
                    gameObjectViews.add(new BanditView(gameObjectSolid, resourceManager));
                    break;
                case HERO:
                    gameObjectViews.add(new HeroView(gameObjectSolid, resourceManager));
                    break;
                case ARROW:
                    gameObjectViews.add(new ArrowView(gameObjectSolid, resourceManager));
                    break;
                case FIREBALL:
                    gameObjectViews.add(new FireballView(gameObjectSolid, resourceManager));
                    break;
                case BANDITARCHER:
                    gameObjectViews.add(new BanditArcherView(gameObjectSolid, resourceManager));
                    break;
                case SKELETON:
                    gameObjectViews.add(new SkeletonView(gameObjectSolid, resourceManager));
                    break;
                case SKELETONMAGE:
                    gameObjectViews.add(new SkeletonMageView(gameObjectSolid, resourceManager));
                    break;
            }
        }

        for (GameObjectSolid gameObjectSolid : World.getInstance().getToDeleteList()) {
            gameObjectViews.removeIf(gameObjectView -> gameObjectView.gameObjectSolid == gameObjectSolid);
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