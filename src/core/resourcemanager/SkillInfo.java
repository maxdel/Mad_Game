package core.resourcemanager;

import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.*;
import org.newdawn.slick.Image;

import java.util.Map;

public class SkillInfo {

    private String name;
    private String description;
    private String type;
    private Image imageMain;
    private Image opacityImage;
    private Map<String, String> map;
    private SkillInstanceKind kind;

    public SkillInfo(String name, String description, String type, Image imageMain, Map<String, String> map, SkillInstanceKind kind) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.imageMain = imageMain;
        this.map = map;
        this.kind = kind;
    }

    public Skill getSkill() {
        Skill skill = null;

        ItemInstanceKind reqItemInstanceKind;
        try {
            reqItemInstanceKind = ItemInstanceKind.valueOf(map.get("requiredItemType").toUpperCase());
        }
        catch (IllegalArgumentException e) {
            reqItemInstanceKind = null;
        }

        if (type.equals("BulletShot")) {
            skill = new BulletShot(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")),
                    reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("bulletSpeed")), Double.parseDouble(map.get("pAttack")),
                    Double.parseDouble(map.get("mAttack")), Double.parseDouble(map.get("distance")),kind);
        } else if (type.equals("AreaDamage")) {
            skill = new AreaDamage(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")),
                    reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("pAttack")), Double.parseDouble(map.get("mAttack")),
                    Double.parseDouble(map.get("radius")), Double.parseDouble(map.get("angle")) * Math.PI / 180, kind);
        } else if (type.equals("ImproverSkill")) {
            skill = new SkillImprover(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")),
                    reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("workTime")),
                    SkillInstanceKind.valueOf(map.get("skillToBuff").toUpperCase()),
                    Integer.parseInt(map.get("castTimeDelta")), Integer.parseInt(map.get("cooldownTimeDelta")), kind);
        } else if (type.equals("Regen")) {
            skill = new Regen(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")),
                    reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("HPdelta")), kind);
        } else if (type.equals("BlinkSkill")) {
            skill = new BlinkSkill(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Integer.parseInt(map.get("distance")), kind);
        } else if (type.equals("BarrierSkill")) {
            skill = new BarrierSkill(name, description, Integer.parseInt(map.get("castTime")),
                    Integer.parseInt(map.get("postApplyTime")),
                    Integer.parseInt(map.get("cooldownTime")), reqItemInstanceKind,
                    Double.parseDouble(map.get("requiredHP")), Double.parseDouble(map.get("requiredMP")),
                    Double.parseDouble(map.get("hp")), Integer.parseInt(map.get("time")), kind);
        }
        return skill;
    }

    public Image getImageMain() {
        return imageMain;
    }

    public Image getOpacityImage() {
        return opacityImage;
    }

    public void setOpacityImage(Image opacityImage) {
        this.opacityImage = opacityImage;
    }
}