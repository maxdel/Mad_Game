package core.resource_manager;

import core.model.gameplay.items.Item;
import org.newdawn.slick.Image;

import java.util.Map;

public class ItemInfo {

    private Item item;
    private Image image;

    public ItemInfo(String name, String description, Image image, String type, Map map) {
        item = new Item(name, description, type, map);
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
