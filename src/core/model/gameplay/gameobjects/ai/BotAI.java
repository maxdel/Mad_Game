package core.model.gameplay.gameobjects.ai;

import core.model.gameplay.gameobjects.Bot;

import java.util.HashMap;
import java.util.Map;

public abstract class BotAI {

    protected Bot owner;
    protected Map<BotAIState, AIState> stateMap;
    protected BotAIState currentState;
    private BotAIState previousState;

    public BotAI() {
        this(null);
    }

    public BotAI(Bot bot) {
        this.owner = bot;
        stateMap = new HashMap<BotAIState, AIState>();
        init();
    }

    public interface BotAIState {

    }

    public void run(int delta) {
        if (currentState != previousState) {
            stateMap.get(currentState).enter();
            previousState = currentState;
        }
        stateMap.get(currentState).run();
        stateMap.get(currentState).update(delta);
    }

    protected abstract void init();

    // Getters and setters

    public BotAIState getCurrentState() {
        return currentState;
    }

    public void setOwner(Bot owner) {
        this.owner = owner;
    }

}