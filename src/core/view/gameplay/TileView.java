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
        int heroTileX = (int)(hero.getX() / tiledMap.getTileWidth());
        int heroTileY = (int)(hero.getY() / tiledMap.getTileHeight());

        int radius = (int)(Math.max(gc.getWidth(), gc.getHeight()) * Math.sqrt(2) / 2);

        int fromTileX = heroTileX - radius / tiledMap.getTileWidth();
        if (fromTileX < 0) fromTileX = 0;
        int toTileX = heroTileX + radius / tiledMap.getTileWidth();
        if (toTileX >= tiledMap.getWidth()) toTileX = tiledMap.getWidth();

        int fromTileY = heroTileY - radius / tiledMap.getTileHeight();
        if (fromTileY < 0) fromTileY = 0;
        int toTileY = heroTileY + radius / tiledMap.getTileHeight();
        if (toTileY >= tiledMap.getHeight()) toTileY = tiledMap.getHeight();

        g.rotate(camera.getWidth() / 2, camera.getHeight() / 2, - (float)(camera.getDirection() / Math.PI * 180));
        for (int i = fromTileX; i < toTileX; ++i) {
            for (int j = fromTileY; j < toTileY; ++j) {
                tiledMap.getTileImage(i, j, 0).draw(tiledMap.getTileWidth() * i - (float) camera.getX(),
                        tiledMap.getTileHeight() * j - (float) camera.getY());
            }
        }
        g.rotate(camera.getWidth() / 2, camera.getHeight() / 2, (float)(camera.getDirection() / Math.PI * 180));
    }

}