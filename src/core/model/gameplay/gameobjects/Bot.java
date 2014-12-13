package core.model.gameplay.gameobjects;

import core.model.gameplay.gameobjects.ai.BotAI;

public class Bot extends Unit {

    private BotAI botAI;

    public Bot(double x, double y, double direction, GameObjInstanceKind type, BotAI botAI) {
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