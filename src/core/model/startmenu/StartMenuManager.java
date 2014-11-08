package core.model.startmenu;

public class StartMenuManager {

    private StartMenu startMenu;

    public StartMenuManager() {
        startMenu = StartMenu.getInstance();
    }

    public void stepUp() {
        if (startMenu.getCurrentChoice() == 0) {
            startMenu.setCurrentChoice(startMenu.getNumberOfChoices() - 1);
        }
        else {
            startMenu.setCurrentChoice(startMenu.getCurrentChoice() - 1);
        }
    }

    public void stepDown() {
        if (startMenu.getCurrentChoice() == startMenu.getNumberOfChoices() - 1) {
            startMenu.setCurrentChoice(0);
        }
        else {
            startMenu.setCurrentChoice(startMenu.getCurrentChoice() + 1);
        }
    }

}
