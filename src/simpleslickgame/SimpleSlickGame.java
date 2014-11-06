package simpleslickgame;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SimpleSlickGame extends BasicGame
{
    private float playerX=40; // начальная позиция игрока по X
    private float playerY=40; // начальная позиция игрока по Y
    private Animation player;
    private Polygon playerPoly;
    public BlockMap map;



    public SimpleSlickGame(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        container.setVSync(true); //включаем вертикальную синхронизацию
        SpriteSheet sheet = new SpriteSheet("resources/karbonator.png",32,32); //спрайт игрока
        map = new BlockMap("resources/map01.tmx");        // тайлмап
        player = new Animation();        //создаем анимацию игрока
        player.setAutoUpdate(false);
        for (int frame=0;frame<3;frame++) { // покадровая анимация игрока
            player.addFrame(sheet.getSprite(frame,0), 150);
        }
        playerPoly = new Polygon(new float[]{ //создаем полигон игрока
                playerX,playerY,
                playerX+32,playerY,
                playerX+32,playerY+32,
                playerX,playerY+32
        });
    }

    @Override
    public void update(GameContainer container, int i) throws SlickException {
        if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
            playerX--; // изменяем координаты игрока
            playerPoly.setX(playerX);  //обновляем положение полигона игрока
            if (entityCollisionWith()){ //если есть коллизия останавливаем игрока
                playerX++;
                playerPoly.setX(playerX);
            }
        }
        if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {

            playerX++;
            playerPoly.setX(playerX);
            if (entityCollisionWith()){
                playerX--;
                playerPoly.setX(playerX);
            }
        }
        if (container.getInput().isKeyDown(Input.KEY_UP)) {

            playerY--;
            playerPoly.setY(playerY);
            if (entityCollisionWith()){
                playerY++;
                playerPoly.setY(playerY);
            }
        }
        if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
            playerY++;
            playerPoly.setY(playerY);
            if (entityCollisionWith()){
                playerY--;
                playerPoly.setY(playerY);
            }
        }
        if(container.getInput().isKeyDown(Input.KEY_DOWN)|  //анимация персонажа при движении
                container.getInput().isKeyDown(Input.KEY_UP)|
                container.getInput().isKeyDown(Input.KEY_LEFT)|
                container.getInput().isKeyDown(Input.KEY_RIGHT)){
            player.setAutoUpdate(true);
        }else{player.setAutoUpdate(false);}

    }

    public boolean entityCollisionWith() throws SlickException {
        for (int i = 0; i < BlockMap.entities.size(); i++) {
            Block entity1 = (Block) BlockMap.entities.get(i);
            if (playerPoly.intersects(entity1.poly)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        BlockMap.tmap.render(0, 0);
        g.drawAnimation(player, playerX, playerY);
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer container =
                    new AppGameContainer(new SimpleSlickGame("MAD"), 640, 480, false);
            container.start();

        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}