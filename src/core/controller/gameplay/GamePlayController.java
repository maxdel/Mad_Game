package core.controller.gameplay;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.GameState;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.World;

import core.view.gameplay.GamePlayView;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private Hero hero;
    private GamePlayView gamePlayView;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;
    private String controlMode;
    private boolean combatMode;
    private final double ROTATE_SPEED = 1f/288;

    public GamePlayController(World world, GamePlayView gamePlayView) throws SlickException {
        hero = world.getHero();
        oldMouseX = -1;
        oldMouseY = -1;
        mouseX = -1;
        mouseY = -1;
        controlMode = "Hero";
        combatMode = false;
        this.gamePlayView = gamePlayView;
    }

    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();

        if (controlMode.equals("Hero")) {
            // Enter pause menu
            if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                game.enterState(GameState.MENUPAUSE.getValue());
            }

            // Controls the direction of the hero
            oldMouseX = mouseX;
            oldMouseY = mouseY;
            mouseX = input.getMouseX();
            mouseY = input.getMouseY();

            if (oldMouseX >= 0) hero.rotate((mouseX - oldMouseX) * (2 * Math.PI) * ROTATE_SPEED);

            if (input.getMouseX() > gc.getWidth() / 2 + 1 / ROTATE_SPEED) {
                Mouse.setCursorPosition((int) (input.getMouseX() - (1.0 / ROTATE_SPEED)),
                        gc.getHeight() - input.getMouseY());
                mouseX = input.getMouseX();
            }

            if (input.getMouseX() < gc.getWidth() / 2 - 1 / ROTATE_SPEED) {
                Mouse.setCursorPosition((int) (input.getMouseX() + (1.0 / ROTATE_SPEED)),
                        gc.getHeight() - input.getMouseY());
                mouseX = input.getMouseX();
            }

            // Controls the movement of the hero
            boolean[] downKeys = {false, false, false, false};
            if (input.isKeyDown(Input.KEY_D)) downKeys[0] = true;
            if (input.isKeyDown(Input.KEY_S)) downKeys[1] = true;
            if (input.isKeyDown(Input.KEY_A)) downKeys[2] = true;
            if (input.isKeyDown(Input.KEY_W)) downKeys[3] = true;

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
                if (direction <= Math.PI / 2 || direction >= 1.5 * Math.PI) {
                    if (input.isKeyDown(Input.KEY_LSHIFT)) {
                        hero.move(direction);
                    }
                } else {
                    hero.move(direction);
                }
            } else {
                hero.stand();
            }

            // Pick loot
            if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)) {
                if (combatMode) {
                    hero.startCastSkill(0);
                    hero.startCastSkill(1);
                    hero.startCastSkill(2);
                } else {
                    hero.startPickItem();
                }
            }

            // Use skill
            if (input.isKeyDown(input.KEY_1)) {
                hero.startCastSkill(0);
                hero.startCastSkill(1);
                hero.startCastSkill(2);
            }
            if (input.isKeyDown(input.KEY_2)) {
                hero.startCastSkill(3);
            }
            if (input.isKeyDown(input.KEY_3)) {
                hero.startCastSkill(4);
            }
            if (input.isKeyDown(input.KEY_4)) {
                hero.startCastSkill(5);
            }
            if (input.isKeyDown(input.KEY_5)) {
                hero.startCastSkill(6);
            }

            // Change full-screen mode
            if (input.isKeyDown(Input.KEY_LALT) && input.isKeyDown(Input.KEY_ENTER)) {
                if (gc.isFullscreen()) {
                    ((AppGameContainer) gc).setDisplayMode(640, 480, false);
                } else {
                    ((AppGameContainer) gc).setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
                }
            }

            // Swtich combat mode
            if (input.isKeyPressed(Input.KEY_CAPITAL)) {
                combatMode = !combatMode;
            }

            // Switch controller mode
            if (input.isKeyPressed(Input.KEY_TAB)) {
                controlMode = "Inventory";
                gamePlayView.getInventoryView().setActive(true);
                input.enableKeyRepeat();
                hero.stand();
            }
        } else if (controlMode.equals("Inventory")) {
            if (input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_TAB)) {
                controlMode = "Hero";
                gamePlayView.getInventoryView().setActive(false);
                mouseX = input.getMouseX();
                input.disableKeyRepeat();
            }

            if (input.isKeyPressed(Input.KEY_D)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectRight();
                if (selectedItemRecordIndex != -1) {
                    hero.getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_S)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectBottom();
                if (selectedItemRecordIndex != -1) {
                    hero.getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_A)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectLeft();
                if (selectedItemRecordIndex != -1) {
                    hero.getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_W)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectTop();
                if (selectedItemRecordIndex != -1) {
                    hero.getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }

            // Drop item
            if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)) {
                hero.startDropItem();
            }

            // Use item
            if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {
                hero.startUseItem();
            }
        }
    }

}