package core.view.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.resourcemanager.MadTiledMap;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObjectView> gameObjectViewList;
    private Camera camera;
    private InventoryView inventoryView;
    private TileView tileView;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjectList, MadTiledMap tiledMap) throws SlickException {
        this.inventoryView = new InventoryView(World.getInstance().getHero().getInventory());
        this.tileView = new TileView(tiledMap);

        this.gameObjectViewList = new ArrayList<>();
        for (GameObject gameObject : gameObjectList) {
            switch (gameObject.getType()) {
                case WALL:
                    gameObjectViewList.add(new WallView(gameObject));
                    break;
                case TREE:
                    gameObjectViewList.add(new TreeView(gameObject));
                    break;
                case HERO:
                    gameObjectViewList.add(new HeroView(gameObject));
                    break;
                case BANDITARCHER:
                    gameObjectViewList.add(new BanditArcherView(gameObject));
                    break;
                case BANDIT:
                    gameObjectViewList.add(new BanditView(gameObject));
                    break;
                case VAMPIRE:
                    gameObjectViewList.add(new VampireView(gameObject));
                    break;
                case SKELETON:
                    gameObjectViewList.add(new SkeletonView(gameObject));
                    break;
                case SKELETONMAGE:
                    gameObjectViewList.add(new SkeletonMageView(gameObject));
                    break;
            }
        }

        this.camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), World.getInstance().getHero().getX(),
                World.getInstance().getHero().getY(), World.getInstance().getHero().getDirection());
        updateGameObjectViewList();

        tileView.render(g, camera);
        for (GameObjectView gameObjectView : gameObjectViewList) {
            gameObjectView.render(g, camera);
        }
        inventoryView.render(g, camera.getWidth(), camera.getHeight());
    }

    private void updateGameObjectViewList() throws SlickException {
        /* Add required views */
        for (GameObject gameObject : World.getInstance().getGameObjectToAddList()) {
            switch (gameObject.getType()) {
                case LOOT:
                    gameObjectViewList.add(new LootView(gameObject));
                    break;
                case WALL:
                    gameObjectViewList.add(new WallView(gameObject));
                    break;
                case BANDIT:
                    gameObjectViewList.add(new BanditView(gameObject));
                    break;
                case HERO:
                    gameObjectViewList.add(new HeroView(gameObject));
                    break;
                case ARROW:
                    gameObjectViewList.add(new ArrowView(gameObject));
                    break;
                case FIREBALL:
                    gameObjectViewList.add(new FireballView(gameObject));
                    break;
                case BANDITARCHER:
                    gameObjectViewList.add(new BanditArcherView(gameObject));
                    break;
                case SKELETON:
                    gameObjectViewList.add(new SkeletonView(gameObject));
                    break;
                case SKELETONMAGE:
                    gameObjectViewList.add(new SkeletonMageView(gameObject));
                    break;
            }
        }
        /* Delete not required views */
        World.getInstance().getGameObjectToDeleteList().forEach(gameObject ->
                gameObjectViewList.removeIf(gameObjectView -> gameObjectView.gameObject == gameObject));
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

}