package core.model.gameplay.skills;

public enum SkillInstanceKind {

    SWORD_ATTACK("Sword attack"), STRONG_SWORD_ATTACK("Strong sword attack"), STAFF_ATTACK("Staff attack"), // Area damage
    BOW_SHOT("Bow shot"), FIREBALL("Magic shot"), // BulletShot
    HEAL("Heal"), // Regen
    WIND_BOW("Wind bow"), // Skill improver
    BLINK("Blink") // Blink skill
    ;

    private String name;
    private SkillInstanceKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
