package core.view.gameplay.ui;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObjectState;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.skills.Skill;
import core.view.gameplay.Camera;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class HeroInfoView {

    Hero hero;
    GameContainer gc;

    public HeroInfoView(GameContainer gc) {
        this.hero = Hero.getInstance();
        this.gc = gc;
    }

    public void render(Graphics g, Camera camera) {
        // draw hp bar
        drawBar(g, (int) (hero.getX() - camera.getX()), (int) (hero.getY() - camera.getY()) - 46, 60, 6,
                hero.getAttribute().getCurrentHP(),
                hero.getAttribute().getMaximumHP(), Color.red);
        // draw mp bar
        drawBar(g, (int) (hero.getX() - camera.getX()), (int) (hero.getY() - camera.getY()) - 38, 60, 6,
                hero.getAttribute().getCurrentMP(),
                hero.getAttribute().getMaximumMP(), Color.blue);

        drawSkillProcessBar(g, (int) (hero.getX() - camera.getX()), (int) (hero.getY() - camera.getY()) + 38, 150, 4,
                Color.magenta, Color.cyan);


    }


    protected void drawSkillProcessBar(Graphics g, int x, int y, int width, int height, Color preCastColor,
                                       Color postCastColor) {

        if (hero.getCurrentState() == GameObjectState.SKILL) {
            int castTime = hero.getCastingSkill().getCastTime();
            int preCastTime = hero.getCastingSkill().getPreApplyTime();
            int postCastTime = castTime - preCastTime;
            int currentCastTime = hero.getCastingSkill().getCastTime() - hero.getCurrentSkillCastingTime();

            int preWidth = width * preCastTime / castTime;
            int postWidth = width * postCastTime / castTime;
            int preX = - postWidth / 2;
            int postX = preWidth / 2;

            int currentPreCastTime;
            if (currentCastTime > preCastTime) {
                currentPreCastTime = preCastTime;
            } else {
                currentPreCastTime = currentCastTime;
            }
            drawBar(g, x + preX, y, preWidth, height, currentPreCastTime, preCastTime, preCastColor);

            int currentPostCastTime;
            if (currentCastTime - preCastTime < 0) {
                currentPostCastTime = 0;
            } else {
                currentPostCastTime = currentCastTime - preCastTime;
            }
            drawBar(g, x + postX, y, postWidth, height, currentPostCastTime, postCastTime, postCastColor);
        }
    }

    public static void drawBar(Graphics g, int x, int y, int width, int height, double current, double maximum,
                                  Color color) {
        Color tempColor = g.getColor();
        g.setColor(Color.white);
        g.fillRect(x - width / 2, y - height / 2, width, height);
        g.setColor(color);
        g.fillRect(x - width / 2, y - height / 2, width * (float) (current / maximum), height);
        g.setColor(Color.darkGray);
        g.drawRect(x - width / 2, y - height / 2, width, height);
        g.setColor(tempColor);
    }



}
