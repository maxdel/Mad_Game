package core.model.menu;

import java.util.*;

public class Menu {

    public List<MenuElement> menuElements;

    protected int currentChoice;

    public enum MenuElement {

        RESUME("Resume", 0), NEW_GAME("New game", 1), SETTING("Setting", 2), AUTHORS("Authors", 3), EXIT("Exit", 4);

        private String name;
        private int value;

        private MenuElement(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

    }
    private Menu() {
        menuElements = new ArrayList<>();
        for (MenuElement menuElement: MenuElement.values())
        menuElements.add(menuElement);
    }

    private static Menu instance;

    public static Menu getInstance() {

        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public void stepUp() {
        if (currentChoice == 0) {
            currentChoice = getNumberOfChoices() - 1;
        } else {
            currentChoice -= 1;
        }
    }

    public void stepDown() {
        if (currentChoice == getNumberOfChoices() - 1) {
            currentChoice = 0;
        } else {
            currentChoice += 1;
        }
    }
    /* Getters and setters region */
    public int getCurrentChoice() {
        return currentChoice;
    }

    public int getMenuId(MenuElement menuElement) {
        return menuElement.getValue();
    }

    public int getNumberOfChoices() {
        return menuElements.size();
    }

    public Set<String> getMenuTitles() {
        Set menuTitles = new LinkedHashSet<>();
        for (MenuElement menuElement: menuElements) {
            menuTitles.add(menuElement.getName());
        }
        return menuTitles;
    }



}