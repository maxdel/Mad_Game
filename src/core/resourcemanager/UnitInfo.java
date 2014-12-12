package core.resourcemanager;

import core.model.gameplay.gameobjects.Attribute;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObjectSolidType;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.LootRecord;
import core.model.gameplay.skills.Skill;
import javafx.util.Pair;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;

public class UnitInfo {
    private GameObjectSolidType type;
    private String mask;
    private double maximumHP;
    private double maximumMP;
    private double maximumSpeed;
    private double pAttack;
    private double mAttack;
    private double pArmor;
    private double mArmor;
    private List<Pair<String, Integer>> itemRecordList;
    private List<String> skillList;
    private List<Pair<String, Double>> lootList;

    public UnitInfo(GameObjectSolidType type, String mask, double maximumHP, double maximumMP, double maximumSpeed,
                    double pAttack, double mAttack, double pArmor, double mArmor,
                    List<Pair<String, Integer>> itemRecordList, List<String> skillList,
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
        return ResourceManager.getInstance().getMask(GameObjectSolidType.valueOf(mask.toUpperCase()));
    }

    public Attribute getAttribute() {
        Attribute attribute = new Attribute(maximumHP, maximumMP, maximumSpeed, pAttack, mAttack, pArmor, mArmor);
        return attribute;
    }

    public Inventory getInventory(Unit owner) {
        Inventory inventory = new Inventory(owner);
        for(Pair<String, Integer> pair : itemRecordList) {
            inventory.addItem(pair.getKey(), pair.getValue());
        }
        return inventory;
    }

    public List<Skill> getSkilLList(Unit owner) {
        ArrayList<Skill> skillArrayList = new ArrayList<Skill>();
        for (String skillName : skillList) {
            skillArrayList.add(ResourceManager.getInstance().getSkill(owner, skillName));
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