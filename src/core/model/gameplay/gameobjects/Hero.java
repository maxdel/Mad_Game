package core.model.gameplay.gameobjects;

import core.MathAdv;
import core.model.gameplay.World;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    private static Hero instance;

    private NPC selectedNPC;

    private Hero(double x, double y, double direction) {
        super(x, y, direction, GameObjInstanceKind.HERO);
    }

    public static Hero getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new Hero(0, 0, 0);
        }
        return instance;
    }

    public static Hero getInstance() {
        return getInstance(false);
    }

    public void talkToNpc() {
        if ((currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE ||
                currentState == GameObjectState.DIALOG) && selectedNPC != null) {
            stand();
            setDirection(MathAdv.getAngle(getX(), getY(), selectedNPC.getX(), selectedNPC.getY()));
            selectedNPC.apply();
            if (selectedNPC.isActive()) {
                currentState = GameObjectState.DIALOG;
            } else {
                currentState = GameObjectState.STAND;
            }
        }
    }

    @Override
    public void update(int delta) {
        updateSelectedNpc();

        super.update(delta);
    }

    private void updateSelectedNpc() {
        int searchPointDistance = (int) getMask().getBoundingCircleRadius() / 2 + 15;
        int searchRadius = 50;
        for (GameObject gameObject : World.getInstance().getGameObjectList()) {
            if (gameObject.getClass() == NPC.class) {
                if (MathAdv.getDistance(getX() + MathAdv.lengthDirX(getDirection(), searchPointDistance),
                        getY() + MathAdv.lengthDirY(getDirection(), searchPointDistance),
                        gameObject.getX(), gameObject.getY()) < searchRadius) {
                    selectedNPC = (NPC) gameObject;
                    return;
                }
            }
        }
        selectedNPC = null;
    }

    // Getters

    public NPC getSelectedNPC() {
        return selectedNPC;
    }

}