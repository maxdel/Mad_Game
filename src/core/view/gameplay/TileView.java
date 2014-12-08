package core.view.gameplay;

import core.resourcemanager.MadTiledMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import core.model.gameplay.units.Hero;

public class TileView {

    private MadTiledMap tiledMap;
    private Hero hero;

    public TileView(MadTiledMap tiledMap, Hero hero) {
        this.tiledMap = tiledMap;
        this.hero = hero;
    }

    public void render(GameContainer gc, Graphics g, Camera camera) {
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), - (float)(camera.getDirection() / Math.PI * 180));

        tiledMap.render((int)- camera.getX(), (int) -camera.getY(), 0);

        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), (float)(camera.getDirection() / Math.PI * 180));
    }

}