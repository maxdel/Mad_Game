package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.HeroManager;

/**
 * Handles user input events using "HeroManager" methods.
 */
public class HeroController {

    private HeroManager heroManager;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;

    final double rotateSpeed = 1/288;

    public HeroController(final HeroManager heroManager) {
        this.heroManager = heroManager;
    }

    public void update(GameContainer gc, final int delta) {
        oldMouseX = mouseX;
        oldMouseY = mouseY;
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();

        heroManager.rotate((mouseX - oldMouseX) * (2 * Math.PI) * rotateSpeed);

        if (gc.getInput().getMouseX() > gc.getWidth() + 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() - (1 / rotateSpeed)),
                    gc.getInput().getMouseY());
        }
        if (gc.getInput().getMouseX() < gc.getWidth() - 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() + (1 / rotateSpeed)),
                    gc.getInput().getMouseY());
        }

        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            heroManager.move(DirKey.RIGHT, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            heroManager.move(DirKey.BOT, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            heroManager.move(DirKey.LEFT, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            heroManager.move(DirKey.TOP, delta);
        }
    }

}