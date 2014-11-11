package core.model.menu;

public class MenuManager {

    private Menu menu;

    public MenuManager(Menu menu) {
        this.menu = menu;
    }

    public void stepUp() {
        if (menu.getCurrentChoice() == 0) {
            menu.setCurrentChoice(menu.getNumberOfChoices() - 1);
        }
        else {
            menu.setCurrentChoice(menu.getCurrentChoice() - 1);
        }
    }

    public void stepDown() {
        if (menu.getCurrentChoice() == menu.getNumberOfChoices() - 1) {
            menu.setCurrentChoice(0);
        }
        else {
            menu.setCurrentChoice(menu.getCurrentChoice() + 1);
        }
    }

}
