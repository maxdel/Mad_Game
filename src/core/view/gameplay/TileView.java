package core.view.gameplay;

import core.resourcemanager.TiledMapAdv;
import org.newdawn.slick.Graphics;

public class TileView {

    private TiledMapAdv tiledMap;

    public TileView(TiledMapAdv tiledMap) {
        this.tiledMap = tiledMap;
    }

    public void render(Graphics g, Camera camera) {
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), - (float)(camera.getDirection() / Math.PI * 180));
        tiledMap.render((int)- camera.getX(), (int) -camera.getY(), 0);
        g.rotate((float)camera.getCenterX(), (float)camera.getCenterY(), (float)(camera.getDirection() / Math.PI * 180));
    }

}