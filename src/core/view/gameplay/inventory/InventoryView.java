package core.view.gameplay.inventory;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.ResourceManager;
import core.model.gameplay.inventory.Inventory;
import core.model.gameplay.inventory.Item;

import java.util.Iterator;

public class InventoryView {

    private Inventory inventory;
    private boolean isActive;
    private int activeItemX;
    private int activeItemY;
    private int x;
    private int y;
    private final int width = 240;
    private final int height = 180;
    private final int margin = 10;
    private final int padding = 10;
    private final int itemWidth = 32;
    private final int itemHeight = 32;
    private final int spacing = 4;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        isActive = false;
        activeItemX = 0;
        activeItemY = 0;
        x = 0;
        y = 0;
    }

    public void render(Graphics g, int viewWidth, int viewHeight) {
        if (isActive) {
            g.setColor(Color.gray);

            x = viewWidth - width - margin;
            y = viewHeight - height - margin;
            g.drawRoundRect(x, y, width, height, 5);
            Iterator<Item> it = inventory.getItems().iterator();
            for (int j = 0; j < (height - 2 * padding - spacing) / (itemHeight + spacing); ++j) {
                for (int i = 0; i < (width - 2 * padding - spacing) / (itemWidth + spacing); ++i) {
                    if (it.hasNext()) {
                        Item item = it.next();
                        g.drawImage(ResourceManager.getInstance().getItemImage(item.getName()),
                                x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                        if (i == activeItemX && j == activeItemY) {
                            g.drawImage(ResourceManager.getInstance().getItemImage("Selected item"),
                                    x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                            g.drawString("Name: " + item.getName() + "\nDescription: " + item.getDescription(),
                                    x, y - 48);
                        } else {
                            g.drawRect(x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing),
                                    itemWidth, itemHeight);
                        }
                    }
                }
            }

            g.setColor(Color.white);
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void selectRight() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        int maximumHeightIndex = (height - 2 * padding - spacing) / (itemHeight + spacing) - 1;
        if (activeItemX < maximumWidthIndex &&
                activeItemY * (maximumWidthIndex + 1) + (activeItemX + 1) < inventory.getItems().size()) {
            activeItemX++;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1)  < inventory.getItems().size()) {
            activeItemX = 0;
            activeItemY++;
        }
    }

    public void selectBottom() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        int maximumHeightIndex = (height - 2 * padding - spacing) / (itemHeight + spacing) - 1;
        if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1) + activeItemX < inventory.getItems().size()) {
            activeItemY++;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1) < inventory.getItems().size()) {
            activeItemX = inventory.getItems().size() - 1 - (activeItemY + 1) * (maximumWidthIndex + 1);
            activeItemY++;
        }
    }

    public void selectLeft() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        if (activeItemX > 0) {
            activeItemX--;
        } else if (activeItemY > 0) {
            activeItemX = maximumWidthIndex;
            activeItemY--;
        }
    }

    public void selectTop() {
        if (activeItemY > 0) {
            activeItemY--;
        }
    }

}