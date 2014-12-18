package core.model.gameplay.skills;

import core.model.gameplay.items.Bow;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.Staff;
import core.model.gameplay.items.Sword;

public enum SkillInstanceKind {

    SWORD_ATTACK("Sword attack"), STRONG_SWORD_ATTACK("Strong sword attack"), STAFF_ATTACK("Staff attack"), // Area damage
    BOW_SHOT("Bow shot"), FIREBALL("Magic shot"), // BulletShot
    HEAL("Heal"), // Regen
    WIND_BOW("Wind bow"), // Skill improver
    BLINK("Blink"), // Blink skill

    ;

    private String name;
    private SkillInstanceKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSwordSkill() {
        if (this != BOW_SHOT && this != WIND_BOW
                && this != STAFF_ATTACK && this != FIREBALL) {
            return true;
        }
        return false;
    }

    public boolean isBowSkill() {
        if (this != SWORD_ATTACK && this != STRONG_SWORD_ATTACK
                && this != STAFF_ATTACK && this != FIREBALL) {
            return true;
        }
        return false;
    }

    public boolean isStaffSkill() {
        if (this != SWORD_ATTACK && this != STRONG_SWORD_ATTACK
                && this != BOW_SHOT && this != WIND_BOW) {
            return true;
        }

        return false;
    }

    public boolean isSkillsBelongsOtherWeapon(ItemRecord weapon) {
        if (weapon.getItem().getClass() == Sword.class) {
            return !isSwordSkill();
        } else if (weapon.getItem().getClass() == Bow.class) {
            return !isBowSkill();
        } else if (weapon.getItem().getClass() == Staff.class) {
            return !isStaffSkill();
        }

        return true;
    }
}
