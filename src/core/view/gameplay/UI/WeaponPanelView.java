package core.view.gameplay.ui;


import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.Image;

public class WeaponPanelView {
    Image swordImage;
    Image staffImage;
    Image bowImage;

    // Panel in Cartesian coordinate system
    static int PANEL_RADIUS;
    static int PANEL_CENTER_X;
    static int PANEL_CENTER_Y;
    static final int X = 0;
    static final int Y = 1;

    public WeaponPanelView() {
        swordImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.STRONG_SWORD_ATTACK);
        swordImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.BOW_SHOT);
        swordImage = ResourceManager.getInstance().getSkillImage(SkillInstanceKind.STAFF_ATTACK);
    }

    private class WeaponOnPanel {
        private Item weapon;
        private Image imageMain;
        private Image imageOpacity;
        private int imageSideLn;
        private int onPanelPosition; // polar angle in degrees
        private int sectorSize;

    }

}
