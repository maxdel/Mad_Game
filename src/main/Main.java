package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import core.GameStatesContainer;
import core.GamePlayState;

public class Main {

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(GameStatesContainer.getInstance("Mad Game"));
            //appgc.setVSync(true);
            appgc.setDisplayMode(800, 600, false);
            appgc.start();
        }
        catch (SlickException ex) {
            Logger.getLogger(GamePlayState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}