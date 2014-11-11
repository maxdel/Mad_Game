package core.model.menu;

import java.util.Map;
import java.util.Set;

public abstract class Menu {

    private Map<String, Integer> menuMap;

    private int currentChoice;

    public Menu(Map<String, Integer> menuMap) {
        this.menuMap = menuMap;
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

    public Set<String> getMenuTitles() {
        return menuMap.keySet();
    }



}
