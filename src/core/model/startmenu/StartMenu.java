package core.model.startmenu;

import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains state of the start menu
 */
public class StartMenu {

    private static StartMenu instance;

    private Map<String, Integer> menuMap;

    private int currentChoice;

    private StartMenu() {
        menuMap = new HashMap<String, Integer>();
        menuMap.put("Start", 0);
        menuMap.put("Load", 1);
        menuMap.put("Help", 2);
        menuMap.put("Exit", 3);
    }

    public static StartMenu getInstance() {
        if (instance == null) {
            instance = new StartMenu();
        }
        return instance;
    }

    public int getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(int currentChoice) {
        this.currentChoice = currentChoice;
    }

    public int getMenuId(String string) {
        return menuMap.get(string);
    }

    public int getNumberOfChoices() {
        return menuMap.size();
    }

}