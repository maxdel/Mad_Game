package core.model.gameplay.gameobjects;

import core.model.gameplay.gameobjects.ai.BotAI;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.LootRecord;
import core.model.gameplay.skills.Skill;

import java.util.List;

public class Bot extends Unit {

    private BotAI botAI;

    public Bot(double x, double y, double direction, GameObjectSolidType type, BotAI botAI) {
        super(x, y, direction, type);
        this.botAI = botAI;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        botAI.run(delta);
    }

    @Override
    protected void onDelete() {
        super.onDelete();
        /* Loot drop here */
    }

    public BotAI getBotAI() {
        return botAI;
    }

}