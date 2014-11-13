package core.model.menu;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuPause extends Menu {

    private static MenuPause instance;

    private static Map<String, Integer> menuMap;

    private MenuPause() {
        super(menuMap);
    }

    public static MenuPause getInstance() {
        if (instance == null) {
            menuMap = new LinkedHashMap<String, Integer>();
            menuMap.put("Resume", 0);
            menuMap.put("Load", 1);
            menuMap.put("Help", 2);
            menuMap.put("Main menu", 3);
            instance = new MenuPause();
        }
        return instance;
    }

}
