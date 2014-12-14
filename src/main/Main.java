package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import core.GameStatesContainer;
import core.GamePlayState;

public class Main {

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(GameStatesContainer.getInstance("Mad Game"));
            //appgc.setVSync(true);
            appgc.start();
        }
        catch (SlickException ex) {
            Logger.getLogger(GamePlayState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean changeFullScreenMode(GameContainer gc, Input input) throws SlickException {
        // Change full-screen mode
        if (input.isKeyDown(Input.KEY_LALT) && input.isKeyDown(Input.KEY_ENTER)) {
            if (gc.isFullscreen()) {
                ((AppGameContainer) gc).setDisplayMode(640, 480, false);
            } else {
                ((AppGameContainer) gc).setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
            }
            return true;
        }

        return false;
    }
}