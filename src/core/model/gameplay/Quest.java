package core.model.gameplay;

import java.util.List;

public class Quest {

    private String name;
    private List<String> closedReplicaList;
    private List<String> openedReplicaList;
    private List<String> completedReplicaList;
    private List<QuestAction> actionList;

    public Quest(String questName, List<String> closedReplicaList, List<String> openedReplicaList,
                 List<String> completedReplicaList, List<QuestAction> actionList) {
        this.name = questName;
        this.closedReplicaList = closedReplicaList;
        this.openedReplicaList = openedReplicaList;
        this.completedReplicaList = completedReplicaList;
        this.actionList = actionList;
    }

    public String getName() {
        return name;
    }

    public List<String> getOpenedReplicaList() {
        return openedReplicaList;
    }

    public List<String> getClosedReplicaList() {
        return closedReplicaList;
    }

    public List<String> getCompletedReplicaList() {
        return completedReplicaList;
    }

    public List<QuestAction> getActionList() {
        return actionList;
    }

}