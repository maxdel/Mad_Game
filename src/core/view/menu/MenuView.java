package core.view.menu;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import core.model.menu.Menu;

/**
 * Renders start menu
 */
public class MenuView {

    private Menu menu;

    private String[] playerChoices;
    private TrueTypeFont menuItemTTF;

    public MenuView(Menu menu) {
        this.menu = menu;

        playerChoices = new String[menu.getMenuTitles().size()];
        menu.getMenuTitles().toArray(playerChoices);

        menuItemTTF = ResourceManager.getInstance().getFont("menufont");
    }

    public void render(GameContainer gc) {
        for (int i = 0; i < menu.getNumberOfChoices(); i++) {
            if (i == menu.getCurrentChoice()) {
                menuItemTTF.drawString(gc.getWidth() / 2 - menuItemTTF.getWidth(playerChoices[i]) / 2,
                        i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(102, 255, 0));
            }
            else {
                menuItemTTF.drawString(gc.getWidth() / 2 - menuItemTTF.getWidth(playerChoices[i]) / 2,
                        i * 50 + 130, playerChoices[i], new org.newdawn.slick.Color(255, 255, 255));
            }
        }
    }

}