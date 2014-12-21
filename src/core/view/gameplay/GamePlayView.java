package core.view.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.MathAdv;
import core.resourcemanager.ResourceManager;
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
import core.view.gameplay.ui.SkillPanelView;
import core.view.gameplay.unit.*;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.resourcemanager.tilemapadv.TiledMapAdv;

/*
* Renders game play game state
* */
public class GamePlayView {

    private GameContainer gc;
    private List<GameObjectView> gameObjectViewList;
    private Camera camera;
    private InventoryView inventoryView;
    private HeroInfoView heroInfoView;
    private ItemPanelView itemPanelView;
    private SkillPanelView skillPanelView;
    private TileView tileView;

    private List<Class> renderOrder;
    private int RENDER_RADIUS = 1000;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjectList, TiledMapAdv tiledMap) throws SlickException {this.gc = gc;
        this.inventoryView = new InventoryView(Hero.getInstance().getInventory());

        this.heroInfoView = new HeroInfoView(gc);
        this.itemPanelView = new ItemPanelView();

        this.tileView = new TileView(tiledMap);
        this.skillPanelView = new SkillPanelView(Hero.getInstance().getSkillList(),
                ResourceManager.getInstance().getSkillInfos(), gc);

        this.gameObjectViewList = new ArrayList<>();
        for (GameObject gameObject : gameObjectList) {
            if (getGameObjecView(gameObject) != null) {
                gameObjectViewList.add(getGameObjecView(gameObject));
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
        updateGameObjectViewList();

        tileView.render(g, camera);

        // Render order
        for (Class currentClass : renderOrder) {
            for (GameObjectView gameObjectView : gameObjectViewList) {
                if (gameObjectView.getClass() == currentClass) {
                    if (isInRenderRaius(gameObjectView)) {
                        gameObjectView.render(g, camera);
                    }
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
                if (isInRenderRaius(gameObjectView)) {
                    gameObjectView.render(g, camera);
                }
            }
        }
        inventoryView.render(g, camera.getWidth(), camera.getHeight());

        skillPanelView.render(g);
        heroInfoView.render(g, camera);
        itemPanelView.render(gc, g);
    }

    public void update(int delta) {
        camera.update(delta, gc.getWidth(), gc.getHeight(), Hero.getInstance().getX(),
                Hero.getInstance().getY(), Hero.getInstance().getDirection());

        for (GameObjectView gameObjectView : gameObjectViewList) {
            if (isInRenderRaius(gameObjectView)) {
                gameObjectView.update(delta);
            }
        }

        itemPanelView.update(delta);
    }

    private void updateGameObjectViewList() throws SlickException {
        /* Add required views */
        for (GameObject gameObject : World.getInstance().getGameObjectToAddList()) {
            if (getGameObjecView(gameObject) != null) {
                gameObjectViewList.add(getGameObjecView(gameObject));
            }
        }
        /* Delete not required views */
        World.getInstance().getGameObjectToDeleteList().forEach(gameObject ->
                gameObjectViewList.removeIf(gameObjectView -> gameObjectView.getGameObject() == gameObject));
    }
    
    private GameObjectView getGameObjecView(GameObject gameObject) {
        switch (gameObject.getType()) {
            case ROCK:
                return new RockView(gameObject);
            case TREEBIG:
                return new TreeBigView(gameObject);
            case TREE:
                return new TreeView(gameObject);
            case ICEWALL:
                return new IceWallView(gameObject);
            case LOOT:
                return new LootView(gameObject);
            case WALL:
                return new WallView(gameObject);
            case BANDIT:
                return new BanditView(gameObject);
            case HERO:
                return new HeroView(gameObject);
            case ARROW:
                return new ArrowView(gameObject);
            case FIREBALL:
                return new FireballView(gameObject);
            case THORN:
                return new ThornView(gameObject);
            case STONE:
                return new StoneView(gameObject);
            case WATERBALL:
                return new WaterballView(gameObject);
            case VAMPIRICKNIFE:
                return new VampiricKnifeView(gameObject);
            case DARKFLAME:
                return new DarkFlameView(gameObject);
            case BANDITARCHER:
                return new BanditArcherView(gameObject);
            case SKELETON:
                return new SkeletonView(gameObject);
            case SKELETONMAGE:
                return new SkeletonMageView(gameObject);
            case FIREELEMENTAL:
                return new FireElementalView(gameObject);
            case WATERELEMENTAL:
                return new WaterElementalView(gameObject);
            case BANDITBOSS:
                return new BanditBossView(gameObject);
            case GOLEM:
                return new GolemView(gameObject);
            case GOLEMTINY:
                return new GolemTinyView(gameObject);
            case GOLEMBOSS:
                return new GolemBossView(gameObject);
            case WANDERER:
                return new NPCView(gameObject);
            case VAMPIRE:
                return new VampireView(gameObject);
            default:
                return null;
        }
    }

    private boolean isInRenderRaius(GameObjectView gameObjectView) {
        return MathAdv.getDistance(gameObjectView.getGameObject().getX(), gameObjectView.getGameObject().getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY()) < RENDER_RADIUS;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

}