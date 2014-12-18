package core.model.gameplay.gameobjects.ai;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import org.newdawn.slick.geom.Point;

public class NPCAI extends BotAI {

    private enum NPCAIState implements BotAIState {
        STAND, WALK, QUEST
    }

    @Override
    protected void init() {
        currentState = NPCAIState.STAND;
        stateMap.put(NPCAIState.STAND, new AIState() {
            public void enter()           {  }
            public void run(int delta)    {  }
            public void update(int delta) {  }
        });
    }

}