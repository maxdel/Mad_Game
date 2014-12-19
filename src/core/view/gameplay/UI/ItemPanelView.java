package core.view.gameplay.ui;

import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.resourcemanager.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemPanelView {

    private SpinnerView weaponSpinner;
    private SpinnerView armorSpinner;

    private Class currentWeapon;
    private ItemInstanceKind currentArmor;

    public ItemPanelView(GameContainer gc) {
        this.armorSpinner = new SpinnerView(40,
                Arrays.asList(ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake")), 3, Math.PI, 200);
        this.weaponSpinner = new SpinnerView(40,
                Arrays.asList(ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake")), 3, Math.PI, 200);
    }

    public void update(int delta) {
        if (Hero.getInstance().getInventory().isItemClassDressed(Sword.class) && currentWeapon != Sword.class) {
            if (currentWeapon == Bow.class) {
                weaponSpinner.spinForward();
            } else if (currentWeapon == Staff.class) {
                weaponSpinner.spinBackward();
            }
            currentWeapon = Sword.class;
        } else if (Hero.getInstance().getInventory().isItemClassDressed(Bow.class) && currentWeapon != Bow.class) {
            if (currentWeapon == Sword.class) {
                weaponSpinner.spinBackward();
            } else if (currentWeapon == Staff.class) {
                weaponSpinner.spinForward();
            }
            currentWeapon = Bow.class;
        } else if (Hero.getInstance().getInventory().isItemClassDressed(Staff.class) && currentWeapon != Staff.class) {
            if (currentWeapon == Sword.class) {
                weaponSpinner.spinForward();
            } else if (currentWeapon == Bow.class) {
                weaponSpinner.spinBackward();
            }
            currentWeapon = Staff.class;
        }
        weaponSpinner.setImages(getWeaponImages());
        weaponSpinner.update(delta);

        if (Hero.getInstance().getInventory().isItemDressed(ItemDB.getInstance().getItem(ItemInstanceKind.LIGHT_ARMOR)) &&
                currentArmor != ItemInstanceKind.LIGHT_ARMOR) {
            if (currentArmor == ItemInstanceKind.HEAVY_ARMOR) {
                armorSpinner.spinForward();
            } else if (currentArmor == ItemInstanceKind.ROBE_OF_MAGIC) {
                armorSpinner.spinBackward();
            }
            currentArmor = ItemInstanceKind.LIGHT_ARMOR;
        } else if (Hero.getInstance().getInventory().isItemDressed(ItemDB.getInstance().getItem(ItemInstanceKind.HEAVY_ARMOR)) &&
                currentArmor != ItemInstanceKind.HEAVY_ARMOR) {
            if (currentArmor == ItemInstanceKind.LIGHT_ARMOR) {
                armorSpinner.spinBackward();
            } else if (currentArmor == ItemInstanceKind.ROBE_OF_MAGIC) {
                armorSpinner.spinForward();
            }
            currentArmor = ItemInstanceKind.HEAVY_ARMOR;
        } else if (Hero.getInstance().getInventory().isItemDressed(ItemDB.getInstance().getItem(ItemInstanceKind.ROBE_OF_MAGIC)) &&
                currentArmor != ItemInstanceKind.ROBE_OF_MAGIC) {
            if (currentArmor == ItemInstanceKind.LIGHT_ARMOR) {
                armorSpinner.spinForward();
            } else if (currentArmor == ItemInstanceKind.HEAVY_ARMOR) {
                armorSpinner.spinBackward();
            }
            currentArmor = ItemInstanceKind.ROBE_OF_MAGIC;
        }
        armorSpinner.setImages(getArmorImages());
        armorSpinner.update(delta);
    }

    public void render(GameContainer gc, Graphics g) {
        weaponSpinner.render(gc.getWidth() - 70, gc.getHeight() - 70);
        armorSpinner.render(gc.getWidth() - 70, gc.getHeight() - 200);
    }

    private List<Image> getWeaponImages() {
        List<Image> imageList = new ArrayList<>();
        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.SWORD))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.SWORD));
        } else if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.STRONG_SWORD))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.STRONG_SWORD));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.BOW))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.BOW));
        } else if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.STRONG_BOW))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.STRONG_BOW));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.STAFF))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.STAFF));
        } else if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.STRONG_STAFF))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.STRONG_STAFF));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        return imageList;
    }

    private List<Image> getArmorImages() {
        List<Image> imageList = new ArrayList<>();
        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.LIGHT_ARMOR))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.LIGHT_ARMOR));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.HEAVY_ARMOR))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.HEAVY_ARMOR));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        if (Hero.getInstance().getInventory().isItemExists(ItemDB.getInstance().getItem(ItemInstanceKind.ROBE_OF_MAGIC))) {
            imageList.add(ResourceManager.getInstance().getItemImage(ItemInstanceKind.ROBE_OF_MAGIC));
        } else {
            imageList.add(ResourceManager.getInstance().getImage("skill fake"));
        }

        return imageList;
    }

}