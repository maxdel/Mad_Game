package core.view.gameplay;

import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.ItemRecord;

public class InventoryView {

    private Inventory inventory;
    private boolean isActive;
    private int activeItemX;
    private int activeItemY;
    private int x;
    private int y;
    private int scroll;
    private final int width = 240;
    private final int height = 180;
    private final int margin = 10;
    private final int padding = 10;
    private final int itemWidth = 32;
    private final int itemHeight = 32;
    private final int spacing = 4;
    private TrueTypeFont fontItem;
    private TrueTypeFont fontDescription;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        isActive = false;
        activeItemX = 0;
        activeItemY = 0;
        x = 0;
        y = 0;
        scroll = 0;
        fontItem = ResourceManager.getInstance().getFont("itemfont");
        fontDescription = ResourceManager.getInstance().getFont("descfont");
    }

    public void render(Graphics g, int viewWidth, int viewHeight) {
        int maximumWidthIndex = (width - 2 * padding - spacing) / (itemWidth + spacing) - 1;
        if (inventory.getExistedItems().size() - 1 < (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX) {
            selectLeft();
        }

        if (isActive) {
            g.setColor(Color.lightGray);

            x = viewWidth - width - margin;
            y = viewHeight - height - margin;
            g.fillRoundRect(x, y, width, height, 5);
            g.setColor(Color.gray);
            Iterator<ItemRecord> it = inventory.getExistedItems().iterator();
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
                        g.drawImage(ResourceManager.getInstance().getItemImage(itemRecord.getItem().getInstanceKind()),
                                x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                        if (itemRecord.isMarked()) {
                            g.drawImage(ResourceManager.getInstance().getImage("Dressed item frame"),
                                    x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                        }
                        if (i == activeItemX && j == activeItemY) {
                            g.drawImage(ResourceManager.getInstance().getImage("Selected item frame"),
                                    x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing));
                            g.setColor(Color.black);
                            fontDescription.drawString(x, y - 42, "Name: " + itemRecord.getItem().getInstanceKind().toString(), Color.black);
                            fontDescription.drawString(x, y - 24, "Description: " + itemRecord.getDescription(), Color.black);
                            g.setColor(Color.gray);
                        } else {
                            g.drawRect(x + padding + i * (itemWidth + spacing), y + padding + j * (itemHeight + spacing),
                                    itemWidth, itemHeight);
                        }
                        fontItem.drawString(x + padding + i * (itemWidth + spacing) + 30 - fontItem.getWidth(String.valueOf(itemRecord.getNumber())),
                                y + padding + j * (itemHeight + spacing) + 16, String.valueOf(itemRecord.getNumber()), Color.black);
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
                activeItemY * (maximumWidthIndex + 1) + (activeItemX + 1) < inventory.getExistedItems().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1)  < inventory.getExistedItems().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX = 0;
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1)  < inventory.getExistedItems().size() -
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
                (activeItemY + 1) * (maximumWidthIndex + 1) + activeItemX < inventory.getExistedItems().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if (activeItemY < maximumHeightIndex &&
                (activeItemY + 1) * (maximumWidthIndex + 1) < inventory.getExistedItems().size() -
                        scroll * (maximumWidthIndex + 1)) {
            activeItemX = inventory.getExistedItems().size() -
                    scroll * (maximumWidthIndex + 1) - 1 - (activeItemY + 1) * (maximumWidthIndex + 1);
            activeItemY++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1) + activeItemX < inventory.getExistedItems().size() -
                scroll * (maximumWidthIndex + 1)) {
            scroll++;
            return (activeItemY + scroll) * (maximumWidthIndex + 1) + activeItemX;
        } else if ((activeItemY + 1) * (maximumWidthIndex + 1) < inventory.getExistedItems().size() -
                scroll * (maximumWidthIndex + 1)) {
            activeItemX = inventory.getExistedItems().size() -
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