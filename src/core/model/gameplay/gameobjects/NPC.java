package core.model.gameplay.gameobjects;

import java.util.ArrayList;
import java.util.List;

import core.MathAdv;
import core.model.gameplay.Quest;
import core.model.gameplay.QuestAction;
import core.model.gameplay.gameobjects.ai.NPCAI;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;

public class NPC extends Bot {

    private List<Quest> questList;
    private int currentQuestId;
    private int currentReplicaId;
    private boolean isOpened;
    private boolean isActive;

    public NPC(double x, double y, GameObjInstanceKind kind) {
        super(x, y, 0, kind, new NPCAI());

        this.questList = new ArrayList<>();
        questList.add(ResourceManager.getInstance().getQuest("Beat bandits"));
        questList.add(ResourceManager.getInstance().getQuest("No quest"));

        this.currentQuestId = 0;
        this.currentReplicaId = -1;
        this.isOpened = false;
        this.isActive = false;
    }

    @Override
    protected void onDelete() {

    }

    public void apply() {
        setDirection(MathAdv.getAngle(getX(), getY(), Hero.getInstance().getX(), Hero.getInstance().getY()));

        if (!isActive) {
            currentReplicaId = -1;
            isActive = true;
        }

        currentReplicaId++;

        if (!isOpened && currentReplicaId == questList.get(currentQuestId).getClosedReplicaList().size()) {
            // Give something to hero at the beggining of the quest if necessary
            isOpened = true;
            isActive = false;
        } else if (isOpened && !isCompleted() && currentReplicaId == questList.get(currentQuestId).getOpenedReplicaList().size()) {
            isActive = false;
        } else if (isOpened && isCompleted() && currentReplicaId == questList.get(currentQuestId).getCompletedReplicaList().size()) {
            doActions();
            if (currentQuestId + 1 < questList.size()) {
                currentQuestId++;
                isOpened = false;
            }
            isActive = false;
        }
    }

    private boolean isCompleted() {
        for (QuestAction questAction : questList.get(currentQuestId).getActionList()) {
            if (questAction.getAction().equals("take") && questAction.getType().equals("item")) {
                if (!Hero.getInstance().getInventory().isItemExists(
                        ItemDB.getInstance().getItem(ItemInstanceKind.valueOf(questAction.getName().toUpperCase())))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void doActions() {
        for (QuestAction questAction : questList.get(currentQuestId).getActionList()) {
            if (questAction.getAction().equals("take") && questAction.getType().equals("item")) {
                Hero.getInstance().getInventory().deleteItem(
                        ItemInstanceKind.valueOf(questAction.getName().toUpperCase()));
            } else if (questAction.getAction().equals("give") && questAction.getType().equals("item")) {
                Hero.getInstance().getInventory().addItem(
                        ItemInstanceKind.valueOf(questAction.getName().toUpperCase()));
            } else if (questAction.getAction().equals("give") && questAction.getType().equals("skill")) {
                Hero.getInstance().getSkillList().add(ResourceManager.getInstance().getSkill(
                        SkillInstanceKind.valueOf(questAction.getName().toUpperCase())));
            }
        }
    }

    // Getters

    public boolean isActive() {
        return isActive;
    }

    public String getCurrentReplica() {
        if (!isOpened) {
            return questList.get(currentQuestId).getClosedReplicaList().get(currentReplicaId);
        } else if (!isCompleted()) {
            return questList.get(currentQuestId).getOpenedReplicaList().get(currentReplicaId);
        } else {
            return questList.get(currentQuestId).getCompletedReplicaList().get(currentReplicaId);
        }
    }

}