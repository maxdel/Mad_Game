package core.resourcemanager;

import core.model.gameplay.skills.*;

import java.util.Map;

import org.newdawn.slick.Image;

public class SkillInfo {

    private String name;
    private String description;
    private String type;
    private Image image;
    private Map<String, String> map;
    private SkillKind kind;

    public SkillInfo(String name, String description, String type, Image image, Map<String, String> map, SkillKind kind) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.image = image;
        this.map = map;
        this.kind = kind;
    }

    public Skill getSkill() {
        Skill skill = null;
        if (type.equals("BulletShot")) {
            skill = new BulletShot(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItemType"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("bulletSpeed")), Double.parseDouble(map.get("pAttack")),
                    Double.parseDouble(map.get("mAttack")), kind);
        } else if (type.equals("AreaDamage")) {
            skill = new AreaDamage(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItemType"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("pAttack")), Double.parseDouble(map.get("mAttack")),
                    Double.parseDouble(map.get("radius")), Double.parseDouble(map.get("angle")) * Math.PI / 180, kind);
        } else if (type.equals("ImproverSkill")) {
            skill = new SkillImprover(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItemType"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("workTime")),
                    SkillKind.valueOf(map.get("skillToBuff")),
                    Integer.parseInt(map.get("castTimeDelta")), Integer.parseInt(map.get("cooldownTimeDelta")), kind);
        } else if (type.equals("Regen")) {
            skill = new Regen(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItemType"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("HPdelta")), kind);
        } else if (type.equals("BlinkSkill")) {
            skill = new BlinkSkill(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItemType"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("distance")), kind);
        }
        return skill;
    }

    public Image getImage() {
        return image;
    }

}