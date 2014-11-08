package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.HeroManager;

public class HeroController {

    private HeroManager heroManager;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;

    public HeroController(HeroManager heroManager) {
        this.heroManager = heroManager;
    }

    public void update(GameContainer gc, final int delta) {
        oldMouseX = mouseX;
        oldMouseY = mouseY;
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();

        heroManager.rotate((mouseX - oldMouseX) * Math.PI / 144);

        if (gc.getInput().getMouseX() > 320 + 288) {
            org.lwjgl.input.Mouse.setCursorPosition(gc.getInput().getMouseX() - 288, gc.getInput().getMouseY());
            //robot.mouseMove(gc.getInput().getMouseX() - 288, gc.getInput().getMouseY());
        }
        if (gc.getInput().getMouseX() < 320 - 288) {
            org.lwjgl.input.Mouse.setCursorPosition(gc.getInput().getMouseX() + 288, gc.getInput().getMouseY());
            //robot.mouseMove(gc.getInput().getMouseX() + 288, gc.getInput().getMouseY());
        }
        // ----- End -----*/

        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            //heroManager.move(0, delta);
            heroManager.advancedMove(0, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            //heroManager.move(1, delta);
            heroManager.advancedMove(1, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            //heroManager.move(2, delta);
            heroManager.advancedMove(2, delta);
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            //heroManager.move(3, delta);
            heroManager.advancedMove(3, delta);
        }
        //heroManager.rotate(gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

}