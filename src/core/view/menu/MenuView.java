package core.view.menu;

import java.awt.Font;

import core.view.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

import core.model.menu.Menu;

/**
 * Renders start menu
 */
public class MenuView {

    private Menu menu;

    private String[] playerChoices;
    private Font menuItemFont;
    private TrueTypeFont menuItemTTF;

    public MenuView(Menu menu) {
        this.menu = menu;

        playerChoices = new String[menu.getMenuTitles().size()];
        menu.getMenuTitles().toArray(playerChoices);

        menuItemFont = new Font(ResourceManager.getInstance().getFontName("menufont"),
                ResourceManager.getInstance().isFontBold("menufont") ? Font.BOLD : 0,
                ResourceManager.getInstance().getFontSize("menufont"));
        menuItemTTF = new TrueTypeFont(menuItemFont, true);
    }

    public void render(GameContainer gc) {
        for (int i = 0; i < menu.getNumberOfChoices(); i++) {
            if (i == menu.getCurrentChoice()) {
                menuItemTTF.drawString(gc.getWidth() / 2 - menuItemTTF.getWidth(playerChoices[i]) / 2,
                        i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(255, 153, 0));
            }
            else {
                menuItemTTF.drawString(gc.getWidth() / 2 - menuItemTTF.getWidth(playerChoices[i]) / 2,
                        i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(200, 200, 200));
            }
        }
    }

}