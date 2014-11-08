package core.controller.gameplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.gameplay.HeroManager;

/**
 * Handles user input events using "HeroManager" methods.
 */
public class HeroController {

    private HeroManager heroManager;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;

    final double rotateSpeed = 1.0/288;

    public HeroController(final HeroManager heroManager) {
        this.heroManager = heroManager;
    }

    public void update(GameContainer gc, final int delta) {
        // Controls the direction of the hero
        oldMouseX = mouseX;
        oldMouseY = mouseY;
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();

        heroManager.rotate((mouseX - oldMouseX) * (2 * Math.PI) * rotateSpeed);

        if (gc.getInput().getMouseX() > gc.getWidth() / 2 + 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() - (1.0 / rotateSpeed)),
                    gc.getHeight() - gc.getInput().getMouseY());
        }
        if (gc.getInput().getMouseX() < gc.getWidth() / 2 - 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() + (1.0 / rotateSpeed)),
                    gc.getHeight() - gc.getInput().getMouseY());
        }

        // Controls the movement of the hero
        boolean[] downKeys = {false, false, false, false};
        if (gc.getInput().isKeyDown(Input.KEY_D)) downKeys[0] = true;
        if (gc.getInput().isKeyDown(Input.KEY_S)) downKeys[1] = true;
        if (gc.getInput().isKeyDown(Input.KEY_A)) downKeys[2] = true;
        if (gc.getInput().isKeyDown(Input.KEY_W)) downKeys[3] = true;

        double direction = -1;
        if (downKeys[0] && !downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 0;
        else if (downKeys[0] && downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 45;
        else if (!downKeys[0] && downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 90;
        else if (!downKeys[0] && downKeys[1] && downKeys[2] && !downKeys[3]) direction = 135;
        else if (!downKeys[0] && !downKeys[1] && downKeys[2] && !downKeys[3]) direction = 180;
        else if (!downKeys[0] && !downKeys[1] && downKeys[2] && downKeys[3]) direction = 225;
        else if (!downKeys[0] && !downKeys[1] && !downKeys[2] && downKeys[3]) direction = 270;
        else if (downKeys[0] && !downKeys[1] && !downKeys[2] && downKeys[3]) direction = 315;

        direction *= Math.PI / 180;
        if (direction >= 0) {
            direction += Math.PI / 2; // because top (270) means forward (0) for hero
            heroManager.move(direction, delta);
        }
    }
    
}