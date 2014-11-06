package simpleslickgame;

/**
 * Created by Max on 11/6/2014.
 */

import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
    public static TiledMap tmap;
    public static int mapWidth;
    public static int mapHeight;
    private int square[] = {1, 1, 15, 1, 15, 15, 1, 15}; //смещения для полигона, окружающего тайлы
    public static ArrayList<Object> entities;

    public BlockMap(String ref) throws SlickException {
        entities = new ArrayList<Object>();
        tmap = new TiledMap(ref, "resources"); //задаем папку с ресурсами карты и
        //создаем тайлмап
        mapWidth = tmap.getWidth() * tmap.getTileWidth(); //вычисляем ширину карты в пикселях
        mapHeight = tmap.getHeight() * tmap.getTileHeight(); //вычисляем высоту карты в пикселях

        for (int x = 0; x < tmap.getWidth(); x++) {
            for (int y = 0; y < tmap.getHeight(); y++) { //делаем проход по всей карте
                int tileID = tmap.getTileId(x, y, 0); //и получаем айди тайла
                if (tileID == 1) { //если айди тайла равен еденице
                    entities.add( // добавляем коллизию для тайла
                            new Block(x * 16, y * 16, square, "square")
                    );
                }
            }
        }
    }
}
