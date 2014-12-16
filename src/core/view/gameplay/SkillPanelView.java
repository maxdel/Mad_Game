package core.view.gameplay;

import core.model.gameplay.World;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import core.resourcemanager.SkillInfo;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillPanelView {

    private static GameContainer gc;
    private List<SkillOnPanel> skillsOnPanel;
    final int MAX_HERO_SKILLS = 5;

    // Panel in Cartesian coordinate system
    static int PANEL_RADIUS;
    static int PANEL_CENTER_X;
    static int PANEL_CENTER_Y;
    static final int X = 0;
    static final int Y = 1;


    public SkillPanelView(List<Skill> unitsSkills, Map<SkillInstanceKind, SkillInfo> skillInfos, GameContainer gc) {
        this.gc = gc;

        // skillsOnPanel filling
        /*skillsOnPanel = new ArrayList<>(MAX_HERO_SKILLS);
        for (int i = 0; i < unitsSkills.size(); i++) {
                Skill skill = unitsSkills.get(i);
                Image skillImg = skillInfos.get(skill.getKind()).getImageMain();
                skillsOnPanel.add(new SkillOnPanel(skill, skillImg));
        }

        final int panel_size = 360;
        int imageSectorSize = panel_size / MAX_HERO_SKILLS;
        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.sectorSize = imageSectorSize;
        }

        int imgPositionOnCircle = skillsOnPanel.get(0).imageSideLn / 2;
        imgPositionOnCircle += 5 * skillsOnPanel.get(0).sectorSize;

        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += skillOnPanel.sectorSize;
        }*/
    }

    public void render(Graphics g) {
        // draw fakes
        /*int imgPositionOnCircle = skillsOnPanel.get(0).imageSideLn / 2;
        imgPositionOnCircle += 5 * skillsOnPanel.get(0).sectorSize;
        for (int i = 0; i < MAX_HERO_SKILLS; i++) {
            g.drawImage(ResourceManager.getInstance().getSkillFakeImage(),
                    SkillOnPanel.getAbsolutePosition(imgPositionOnCircle)[X],
                    SkillOnPanel.getAbsolutePosition(imgPositionOnCircle)[Y]);
            imgPositionOnCircle += skillsOnPanel.get(0).sectorSize;
        }
        //--

        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.renderFake(g);
            //--

            Skill castingSkill = World.getInstance().getHero().getCastingSkill();
            skillOnPanel.render(g, Color.green);

            if (skillOnPanel.skill.getTimerCooldown().isActive()) {
                skillOnPanel.render(g, Color.yellow);
            } else if (!World.getInstance().getHero().canStartCast(skillOnPanel.skill)) {
                skillOnPanel.render(g, Color.red);
            } else if (castingSkill != null && castingSkill.getKind() == skillOnPanel.skill.getKind()) {
                skillOnPanel.render(g);
            }
        }*/
    }



    static class SkillOnPanel {
        private Skill skill;
        private Image imageMain;
        private static int imageSideLn;
        private int onPanelPosition; // polar angle in degrees
        private int sectorSize;

        private SkillOnPanel(Skill skill, Image image) {
            this.skill = skill;
            this.imageMain = image;
            imageSideLn = image.getWidth();
        }

        /**
         * Returns left upper corner of image to draw on screen
         * @return left upper corner of image to draw on screen
         */
        private int[] getAbsolutePosition() {
            PANEL_RADIUS = (int) (imageSideLn * 1.5);
            PANEL_CENTER_X = 0 + 110;
            PANEL_CENTER_Y = gc.getHeight() - 100;

            int cartesianX = (int) (PANEL_RADIUS *  Math.cos((float) onPanelPosition / 180f * Math.PI));
            int cartesianY = (int) (PANEL_RADIUS *  Math.sin((float) onPanelPosition / 180f * Math.PI));
            int[] resultPoint = new int[2];
            resultPoint[X] = PANEL_CENTER_X + cartesianX - imageSideLn / 2;
            resultPoint[Y] = PANEL_CENTER_Y + cartesianY - imageSideLn / 2;
            return resultPoint;
        }


        private static int[] getAbsolutePosition(int onPanelPosition) {
            PANEL_RADIUS = (int) (imageSideLn * 1.5);
            PANEL_CENTER_X = 0 + 110;
            PANEL_CENTER_Y = gc.getHeight() - 100;

            int cartesianX = (int) (PANEL_RADIUS *  Math.cos((float) onPanelPosition / 180f * Math.PI));
            int cartesianY = (int) (PANEL_RADIUS *  Math.sin((float) onPanelPosition / 180f * Math.PI));
            int[] resultPoint = new int[2];
            resultPoint[X] = PANEL_CENTER_X + cartesianX - imageSideLn / 2;
            resultPoint[Y] = PANEL_CENTER_Y + cartesianY - imageSideLn / 2;
            return resultPoint;
        }

        private void render(Graphics g) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(imageMain, absoluteImgPositionX, absoluteImgPositionY);
        }


        private void render(Graphics g, Color color) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(imageMain, absoluteImgPositionX, absoluteImgPositionY, color);
        }

        private void renderFake(Graphics g) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(ResourceManager.getInstance().getSkillFakeImage(), absoluteImgPositionX, absoluteImgPositionY);
        }
    }


}
