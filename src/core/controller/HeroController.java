package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.HeroManager;

//import java.awt.Robot;

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

/*        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (gc.getInput().getMouseX() > 320 + 288) {
            assert robot != null;
            robot.mouseMove(gc.getInput().getAbsoluteMouseX() - 144, gc.getInput().getAbsoluteMouseY());
        }*/

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