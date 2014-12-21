package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.gameobjects.ai.Cell;
import core.model.gameplay.skills.Skill;
import core.view.gameplay.Camera;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class BanditArcherView extends UnitView {

    private UnitState previousState;
    private Animation attack;
    private Animation walk;

    public BanditArcherView(GameObject banditArcher) {
        super(banditArcher);
        walk = ResourceManager.getInstance().getAnimation("bandit_archer_walk");
        attack = ResourceManager.getInstance().getAnimation("bandit_archer_attack");
        animation = walk;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Bot banditArcher = (Bot) gameObject;

        if (banditArcher.getCurrentState() != previousState) {
            switch (banditArcher.getCurrentState()) {
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (banditArcher.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("bandit_walk_sword")));
                    animation.start();
                    break;
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    break;
                case SKILL:
                    Skill castingSkill = banditArcher.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case BANDIT_BOW_SHOT:
                            ResourceManager.getInstance().getSound("bow_shot").play();
                            animation = attack;
                            animation.restart();
                            banditArcher.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, banditArcher.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            animation.start();
                            break;
                    }
                    break;
            }
        }
        previousState = banditArcher.getCurrentState();

        super.render(g, camera);
    }

    private void drawPath(Graphics g, Camera camera) {
        Bot unit = (Bot) gameObject;
        Cell previousCell = null;
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), -camera.getDirectionDegrees());
        for(Cell cell : ((Bot) gameObject).getBotAI().getPath()) {
            if (previousCell != null) {
                g.drawLine((float) (previousCell.x - camera.getX()),
                        (float) (previousCell.y - camera.getY()),
                        (float) (cell.x - camera.getX()),
                        (float) (cell.y - camera.getY()));
                g.drawString(String.valueOf((int) cell.f) + "\nx:" + cell.x + "\ny:" + cell.y, (float) (cell.x - camera.getX()), (float) (cell.y - camera.getY()));
            } else {
                g.drawString(String.valueOf((int) cell.f) + "\nx:" + cell.x + "\ny:" + cell.y, (float) (cell.x - camera.getX()), (float) (cell.y - camera.getY()));
            }
            previousCell = cell;
        }
        if (unit.getBotAI().getAStar().getFirstReachablePoint(unit) != null) {
            g.fillOval((float) (unit.getBotAI().getAStar().getFirstReachablePoint(unit).getX() - camera.getX()),
                    (float) (unit.getBotAI().getAStar().getFirstReachablePoint(unit).getY() - camera.getY()), 10, 10);
        }
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());
    }

}