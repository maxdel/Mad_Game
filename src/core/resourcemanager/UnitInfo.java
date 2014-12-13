package core.resourcemanager;

import core.model.gameplay.gameobjects.Attribute;
import core.model.gameplay.gameobjects.GameObjectType;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.LootRecord;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillKind;
import javafx.util.Pair;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;

public class UnitInfo {
    private GameObjectType type;
    private String mask;
    private double maximumHP;
    private double maximumMP;
    private double maximumSpeed;
    private double pAttack;
    private double mAttack;
    private double pArmor;
    private double mArmor;
    private List<Pair<String, Integer>> itemRecordList;
    private List<SkillKind> skillList;
    private List<Pair<String, Double>> lootList;

    public UnitInfo(GameObjectType type, String mask, double maximumHP, double maximumMP, double maximumSpeed,
                    double pAttack, double mAttack, double pArmor, double mArmor,
                    List<Pair<String, Integer>> itemRecordList, List<SkillKind> skillList,
                    List<Pair<String, Double>> lootList) {
        this.type = type;
        this.mask = mask;
        this.maximumHP = maximumHP;
        this.maximumMP = maximumMP;
        this.maximumSpeed = maximumSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.pArmor = pArmor;
        this.mArmor = mArmor;
        this.itemRecordList = itemRecordList;
        this.skillList = skillList;
        this.lootList = lootList;
    }

    public Shape getMask() {
        return ResourceManager.getInstance().getMask(mask);
    }

    public Attribute getAttribute() {
        return new Attribute(maximumHP, maximumMP, maximumSpeed, pAttack, mAttack, pArmor, mArmor);
    }

    public Inventory getInventory(Unit owner) {
        Inventory inventory = new Inventory(owner);
        for(Pair<String, Integer> pair : itemRecordList) {
            inventory.addItem(pair.getKey(), pair.getValue());
        }
        return inventory;
    }

    public List<Skill> getSkilLList() {
        ArrayList<Skill> skillArrayList = new ArrayList<Skill>();
        for (SkillKind skillKind : skillList) {
            skillArrayList.add(ResourceManager.getInstance().getSkill(skillKind));
        }
        return skillArrayList;
    }

    public List<LootRecord> getLootRecordList() {
        List<LootRecord> lootRecordList = new ArrayList<LootRecord>();
        for (Pair<String, Double> pair : lootList) {
            lootRecordList.add(new LootRecord(ResourceManager.getInstance().getItem(pair.getKey()), pair.getValue()));
        }
        return lootRecordList;
    }

}