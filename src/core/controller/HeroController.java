package core.controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.model.HeroManager;

public class HeroController {

    private HeroManager heroManager;

    public HeroController(HeroManager heroManager) {
        this.heroManager = heroManager;
    }

    public void update(GameContainer gc, final int delta) {
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            heroManager.move(0, delta);
            //heroManager.advancedMove(0, delta, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            heroManager.move(1, delta);
            //heroManager.advancedMove(1, delta, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            heroManager.move(2, delta);
            //heroManager.advancedMove(2, delta, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            heroManager.move(3, delta);
            //heroManager.advancedMove(3, delta, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        heroManager.rotate(gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

}