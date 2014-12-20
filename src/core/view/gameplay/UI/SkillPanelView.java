package core.view.gameplay.ui;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillImprover;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import core.resourcemanager.SkillInfo;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SkillPanelView {

    private static GameContainer gc;
    private List<SkillOnPanel> skillsOnPanel;
    private List<SkillOnPanel> stubImages;
    final int MAX_HERO_SKILLS = 5;

    public static boolean isOpacity = true;
    public static boolean skillBarsIsActive = true;

    private SkillOnPanel buff;

    // Panel in Cartesian coordinate system
    static int PANEL_RADIUS;
    static int PANEL_CENTER_X;
    static int PANEL_CENTER_Y;
    static final int X = 0;
    static final int Y = 1;
    static int imageSideLn;
    static int OFFSET = 38;

    private static TrueTypeFont fontName;
    private static TrueTypeFont fontDescription;



    public SkillPanelView(List<Skill> unitsSkills, Map<SkillInstanceKind, SkillInfo> skillInfos, GameContainer gc) {
        fontName = ResourceManager.getInstance().getFont("itemfont");
        fontDescription = ResourceManager.getInstance().getFont("descfont");

        skillsOnPanel = new ArrayList<>();

        this.gc = gc;

        updateSkillsOnPanel(unitsSkills, skillInfos);

        updateSkillsStubs(unitsSkills);
    }


    private void updateSkillsOnPanel(List<Skill> unitsSkills, Map<SkillInstanceKind, SkillInfo> skillInfos) {
        List<SkillOnPanel> newSkillsOnPanel;
        newSkillsOnPanel = new ArrayList<>();

        for (int i = 0; i < unitsSkills.size(); i++) {
            Skill skill = unitsSkills.get(i);
            Image skillImg = skillInfos.get(skill.getKind()).getImageMain();
            Image opacitySkillImg = skillInfos.get(skill.getKind()).getOpacityImage();
            newSkillsOnPanel.add(new SkillOnPanel(skill, skillImg, opacitySkillImg));
        }


        Iterator itr = newSkillsOnPanel.iterator();
        while (itr.hasNext()) {
            SkillOnPanel skillOnPanel = (SkillOnPanel) itr.next();
            ItemRecord dressedWeapon = Hero.getInstance().getInventory().getDressedWeapon();
            if (skillOnPanel.getSkill().getKind().isSkillsBelongsOtherWeapon(dressedWeapon)) {
                itr.remove();
            }
        }

        final int panel_size = 360;
        int imageSectorSize = panel_size / MAX_HERO_SKILLS;

        for (SkillOnPanel skillOnPanel: newSkillsOnPanel) {
            skillOnPanel.sectorSize = imageSectorSize;
        }

        int imgPositionOnCircle = OFFSET / 2;
        imgPositionOnCircle += newSkillsOnPanel.get(0).sectorSize;

        for (SkillOnPanel skillOnPanel: newSkillsOnPanel) {
            skillOnPanel.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += skillOnPanel.sectorSize;
        }



        skillsOnPanel = newSkillsOnPanel;
    }

    private void updateSkillsStubs(List<Skill> unitsSkills) {
        stubImages = new ArrayList<>(MAX_HERO_SKILLS);

        for (int i = 0; i < unitsSkills.size(); i++) {
            stubImages.add(new SkillOnPanel(
                    ResourceManager.getInstance().getImage("skill fake"),
                    ResourceManager.getInstance().getOpacitySkillFakeImage()));
        }

        final int panel_size = 360;
        int imageSectorSize = panel_size / MAX_HERO_SKILLS;
        for (SkillOnPanel fake: stubImages) {
            fake.sectorSize = imageSectorSize;
        }


        int imgPositionOnCircle = OFFSET / 2;
        imgPositionOnCircle += skillsOnPanel.get(0).sectorSize;
        for (SkillOnPanel fake: stubImages) {
            fake.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += fake.sectorSize;
        }
    }


    public void render(Graphics g) {
        for (SkillOnPanel fake: stubImages) {
            fake.simpleRender(g, isOpacity);
        }

        updateSkillsOnPanel(Hero.getInstance().getSkillList(), ResourceManager.getInstance().getSkillInfos());

        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            skillOnPanel.render(g, isOpacity);

            if (!isOpacity && skillBarsIsActive) {
                //skillOnPanel.renderName();
            }
        }
        renderCastingSkill(g);
        renderBuff(g);

        // render skill bars
        if (skillBarsIsActive) {
            renderSkillStatusesAddon(g);
        }
    }

    private void renderSkillStatusesAddon(Graphics g) {
        Skill skill;
        for (int j = 0; j < skillsOnPanel.size(); j++) {
            SkillOnPanel skillOnPanel = skillsOnPanel.get(j);
            skill = skillsOnPanel.get(j).getSkill();

            final int step = 60;
            fontDescription.drawString(50, j * step + 10, skill.getKind().getName() + ":", Color.cyan);
            fontName.drawString(50, j * step + 40,
                    skill.getDescription(),
                    Color.magenta);

               // g.drawString(skill.getName() + "[mp cost{" + (int) skill.getRequiredMP() + "}]" , 50, j * 50 + 10);

            final int width = 100;
            final int height = 10;
            final int x = 100;
            final int y = j * step + 25 + 10;

            skillOnPanel.renderCooolDownBar(g, width, height, x, y);

            skillOnPanel.renderCastingBar(g, width, height, x, y);
        }
    }


    private void renderBuff(Graphics g) {
        if (buff == null) {
            return;
        }

        buff.simpleRender(g, false);

        if (!((SkillImprover) buff.getSkill()).getTimerWorkTime().isActive()) {
            buff = null;
        }
    }

    private void renderCastingSkill(Graphics g) {
        Skill castingSkill = Hero.getInstance().getCastingSkill();

        if (castingSkill == null) {
            return;
        }

        for (SkillOnPanel skillOnPanel: skillsOnPanel) {
            if (skillOnPanel.getSkill().getKind() == castingSkill.getKind()) {
                skillOnPanel.simpleRender(g, false);
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
            return getAbsolutePosition(PANEL_RADIUS);
        }

        private int[] getAbsolutePosition(int panelRadius) {
            PANEL_RADIUS = panelRadius;
            PANEL_CENTER_X = 0 + 210;
            PANEL_CENTER_Y = gc.getHeight() - 100;

            int cartesianX = (int) (PANEL_RADIUS *  Math.cos((float) onPanelPosition / 180f * Math.PI));
            int cartesianY = (int) (PANEL_RADIUS *  Math.sin((float) onPanelPosition / 180f * Math.PI));
            int[] resultPoint = new int[2];
            resultPoint[X] = PANEL_CENTER_X + cartesianX - imageSideLn / 2;
            resultPoint[Y] = PANEL_CENTER_Y + cartesianY - imageSideLn / 2;
            return resultPoint;
        }

        private void simpleRender(Graphics g, boolean opacity) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            Image imageToDraw;
            if (opacity) {
                imageToDraw = imageOpacity;
            } else {
                imageToDraw = imageMain;
            }

            g.drawImage(imageToDraw, absoluteImgPositionX, absoluteImgPositionY);

        }

        private void render(Graphics g, boolean opacity) {
            simpleRender(g, opacity);

            if (skill.getTimerCooldown().isActive()) {
                renderOnCDMask(g, opacity);
            } else if (!Hero.getInstance().canStartCast(skill)) {
                renderCantCastMsak(g, opacity);
            }

            if (skill.getTimerAterCooldown().isActive()) {
                // TODO: add after CD animation render
            }


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

        /* CD - cooldown */
        public void renderOnCDMask(Graphics g, boolean opacity) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            Image mask = null;
            if (!opacity) {
                mask = ResourceManager.getInstance().getOnCDImage();
            } else {
                mask = ResourceManager.getInstance().getOpacityOnCDImage();
            }
            g.drawImage(mask, absoluteImgPositionX, absoluteImgPositionY);
        }

        private void renderCastingBar(Graphics g, int width, int height, int x, int y) {
            Hero hero = Hero.getInstance();
            if (hero.getCurrentState() == UnitState.SKILL && hero.getCastingSkill() == skill) {
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
                HeroInfoView.drawBar(g, x + preX, y, preWidth, height, currentPreCastTime, preCastTime,
                        Color.magenta);

                int currentPostCastTime;
                if (currentCastTime - preCastTime < 0) {
                    currentPostCastTime = 0;
                } else {
                    currentPostCastTime = currentCastTime - preCastTime;
                }
                HeroInfoView.drawBar(g, x + postX, y, postWidth, height, currentPostCastTime, postCastTime, Color.cyan);
            }
        }

        private void renderCooolDownBar(Graphics g, int width, int height, int x, int y) {
            HeroInfoView.drawBar(g, x, y, width, height,
                    skill.getTimerCooldown().getValue(), skill.getCooldownTime(),
                    Color.yellow);
        }


        public Skill getSkill() {
            return skill;
        }
    }

}
