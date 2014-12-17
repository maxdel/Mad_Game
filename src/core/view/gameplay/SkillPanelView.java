package core.view.gameplay;

import core.model.gameplay.World;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillImprover;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import core.resourcemanager.SkillInfo;
import org.lwjgl.Sys;
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
    private List<SkillOnPanel> fakeImages;
    final int MAX_HERO_SKILLS = 5;

    public static boolean isOpacity = true;
    private SkillOnPanel buff;

    // Panel in Cartesian coordinate system
    static int PANEL_RADIUS;
    static int PANEL_CENTER_X;
    static int PANEL_CENTER_Y;
    static final int X = 0;
    static final int Y = 1;


    public SkillPanelView(List<Skill> unitsSkills, Map<SkillInstanceKind, SkillInfo> skillInfos, GameContainer gc) {
        this.gc = gc;

        // skillsOnPanel and fakesImages filling
        fakeImages = new ArrayList<>(MAX_HERO_SKILLS);

        skillsOnPanel = new ArrayList<>(MAX_HERO_SKILLS);

        for (int i = 0; i < unitsSkills.size(); i++) {
                Skill skill = unitsSkills.get(i);
                Image skillImg = skillInfos.get(skill.getKind()).getImageMain();
                Image opacitySkillImg = skillInfos.get(skill.getKind()).getOpacityImage();
                skillsOnPanel.add(new SkillOnPanel(skill, skillImg, opacitySkillImg));
                fakeImages.add(new SkillOnPanel(
                        ResourceManager.getInstance().getSkillFakeImage(),
                        ResourceManager.getInstance().getOpacitySkillFakeImage()));
        }

        final int panel_size = 360;
        int imageSectorSize = panel_size / MAX_HERO_SKILLS;
        // fakes
        for (SkillOnPanel fake: fakeImages) {
            fake.sectorSize = imageSectorSize;
        }
        // skills
        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.sectorSize = imageSectorSize;
        }

        int imgPositionOnCircle = skillsOnPanel.get(0).imageSideLn / 2;
        imgPositionOnCircle += 5 * skillsOnPanel.get(0).sectorSize;
        // fakes
        for (SkillOnPanel fake: fakeImages) {
            fake.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += fake.sectorSize;
        }

        // skills
        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += skillOnPanel.sectorSize;
        }
    }

    public void render(Graphics g) {
        if (isOpacity) {
            for (SkillOnPanel fake: fakeImages) {
                fake.renderOpacity(g);

            }

            for (SkillOnPanel skillOnPanel: skillsOnPanel) {
                skillOnPanel.renderOpacity(g);

                if (!World.getInstance().getHero().canStartCast(skillOnPanel.getSkill())) {
                    skillOnPanel.renderCantCastMsak(g, true);
                }
            }


            renderCastingSkill(g);

        }

        else if (!isOpacity) {
            for (SkillOnPanel fake: fakeImages) {
                fake.render(g);
            }

            for (SkillOnPanel skillOnPanel: skillsOnPanel) {
                skillOnPanel.render(g);

                if (!World.getInstance().getHero().canStartCast(skillOnPanel.getSkill())) {
                    skillOnPanel.renderCantCastMsak(g, false);

                }
            }
        }

        renderBuff(g);


    }



    private void renderBuff(Graphics g) {
        if (buff == null) {
            return;
        }

        buff.render(g);

        if (!((SkillImprover) buff.getSkill()).getTimerWorkTime().isActive()) {
            buff = null;
        }
    }

    private void renderCastingSkill(Graphics g) {
        Skill castingSkill = World.getInstance().getHero().getCastingSkill();

        if (castingSkill == null) {
            return;
        }

        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            if (skillOnPanel.getSkill().getKind() == castingSkill.getKind()) {
                skillOnPanel.render(g);
                if (castingSkill.getKind() == SkillInstanceKind.WIND_BOW) {
                    buff = skillOnPanel;
                }
            }
        }
    }


    private static class SkillOnPanel {
        private Skill skill;
        private Image imageMain;
        private Image imageOpacity;
        private static int imageSideLn;
        private int onPanelPosition; // polar angle in degrees
        private int sectorSize;

        public SkillOnPanel(Image imageMain, Image imageOpacity) {
            this.imageMain = imageMain;
            this.imageOpacity = imageOpacity;
        }

        public SkillOnPanel(Skill skill, Image imageMain, Image imageOpacity) {
            this.skill = skill;
            this.imageMain = imageMain;
            this.imageOpacity = imageOpacity;
            imageSideLn = imageMain.getWidth();
        }

        /**
         * Returns left upper corner of image to draw on screen
         * @return left upper corner of image to draw on screen
         */
        private int[] getAbsolutePosition() {
            PANEL_RADIUS = (int) (imageSideLn);
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
        private void render(Graphics g, Color c) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(imageMain, absoluteImgPositionX, absoluteImgPositionY, c);
        }

        private void renderOpacity(Graphics g) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(imageOpacity, absoluteImgPositionX, absoluteImgPositionY);
        }
        private void renderOpacity(Graphics g, Color c) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            g.drawImage(imageOpacity, absoluteImgPositionX, absoluteImgPositionY, c);
        }

        public void renderCantCastMsak(Graphics g, boolean opacity) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            Image mask = null;
            if (!opacity) {
                mask = ResourceManager.getInstance().getCantCastImage();
            } else {
                mask = ResourceManager.getInstance().getOpacityCantCastImage();
            }
            g.drawImage(mask, absoluteImgPositionX, absoluteImgPositionY);
        }

        public Skill getSkill() {
            return skill;
        }
    }

}
