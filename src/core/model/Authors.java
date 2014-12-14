package core.model;

import core.gamestates.GameState;
import org.newdawn.slick.state.BasicGameState;

import java.util.ArrayList;
import java.util.List;

public class Authors {

    private static Authors instance;

    private String title;
    private List<String> authorsNames = new ArrayList<>();
    private String body;

    private Authors() {
        title = "Authors";
        authorsNames.add("Max Del'");
        authorsNames.add("Alex Guk");
        body = "From KPI";
    }

    public static Authors getInstance() {
        if (instance == null) {
            instance = new Authors();
        }

        return instance;
    }

    public List<String> getAuthorsNames() {
        return authorsNames;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }


}
