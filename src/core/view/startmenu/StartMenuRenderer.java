package core.view.startmenu;

import core.controller.startmenu.StartMenuController;
import core.model.startmenu.StartMenu;
import org.newdawn.slick.*;

import java.awt.Font;

/**
 * Renders start menu
 */
public class StartMenuRenderer {

    private String[] playerChoices = new String[] {"NEW", "LOAD", "HELP", "EXIT"};
    private Font menuItemFont;
    private TrueTypeFont menuItemTTF;

    public StartMenuRenderer() {
        menuItemFont = new Font("Verdana", Font.BOLD, 40);
        menuItemTTF = new TrueTypeFont(menuItemFont, true);

    }

    public void render() {
        for (int i = 0; i < StartMenu.NUMBER_OF_CHOICES; i++) {
            if (i != StartMenu.getCurrentChoice()) {
                menuItemTTF.drawString(255, i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(200, 200, 200));
            }
            else {
                menuItemTTF.drawString(255, i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(255, 153, 0));
            }
        }
    }
}
