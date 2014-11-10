package core.view.startmenu;

import java.awt.Font;

import org.newdawn.slick.*;

import core.model.startmenu.StartMenu;

/**
 * Renders start menu
 */
public class StartMenuRenderer {

    private static StartMenuRenderer instance;

    private StartMenu startMenu;

    private String[] playerChoices = new String[] {"NEW", "LOAD", "HELP", "EXIT"};
    private Font menuItemFont;
    private TrueTypeFont menuItemTTF;

    private StartMenuRenderer() {
        startMenu = StartMenu.getInstance();

        menuItemFont = new Font("Verdana", Font.BOLD, 40);
        menuItemTTF = new TrueTypeFont(menuItemFont, true);
    }

    public static StartMenuRenderer getInstance() {
        if (instance == null) {
            instance = new StartMenuRenderer();
        }
        return instance;
    }

    public void render() {
        for (int i = 0; i < startMenu.getNumberOfChoices(); i++) {
            if (i != startMenu.getCurrentChoice()) {
                menuItemTTF.drawString(255, i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(200, 200, 200));
            }
            else {
                menuItemTTF.drawString(255, i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(255, 153, 0));
            }
        }
    }

}
