package core.model.gameplay;

public class QuestAction {

    private String action;
    private String type;
    private String name;

    public QuestAction(String action, String type, String name) {
        this.action = action;
        this.type = type;
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}