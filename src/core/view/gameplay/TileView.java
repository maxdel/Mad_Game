package core.view.gameplay;

import org.newdawn.slick.Graphics;

import core.resourcemanager.MadTiledMap;

public class TileView {

    private MadTiledMap tiledMap;

    public TileView(MadTiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public void render(Graphics g, Camera camera) {
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), - (float)(camera.getDirection() / Math.PI * 180));
        tiledMap.render((int)- camera.getX(), (int) -camera.getY(), 0);
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), (float)(camera.getDirection() / Math.PI * 180));
    }

}