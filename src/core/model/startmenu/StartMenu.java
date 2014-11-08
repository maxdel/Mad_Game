package core.model.startmenu;

import org.newdawn.slick.state.StateBasedGame;

/**
 * Contains state of the start menu
 */
public class StartMenu {

    private static StartMenu instance;

    public static final int NUMBER_OF_CHOICES = 4;
    public static final int START = 0;
    public static final int LOAD = 1;
    public static final int HELP = 2;
    public static final int EXIT = 3;

    private int currentChoice;

    private StartMenu() {

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

}