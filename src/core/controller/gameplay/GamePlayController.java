package core.controller.gameplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.GameState;
import core.model.gameplay.Hero;
import core.model.gameplay.World;

import core.view.gameplay.GamePlayView;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private Hero hero;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;
    private final double rotateSpeed = 1.0/288;

    public GamePlayController(final World world, final GamePlayView gamePlayView) throws SlickException {
        hero = world.getHero();
        oldMouseX = -1;
        oldMouseY = -1;
        mouseX = -1;
        mouseY = -1;
    }

    public void update(GameContainer gc, StateBasedGame game) {
        /* Enter pause menu */
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameState.MENUPAUSE.getValue());
        }

        // Controls the direction of the hero
        oldMouseX = mouseX;
        oldMouseY = mouseY;
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();

        if (oldMouseX >= 0) hero.rotate((mouseX - oldMouseX) * (2 * Math.PI) * rotateSpeed);

        if (gc.getInput().getMouseX() > gc.getWidth() / 2 + 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() - (1.0 / rotateSpeed)),
                    gc.getHeight() - gc.getInput().getMouseY());
            mouseX = gc.getInput().getMouseX();
        }
        if (gc.getInput().getMouseX() < gc.getWidth() / 2 - 1 / rotateSpeed) {
            org.lwjgl.input.Mouse.setCursorPosition((int) (gc.getInput().getMouseX() + (1.0 / rotateSpeed)),
                    gc.getHeight() - gc.getInput().getMouseY());
            mouseX = gc.getInput().getMouseX();
        }

        // Controls the movement of the hero
        boolean[] downKeys = {false, false, false, false};
        if (gc.getInput().isKeyDown(Input.KEY_D)) downKeys[0] = true;
        if (gc.getInput().isKeyDown(Input.KEY_S)) downKeys[1] = true;
        if (gc.getInput().isKeyDown(Input.KEY_A)) downKeys[2] = true;
        if (gc.getInput().isKeyDown(Input.KEY_W)) downKeys[3] = true;

        double direction = -1;
        if (downKeys[0] && !downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 90;
        else if (downKeys[0] && downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 135;
        else if (!downKeys[0] && downKeys[1] && !downKeys[2] && !downKeys[3]) direction = 180;
        else if (!downKeys[0] && downKeys[1] && downKeys[2] && !downKeys[3]) direction = 225;
        else if (!downKeys[0] && !downKeys[1] && downKeys[2] && !downKeys[3]) direction = 270;
        else if (!downKeys[0] && !downKeys[1] && downKeys[2] && downKeys[3]) direction = 315;
        else if (!downKeys[0] && !downKeys[1] && !downKeys[2] && downKeys[3]) direction = 360;
        else if (downKeys[0] && !downKeys[1] && !downKeys[2] && downKeys[3]) direction = 45;

        direction *= Math.PI / 180;
        if (direction >= 0) {
            if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)) {
                hero.run(direction);
            } else {
                hero.walk(direction);
            }
        } else {
            hero.stand();
        }

        // hero.update(delta); // ???????????????????/
    }

}