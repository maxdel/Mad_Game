package core.view.gameplay;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObjectState;
import core.model.gameplay.gameobjects.Hero;
import org.newdawn.slick.*;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.GameObjectSolid;

public class BanditView extends GameObjectView {

    private Animation animationWalkSword;
    private GameObjectState previousState;

    public BanditView(GameObjectSolid enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("bandit");
        animationWalkSword = resourceManager.getAnimation("bandit_walk_sword");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        Bot bandit = (Bot) gameObjectSolid;

        if (bandit.getCurrentState() != previousState) {
            switch (bandit.getCurrentState()) {
                case MOVE:
                    animation = animationWalkSword;
                    animation.setSpeed((float) (bandit.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("bandit_walk_sword")));
                    animation.start();
                    break;
                case STAND:
                    animation = animationWalkSword;
                    animation.setCurrentFrame(0);
                    animation.stop();
                    break;
                case ITEM:
                    break;
                case SKILL:
                    break;
            }
        }
        previousState = bandit.getCurrentState();

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                (float) (viewDegreeAngle - bandit.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(bandit.getX() - viewX), (int)(bandit.getY() - viewY) - 50, 60, 8,
                bandit.getAttribute().getHP().getCurrent(),
                bandit.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, (int) (bandit.getX() - viewX), (int) (bandit.getY() - viewY) - 38, 60, 8, 
                bandit.getAttribute().getMP().getCurrent(),
                bandit.getAttribute().getMP().getMaximum(),
                Color.blue);
        String str = "curState: " + bandit.getCurrentState();
        g.drawString(str,
                (float) (bandit.getX() - viewX),
                (float) (bandit.getY() - viewY - 80));
        /*g.drawString(String.valueOf((int) bandit.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) bandit.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) bandit.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getHP().getMaximum()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) bandit.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));*/
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                - (float) (viewDegreeAngle - bandit.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}