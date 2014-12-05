package core.resource_manager;

import core.model.gameplay.skills.AreaSkill;
import core.model.gameplay.skills.BulletSkill;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.units.GameObjectMoving;
import org.newdawn.slick.Image;

import java.util.Map;

public class SkillInfo {

    private String name;
    private String description;
    private String type;
    private Image image;
    private Map<String, String> map;

    public SkillInfo(String name, String description, String type, Image image, Map<String, String> map) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.image = image;
        this.map = map;
    }

    public Skill getSkill(GameObjectMoving owner) {
        Skill skill = null;
        if (type.equals("BulletSkill")) {
            skill = new BulletSkill(owner, name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItem"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("bulletSpeed")), Double.parseDouble(map.get("pAttack")),
                    Double.parseDouble(map.get("mAttack")));
        } else if (type.equals("AreaSkill")) {
            skill = new AreaSkill(owner, name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("cooldownTime")), map.get("requiredItem"),
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("pAttack")), Double.parseDouble(map.get("mAttack")),
                    Double.parseDouble(map.get("radius")), Double.parseDouble(map.get("angle")) * Math.PI / 180);
        }
        return skill;
    }

    public Image getImage() {
        return image;
    }

}