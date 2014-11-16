package core.view.gameplay.inventory;

import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.ResourceManager;
import core.model.gameplay.inventory.Inventory;
import core.model.gameplay.inventory.ItemRecord;

public class InventoryView {

    private Inventory inventory;
    private boolean isActive;
    private int activeItemX;
    private int activeItemY;
    private int x;
    private int y;
    private int scroll;
    private final int width = 110;
    private final int height = 60;
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
        scroll = 0;
    }

    public void render(Graphics g, int viewWidth, int viewHeight) {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        if (inventory.getItemRecords().size() - 1 < (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX) {
            selectLeft();
        }

        if (isActive) {
            g.setColor(Color.gray);

            x = viewWidth - width - margin;
            y = viewHeight - height - margin;
            g.drawRoundRect(x, y, width, height, 5);
            Iterator<ItemRecord> it = inventory.getItemRecords().iterator();
            for (int i = 0; i < scroll; ++i) {
                for (int j = 0; j < (width - 2 * padding - spacing) / (itemWidth + spacing); ++j) {
                    if (it.hasNext()) {
                        it.next();
                    }
                }
            }
            for (int j = 0; j < (height - 2 * padding - spacing) / (itemHeight + spacing); ++j) {
                for (int i = 0; i < (width - 2 * padding - spacing) / (itemWidth + spacing); ++i) {
                    if (it.hasNext()) {
                        ItemRecord itemRecord = it.next();
                        g.drawImage(ResourceManager.getInstance().getItemImage(itemRecord.getName()),
                                x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                        if (i == activeItemX && j == activeItemY) {
                            g.drawImage(ResourceManager.getInstance().getImage("Selected item frame"),
                                    x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                            g.drawString("Name: " + itemRecord.getName() + "\nDescription: " + itemRecord.getDescription(),
                                    x, y - 48);
                        } else {
                            g.drawRect(x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing),
                                    itemWidth, itemHeight);
                        }
                        g.drawString(String.valueOf(itemRecord.getNumber()), x + padding + i * (itemWidth + spacing) + 22,
                                y + padding + j * (itemHeight + spacing) + 16);
                    }
                }
            }

            g.setColor(Color.white);
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int selectRight() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        int maximumHeightIndex = (height - 2 * padding - spacing) / (itemHeight + spacing) - 1;
        if (activeItemX < maximumWidthIndex &&
                activeItemY * (maximumWidthIndex + 1) + (activeItemX + 1) < inventory.getItemRecords().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1)  < inventory.getItemRecords().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX = 0;
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1)  < inventory.getItemRecords().size() -
                scroll * (maximumWidthIndex + 1)) {
            scroll++;
            activeItemX = 0;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        }
        return -1;
    }

    public int selectBottom() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        int maximumHeightIndex = (height - 2 * padding - spacing) / (itemHeight + spacing) - 1;
        if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1) + activeItemX < inventory.getItemRecords().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1) < inventory.getItemRecords().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX = inventory.getItemRecords().size() -
                    scroll * (maximumWidthIndex + 1) - 1 - (activeItemY + 1) * (maximumWidthIndex + 1);
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1) + activeItemX < inventory.getItemRecords().size() -
                scroll * (maximumWidthIndex + 1)) {
            scroll++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1) < inventory.getItemRecords().size() -
                scroll * (maximumWidthIndex + 1)) {
            activeItemX = inventory.getItemRecords().size() -
                    scroll * (maximumWidthIndex + 1) - 1 - (activeItemY + 1) * (maximumWidthIndex + 1);
            scroll++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        }
        return -1;
    }

    public int selectLeft() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        if (activeItemX > 0) {
            activeItemX--;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (activeItemY > 0) {
            activeItemX = maximumWidthIndex;
            activeItemY--;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (scroll > 0) {
            activeItemX = maximumWidthIndex;
            scroll--;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        }
        return -1;
    }

    public int selectTop() {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        if (activeItemY > 0) {
            activeItemY--;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (scroll > 0) {
            scroll--;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        }
        return -1;
    }

}