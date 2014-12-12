package core.view.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.items.Loot;
import core.resourcemanager.MadTiledMap;
import core.resourcemanager.ResourceManager;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObjectView> gameObjectViewList;
    private List<LootView> lootViewList;
    private Hero hero;
    private Camera camera;
    private InventoryView inventoryView;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<GameObjectSolid> gameObjectSolids, Hero hero, List<Loot> lootList,
                        MadTiledMap tiledMap) throws SlickException {
        this.hero = hero;
        this.inventoryView = new InventoryView(hero.getInventory());
        this.tileView = new TileView(tiledMap);

        this.gameObjectViewList = new ArrayList<>();
        ResourceManager resourceManager = ResourceManager.getInstance();
        for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
            switch (gameObjectSolid.getType()) {
                case WALL:
                    gameObjectViewList.add(new WallView(gameObjectSolid, resourceManager));
                    break;
                case TREE:
                    gameObjectViewList.add(new TreeView(gameObjectSolid, resourceManager));
                    break;
                case HERO:
                    gameObjectViewList.add(new HeroView(gameObjectSolid, resourceManager));
                    break;
                case BANDITARCHER:
                    gameObjectViewList.add(new BanditArcherView(gameObjectSolid, resourceManager));
                    break;
                case BANDIT:
                    gameObjectViewList.add(new BanditView(gameObjectSolid, resourceManager));
                    break;
                case VAMPIRE:
                    gameObjectViewList.add(new VampireView(gameObjectSolid, resourceManager));
                    break;
                case SKELETON:
                    gameObjectViewList.add(new SkeletonView(gameObjectSolid, resourceManager));
                    break;
                case SKELETONMAGE:
                    gameObjectViewList.add(new SkeletonMageView(gameObjectSolid, resourceManager));
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

        for (GameObjectView gameObjectView : gameObjectViewList) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getCenterX(),
                    camera.getCenterY(), hero);
        }

        inventoryView.render(g, camera.getWidth(), camera.getHeight());
    }

    private void updateViews() throws SlickException {
        ResourceManager resourceManager = ResourceManager.getInstance();

        for (GameObjectSolid gameObjectSolid : World.getInstance().getGameObjectToAddList()) {
            switch (gameObjectSolid.getType()) {
                case WALL:
                    gameObjectViewList.add(new WallView(gameObjectSolid, resourceManager));
                    break;
                case BANDIT:
                    gameObjectViewList.add(new BanditView(gameObjectSolid, resourceManager));
                    break;
                case HERO:
                    gameObjectViewList.add(new HeroView(gameObjectSolid, resourceManager));
                    break;
                case ARROW:
                    gameObjectViewList.add(new ArrowView(gameObjectSolid, resourceManager));
                    break;
                case FIREBALL:
                    gameObjectViewList.add(new FireballView(gameObjectSolid, resourceManager));
                    break;
                case BANDITARCHER:
                    gameObjectViewList.add(new BanditArcherView(gameObjectSolid, resourceManager));
                    break;
                case SKELETON:
                    gameObjectViewList.add(new SkeletonView(gameObjectSolid, resourceManager));
                    break;
                case SKELETONMAGE:
                    gameObjectViewList.add(new SkeletonMageView(gameObjectSolid, resourceManager));
                    break;
            }
        }

        World.getInstance().getGameObjectToDeleteList().forEach(gameObjectSolid ->
                gameObjectViewList.removeIf(gameObjectView -> gameObjectView.gameObjectSolid == gameObjectSolid));
    }

    private void updateLootViewList() throws SlickException {
        World.getInstance().getLootToAddList().forEach(loot -> lootViewList.add(new LootView(loot)));
        World.getInstance().getLootToDeleteList().forEach(loot ->
                lootViewList.removeIf(lootView -> lootView.getLoot() == loot));
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

}