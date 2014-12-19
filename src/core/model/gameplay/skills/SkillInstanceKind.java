package core.model.gameplay.skills;

import core.model.gameplay.items.Bow;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.Staff;
import core.model.gameplay.items.Sword;

public enum SkillInstanceKind {

    // Hero
    SWORD_ATTACK, STRONG_SWORD_ATTACK, STAFF_ATTACK, FIREBALL, BOW_SHOT, WIND_BOW, HEAL, BLINK, ICE_WALL,
    // Bandit
    BANDIT_SWORD_ATTACK,
    // Bandit Archer
    BANDIT_BOW_SHOT,
    // Bandit Boss
    BANDITBOSS_SWORD_ATTACK, BANDITBOSS_STRONG_SWORD_ATTACK,
    // Golem
    PUNCH, THORN,
    // Golem Tiny
    WEAK_PUNCH, WEAK_THORN,
    // Golem Boss
    STRONG_PUNCH, STRONG_THORN, THROW_STONE,
    // Fire Elemental
    ELEMENTAL_FIREBALL,
    // Water Elemental
    ELEMENTAL_WATERBALL,
    // Skeleton
    SKELETON_SWORD_ATTACK,
    // Skeleton Mage
    SKELETON_FIREBALL,
    // Vampire
    KNIFE_ATTACK, SWORD_SPIN, VAMPIRIC_KNIFE, POWER_BEAM, DOOM, SHIFT;

    private String name;

    SkillInstanceKind() {
    }

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
