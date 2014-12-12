package core.resourcemanager;

import core.model.gameplay.items.*;
import org.newdawn.slick.Image;

import java.util.Map;

public class ItemInfo {

    private Item item;
    private Image image;

    public ItemInfo(String name, String description, Image image, String type, Map map) {
        if (type.equals("Sword")) {
            item = new Sword(name, description, map);
        } else if (type.equals("Bow")) {
            item = new Bow(name, description, map);
        } else if (type.equals("Staff")) {
            item = new Staff(name, description, map);
        } else if (type.equals("Reagent")) {
            item = new Reagent(name, description, map);
        } else if (type.equals("Armor")) {
            item = new Armor(name, description, map);
        } else if (type.equals("Arrow")) {
            item = new ArrowItem(name, description, map);
        }

        this.image = image;
    }

    public String getName() {
        return item.getName();
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
