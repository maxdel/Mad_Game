package core.model.menu;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains state of the start menu
 */
public class MenuStart extends Menu {

    private static MenuStart instance;

    private static Map<String, Integer> menuMap;

    private MenuStart() {
        super(menuMap);
    }

    public static MenuStart getInstance() {
        if (instance == null) {
            menuMap = new LinkedHashMap<String, Integer>();
            menuMap.put("Start", 0);
            menuMap.put("Load", 1);
            menuMap.put("Help", 2);
            menuMap.put("Exit", 3);
            instance = new MenuStart();
        }
        return instance;
    }

}