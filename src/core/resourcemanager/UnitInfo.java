package core.resourcemanager;

import core.model.gameplay.gameobjects.Attribute;
import core.model.gameplay.gameobjects.GameObjInstanceKind;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.items.LootRecord;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillInstanceKind;
import javafx.util.Pair;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;

public class UnitInfo {
    private GameObjInstanceKind type;
    private String mask;
    private double maximumHP;
    private double maximumMP;
    private double maximumSpeed;
    private double pAttack;
    private double mAttack;
    private double pArmor;
    private double mArmor;
    private List<Pair<ItemInstanceKind, Integer>> itemRecordList;
    private List<SkillInstanceKind> skillList;
    private List<Pair<ItemInstanceKind, Double>> lootList;

    public UnitInfo(GameObjInstanceKind type, String mask, double maximumHP, double maximumMP, double maximumSpeed,
                    double pAttack, double mAttack, double pArmor, double mArmor,
                    List<Pair<ItemInstanceKind, Integer>> itemRecordList, List<SkillInstanceKind> skillList,
                    List<Pair<ItemInstanceKind, Double>> lootList) {
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
        return ResourceManager.getInstance().getMask(GameObjInstanceKind.valueOf(mask.toUpperCase()));
    }

    public Attribute getAttribute() {
        return new Attribute(maximumHP, maximumMP, maximumSpeed, pAttack, mAttack, pArmor, mArmor);
    }

    public Inventory getInventory(Unit owner) {
        Inventory inventory = new Inventory(owner);
        for(Pair<ItemInstanceKind, Integer> pair : itemRecordList) {
            inventory.addItem(pair.getKey(), pair.getValue());
        }
        return inventory;
    }

    public List<Skill> getSkilLList() {
        ArrayList<Skill> skillArrayList = new ArrayList<Skill>();
        for (SkillInstanceKind skillInstanceKind : skillList) {
            skillArrayList.add(ResourceManager.getInstance().getSkill(skillInstanceKind));
        }
        return skillArrayList;
    }

    public List<LootRecord> getLootRecordList() {
        List<LootRecord> lootRecordList = new ArrayList<LootRecord>();
        for (Pair<ItemInstanceKind, Double> pair : lootList) {
            lootRecordList.add(new LootRecord(ResourceManager.getInstance().getItem(pair.getKey()), pair.getValue()));
        }
        return lootRecordList;
    }

}