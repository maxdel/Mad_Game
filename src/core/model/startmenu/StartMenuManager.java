package core.model.startmenu;

public class StartMenuManager {

    private StartMenu startMenu;

    public StartMenuManager() {
        startMenu = StartMenu.getInstance();
    }

    public void stepUp() {
        if (startMenu.getCurrentChoice() == 0) {
            startMenu.setCurrentChoice(startMenu.NUMBER_OF_CHOICES - 1);
        }
        else {
            startMenu.setCurrentChoice(startMenu.getCurrentChoice() - 1);
        }
    }

    public void stepDown() {
        if (startMenu.getCurrentChoice() == StartMenu.NUMBER_OF_CHOICES - 1) {
            startMenu.setCurrentChoice(0);
        }
        else {
            startMenu.setCurrentChoice(startMenu.getCurrentChoice() + 1);
        }
    }

}
