package core.view.gameplay;

import java.util.ArrayList;
import java.util.List;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.gameobject.GameObjectView;
import core.view.gameplay.gameobject.LootView;
import core.view.gameplay.gameobjectsolid.ArrowView;
import core.view.gameplay.gameobjectsolid.FireballView;
import core.view.gameplay.gameobjectsolid.TreeView;
import core.view.gameplay.gameobjectsolid.WallView;
import core.view.gameplay.ui.HeroInfoView;
import core.view.gameplay.ui.InventoryView;
import core.view.gameplay.ui.SkillPanelView;
import core.view.gameplay.unit.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.resourcemanager.tilemapadv.TiledMapAdv;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObjectView> gameObjectViewList;
    private Camera camera;
    private InventoryView inventoryView;
    private SkillPanelView skillPanelView;
    private HeroInfoView heroInfoView;
    private TileView tileView;

    private List<Class> renderOrder;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjectList, TiledMapAdv tiledMap) throws SlickException {
        this.inventoryView = new InventoryView(World.getInstance().getHero().getInventory());

        this.skillPanelView = new SkillPanelView(World.getInstance().getHero().getSkillList(),
                ResourceManager.getInstance().getSkillInfos(), gc);

        this.heroInfoView = new HeroInfoView(gc);

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
                case FIREELEMENTAL:
                    gameObjectViewList.add(new FireElementalView(gameObject));
                    break;
                case WATERELEMENTAL:
                    gameObjectViewList.add(new WaterElementalView(gameObject));
                    break;
            }
        }

        this.camera = new Camera(gc.getWidth(), gc.getHeight());

        this.renderOrder = new ArrayList<>();
        renderOrder.add(WallView.class);
        renderOrder.add(LootView.class);
        renderOrder.add(BanditView.class);
        renderOrder.add(BanditArcherView.class);
        renderOrder.add(SkeletonMageView.class);
        renderOrder.add(SkeletonView.class);
        renderOrder.add(HeroView.class);
        renderOrder.add(VampireView.class);
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), World.getInstance().getHero().getX(),
                World.getInstance().getHero().getY(), World.getInstance().getHero().getDirection());
        updateGameObjectViewList();

        tileView.render(g, camera);

        // Render order
        for (Class currentClass : renderOrder) {
            for (GameObjectView gameObjectView : gameObjectViewList) {
                if (gameObjectView.getClass() == currentClass) {
                    gameObjectView.render(g, camera);
                }
            }
        }
        for (GameObjectView gameObjectView : gameObjectViewList) {
            boolean isRendered = false;
            for (Class currentClass : renderOrder) {
                if (gameObjectView.getClass() == currentClass) {
                    isRendered = true;
                    break;
                }
            }
            if (!isRendered) {
                gameObjectView.render(g, camera);
            }
        }
        inventoryView.render(g, camera.getWidth(), camera.getHeight());

        skillPanelView.render(g);

        heroInfoView.render(g, camera);
    }

    public void update(int delta) {
        for (GameObjectView gameObjectView : gameObjectViewList) {
            gameObjectView.update(delta);
        }
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
                case FIREELEMENTAL:
                    gameObjectViewList.add(new FireElementalView(gameObject));
                    break;
                case WATERELEMENTAL:
                    gameObjectViewList.add(new WaterElementalView(gameObject));
                    break;
            }
        }
        /* Delete not required views */
        World.getInstance().getGameObjectToDeleteList().forEach(gameObject ->
                gameObjectViewList.removeIf(gameObjectView -> gameObjectView.getGameObject() == gameObject));
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

}