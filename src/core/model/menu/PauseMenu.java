package core.model.menu;

import java.util.LinkedHashMap;
import java.util.Map;

public class PauseMenu extends Menu {

    private static PauseMenu instance;

    private static Map<String, Integer> menuMap;

    private PauseMenu() {
        super(menuMap);

    }

    public static PauseMenu getInstance() {
        if (instance == null) {
            menuMap = new LinkedHashMap<String, Integer>();
            menuMap.put("Resume", 0);
            menuMap.put("Load", 1);
            menuMap.put("Help", 2);
            menuMap.put("Main menu", 3);
            instance = new PauseMenu();
        }
        return instance;
    }

}
