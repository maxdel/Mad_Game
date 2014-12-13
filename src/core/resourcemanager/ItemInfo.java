package core.resourcemanager;

import java.util.Map;

import core.model.gameplay.items.*;

import org.newdawn.slick.Image;

public class ItemInfo {

    private Item item;
    private Image image;

    public ItemInfo(String name, String description, Image image, String type, Map map) {
        ItemInstanceKind instanceKind = ItemInstanceKind.valueOf(name.toUpperCase());
        if (type.equals("Sword")) {
            item = new Sword(instanceKind, description, map);
        } else if (type.equals("Bow")) {
            item = new Bow(instanceKind, description, map);
        } else if (type.equals("Staff")) {
            item = new Staff(instanceKind, description, map);
        } else if (type.equals("Reagent")) {
            item = new Reagent(instanceKind, description, map);
        } else if (type.equals("Armor")) {
            item = new Armor(instanceKind, description, map);
        } else if (type.equals("Arrow")) {
            item = new ArrowItem(instanceKind, description, map);
        }

        this.image = image;
    }

    public String getDescription() {
        return item.getDescription();
    }

    public Image getImage() {
        return image;
    }

    public Item getItem() {
        return item;
    }

}
