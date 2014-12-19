package core.view.gameplay;

import java.util.ArrayList;
import java.util.List;

import core.view.gameplay.gameobject.GameObjectView;
import core.view.gameplay.gameobject.LootView;
import core.view.gameplay.gameobjectsolid.*;
import core.view.gameplay.gameobjectsolid.ArrowView;
import core.view.gameplay.gameobjectsolid.FireballView;
import core.view.gameplay.gameobjectsolid.TreeView;
import core.view.gameplay.gameobjectsolid.WallView;
import core.view.gameplay.ui.HeroInfoView;
import core.view.gameplay.ui.InventoryView;
import core.view.gameplay.ui.ItemPanelView;
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
    private HeroInfoView heroInfoView;
    private ItemPanelView itemPanelView;
    private TileView tileView;

    private List<Class> renderOrder;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjectList, TiledMapAdv tiledMap) throws SlickException {
        this.inventoryView = new InventoryView(Hero.getInstance().getInventory());

        this.heroInfoView = new HeroInfoView(gc);
        this.itemPanelView = new ItemPanelView();

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
                case TREEBIG:
                    gameObjectViewList.add(new TreeBigView(gameObject));
                    break;
                case ROCK:
                    gameObjectViewList.add(new RockView(gameObject));
                    break;
                case ICEWALL:
                    gameObjectViewList.add(new IceWallView(gameObject));
                    break;
                case HERO:
                    gameObjectViewList.add(new HeroView(gameObject));
                    break;
                case BANDIT:
                    gameObjectViewList.add(new BanditView(gameObject));
                    break;
                case BANDITARCHER:
                    gameObjectViewList.add(new BanditArcherView(gameObject));
                    break;
                case BANDITBOSS:
                    gameObjectViewList.add(new BanditBossView(gameObject));
                    break;
                case GOLEM:
                    gameObjectViewList.add(new GolemView(gameObject));
                    break;
                case GOLEMTINY:
                    gameObjectViewList.add(new GolemTinyView(gameObject));
                    break;
                case GOLEMBOSS:
                    gameObjectViewList.add(new GolemBossView(gameObject));
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
                case WANDERER:
                    gameObjectViewList.add(new NPCView(gameObject));
                    break;
            }
        }

        this.camera = new Camera(gc.getWidth(), gc.getHeight());

        this.renderOrder = new ArrayList<>();
        renderOrder.add(WallView.class);
        renderOrder.add(LootView.class);

        renderOrder.add(BanditView.class);
        renderOrder.add(BanditArcherView.class);
        renderOrder.add(BanditBossView.class);

        renderOrder.add(SkeletonView.class);
        renderOrder.add(SkeletonMageView.class);
        renderOrder.add(VampireView.class);

        renderOrder.add(HeroView.class);

        renderOrder.add(ArrowView.class);
        renderOrder.add(WaterballView.class);
        renderOrder.add(FireballView.class);
        renderOrder.add(StoneView.class);
        renderOrder.add(VampiricKnifeView.class);

        renderOrder.add(TreeView.class);
        renderOrder.add(TreeBigView.class);
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), Hero.getInstance().getX(),
                Hero.getInstance().getY(), Hero.getInstance().getDirection());
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

        heroInfoView.render(g, camera);
        itemPanelView.render(gc, g);
    }

    public void update(int delta) {
        for (GameObjectView gameObjectView : gameObjectViewList) {
            gameObjectView.update(delta);
        }
        itemPanelView.update(delta);
    }

    private void updateGameObjectViewList() throws SlickException {
        /* Add required views */
        for (GameObject gameObject : World.getInstance().getGameObjectToAddList()) {
            switch (gameObject.getType()) {
                case ICEWALL:
                    gameObjectViewList.add(new IceWallView(gameObject));
                    break;
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
                case THORN:
                    gameObjectViewList.add(new ThornView(gameObject));
                    break;
                case STONE:
                    gameObjectViewList.add(new StoneView(gameObject));
                    break;
                case WATERBALL:
                    gameObjectViewList.add(new WaterballView(gameObject));
                    break;
                case VAMPIRICKNIFE:
                    gameObjectViewList.add(new VampiricKnifeView(gameObject));
                    break;
                case DARKFLAME:
                    gameObjectViewList.add(new DarkFlameView(gameObject));
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
                case BANDITBOSS:
                    gameObjectViewList.add(new BanditBossView(gameObject));
                    break;
                case GOLEM:
                    gameObjectViewList.add(new GolemView(gameObject));
                    break;
                case GOLEMTINY:
                    gameObjectViewList.add(new GolemTinyView(gameObject));
                    break;
                case GOLEMBOSS:
                    gameObjectViewList.add(new GolemBossView(gameObject));
                    break;
                case WANDERER:
                    gameObjectViewList.add(new NPCView(gameObject));
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