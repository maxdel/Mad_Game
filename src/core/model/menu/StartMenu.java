package core.model.menu;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains state of the start menu
 */
public class StartMenu extends Menu {

    private static StartMenu instance;

    private static Map<String, Integer> menuMap;

    private StartMenu() {
        super(menuMap);

    }

    public static StartMenu getInstance() {
        if (instance == null) {
            menuMap = new LinkedHashMap<String, Integer>();
            menuMap.put("Start", 0);
            menuMap.put("Load", 1);
            menuMap.put("Help", 2);
            menuMap.put("Exit", 3);
            instance = new StartMenu();
        }
        return instance;
    }

}