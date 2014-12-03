package core.view.gameplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import core.model.gameplay.Hero;

public class TileView {

    private TiledMap tiledMap;
    private Hero hero;

    public TileView(TiledMap tiledMap, Hero hero) {
        this.tiledMap = tiledMap;
        this.hero = hero;
    }

    public void render(GameContainer gc, Graphics g, Camera camera) {
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), - (float)(camera.getDirection() / Math.PI * 180));

        tiledMap.render((int)- camera.getX(), (int) -camera.getY(), 0);

        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), (float)(camera.getDirection() / Math.PI * 180));
    }

}