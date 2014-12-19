package core.view.gameplay.ui;


import core.model.gameplay.items.Item;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class WeaponPanelView {
    private GameContainer gc;

    private int CURRENT_WEAPON_ANGLE;
    private int SECOND_WEAPON_ANGLE;
    private int THIRD_WEAPON_ANGLE;

    Image stub = ResourceManager.getInstance().getImage("skill fake");
    Image swordImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.STRONG_SWORD_ATTACK);
    Image staffImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.BOW_SHOT);
    Image bowImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.STAFF_ATTACK);
    static int imageSideLn = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.BLINK).getWidth();
    int OFFSET = imageSideLn / 2 + 2 * imageSideLn;

    List<WeaponOnPanel> weaponsOnPanel;


    // Panel in Cartesian coordinate system
    static int PANEL_RADIUS;
    static int PANEL_CENTER_X;
    static int PANEL_CENTER_Y;
    static final int X = 0;
    static final int Y = 1;


    public WeaponPanelView(GameContainer gc) {
        this.gc = gc;

        weaponsOnPanel = new ArrayList<>();
        weaponsOnPanel.add(new WeaponOnPanel(stub));
        weaponsOnPanel.add(new WeaponOnPanel(stub));
        weaponsOnPanel.add(new WeaponOnPanel(stub));

        final int panel_size = 360;
        int imageSectorSize = panel_size / 3;
        for (WeaponOnPanel fake: weaponsOnPanel) {
            fake.sectorSize = imageSectorSize;
        }

        int imgPositionOnCircle = OFFSET + 180;
        imgPositionOnCircle += weaponsOnPanel.get(0).sectorSize;
        for (WeaponOnPanel fake: weaponsOnPanel) {
            fake.onPanelPosition = imgPositionOnCircle;
            imgPositionOnCircle += fake.sectorSize;
        }
    }

    public void render(Graphics g) {
        for (WeaponOnPanel fake : weaponsOnPanel) {
            fake.simpleRender(g);
        }
    }

    private class WeaponOnPanel {
        private Item weapon;
        private Image imageMain;
        private Image imageOpacity;
        private int onPanelPosition; // polar angle in degrees
        private int sectorSize;

        public WeaponOnPanel(Image imageMain) {
            this.imageMain = imageMain;
        }

        public WeaponOnPanel(Item weapon, Image imageMain) {
            this.weapon = weapon;
            this.imageMain = imageMain;
        }

        private void simpleRender(Graphics g) {
            int absoluteImgPositionX = getAbsolutePosition()[X];
            int absoluteImgPositionY = getAbsolutePosition()[Y];

            Image imageToDraw;
            imageToDraw = imageMain;

            g.drawImage(imageToDraw, absoluteImgPositionX, absoluteImgPositionY);

        }

        private int[] getAbsolutePosition() {
            PANEL_RADIUS = (int) (imageSideLn);
            return getAbsolutePosition(PANEL_RADIUS);
        }

        private int[] getAbsolutePosition(int panelRadius) {
            PANEL_RADIUS = panelRadius;
            PANEL_CENTER_X = gc.getWidth() - 210;
            PANEL_CENTER_Y = gc.getHeight() - 100;

            int cartesianX = (int) (PANEL_RADIUS * Math.cos((float) onPanelPosition / 180f * Math.PI));
            int cartesianY = (int) (PANEL_RADIUS * Math.sin((float) onPanelPosition / 180f * Math.PI));
            int[] resultPoint = new int[2];
            resultPoint[X] = PANEL_CENTER_X + cartesianX - imageSideLn / 2;
            resultPoint[Y] = PANEL_CENTER_Y + cartesianY - imageSideLn / 2;
            return resultPoint;
        }
    }
}
