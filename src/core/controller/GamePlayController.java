package core.controller;

import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;
//import core.view.gameplay.ui.SkillPanelView;
import core.view.gameplay.ui.SkillPanelView;
import main.Main;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import core.gamestates.GameState;
import core.model.gameplay.World;

import core.view.gameplay.GamePlayView;

/**
 * Game play controller class, that uses game object's controllers to process external events (like user input).
 */
public class GamePlayController {

    private GamePlayView gamePlayView;
    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;
    private String controlMode;
    private boolean combatMode;
    private final double ROTATE_SPEED = 1f/288;
    private Hero hero = Hero.getInstance();

    public GamePlayController(World world, GamePlayView gamePlayView) throws SlickException {
        oldMouseX = -1;
        oldMouseY = -1;
        mouseX = -1;
        mouseY = -1;
        controlMode = "World";
        combatMode = false;
        this.gamePlayView = gamePlayView;
    }

    public void update(GameContainer gc, StateBasedGame game) throws SlickException {
        Input input = gc.getInput();

        Main.changeFullScreenMode(gc, input);

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameState.MENU.getValue());
        }

        // Pause
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            gc.setPaused(!gc.isPaused());
            if (!gc.isPaused()) {
                mouseX = input.getMouseX();
            }
        }
        if (gc.isPaused()) {
            return;
        }
        //--

        if (controlMode.equals("World")) {

            // Controls the direction of the hero
            oldMouseX = mouseX;
            oldMouseY = mouseY;
            mouseX = input.getMouseX();
            mouseY = input.getMouseY();

            if (oldMouseX >= 0) Hero.getInstance().rotate((mouseX - oldMouseX) * (2 * Math.PI) * ROTATE_SPEED);

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

            // Skill panel view showing
            if (input.isKeyDown(Input.KEY_LSHIFT)) {
                SkillPanelView.isOpacity = false;
            } else {
                SkillPanelView.isOpacity = true;
            }

            if (input.isKeyPressed(Input.KEY_TAB)) {
                SkillPanelView.skillBarsIsActive = !SkillPanelView.skillBarsIsActive;
            }

            if (input.isKeyPressed(Input.KEY_Q)) {
                Hero.getInstance().changeWeapon();
            }

            if (input.isKeyPressed(Input.KEY_E)) {
                Hero.getInstance().changeArmor();
            }

            if (input.isKeyPressed(Input.KEY_R)) {
                Hero.getInstance().forceUseItem(ItemInstanceKind.HEALING_FLASK);
            }

            if (input.isKeyPressed(Input.KEY_F)) {
                Hero.getInstance().forceUseItem(ItemInstanceKind.MANA_FLASK);
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
                Hero.getInstance().move(direction);
            } else {
                Hero.getInstance().stand();
            }

            // Pick loot
            if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)) {
                if (combatMode) {
                    Hero.getInstance().startCastSkill(SkillInstanceKind.SWORD_ATTACK);
                    Hero.getInstance().startCastSkill(SkillInstanceKind.BOW_SHOT);
                    Hero.getInstance().startCastSkill(SkillInstanceKind.FIREBALL);
                } else {
                    Hero.getInstance().startPickItem();
                }
            }

            // Talk to NPC
            if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {
                Hero.getInstance().talkToNpc();
            }

            // Use skill
            if (input.isKeyDown(input.KEY_1)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.SWORD_ATTACK);
                Hero.getInstance().startCastSkill(SkillInstanceKind.BOW_SHOT);
                Hero.getInstance().startCastSkill(SkillInstanceKind.FIREBALL);
            }
            if (input.isKeyDown(input.KEY_7)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.STRONG_SWORD_ATTACK);
            }
            if (input.isKeyDown(input.KEY_3)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.WIND_BOW);
            }
            if (input.isKeyDown(input.KEY_4)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.HEAL);
            }
            if (input.isKeyDown(input.KEY_5)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.STAFF_ATTACK);
            }
            if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.BLINK);
            }
            if (input.isKeyDown(input.KEY_2)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.ICE_WALL);
            }
            if (input.isKeyDown(input.KEY_8)) {
                Hero.getInstance().startCastSkill(SkillInstanceKind.DOOM);
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
                Hero.getInstance().stand();
            }
        } else if (controlMode.equals("Inventory")) {
            if (input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_TAB)) {
                controlMode = "World";
                gamePlayView.getInventoryView().setActive(false);
                mouseX = input.getMouseX();
                input.disableKeyRepeat();
            }

            if (input.isKeyPressed(Input.KEY_D)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectRight();
                if (selectedItemRecordIndex != -1) {
                    Hero.getInstance().getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_S)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectBottom();
                if (selectedItemRecordIndex != -1) {
                    Hero.getInstance().getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_A)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectLeft();
                if (selectedItemRecordIndex != -1) {
                    Hero.getInstance().getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }
            if (input.isKeyPressed(Input.KEY_W)) {
                int selectedItemRecordIndex = gamePlayView.getInventoryView().selectTop();
                if (selectedItemRecordIndex != -1) {
                    Hero.getInstance().getInventory().setSelectedRecord(selectedItemRecordIndex);
                }
            }

            // Drop item
            if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)) {
                Hero.getInstance().startDropItem();
            }

            // Use item
            if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)) {
                Hero.getInstance().startUseItem();
            }

        }
    }


}