package core.resource_manager;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import core.GameState;
import core.model.gameplay.items.Item;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class ResourceManager {

    private static ResourceManager instance;

    private final String xmlFilePath = "res/resources.xml";

    private Map<String, AnimationInfo> animationInfos;
    private Map<String, MaskInfo> maskInfos;
    private Map<String, FontInfo> fontInfos;
    private Map<String, ItemInfo> itemInfos;
    private Map<String, Image> imageInfos;

    private ResourceManager() {
        animationInfos = new HashMap<String, AnimationInfo>();
        maskInfos = new HashMap<String, MaskInfo>();
        fontInfos = new HashMap<String, FontInfo>();
        itemInfos = new HashMap<String, ItemInfo>();
        imageInfos = new HashMap<String, Image>();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void load(GameState gameState) throws SlickException {
        try {
            switch (gameState) {
                case MENUSTART:
                    loadMenuStart();
                    break;
                case GAMEPLAY:
                    loadGamePlay();
                    break;
                case MENUPAUSE:
                    loadMenuPause();
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGamePlay() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement gameplayElement = root.getChildrenByName("gameplay").get(0);

        // gameplay/animations
        XMLElement animationsElement = gameplayElement.getChildrenByName("animations").get(0);
        XMLElementList animationList = animationsElement.getChildrenByName("animation");
        for (int i = 0; i < animationList.size(); ++i) {
            XMLElement animationElement = animationList.get(i);

            String name = animationElement.getAttribute("name");
            String path = animationElement.getAttribute("path");
            boolean isImage = Boolean.valueOf(animationElement.getAttribute("isImage"));

            if (isImage) {
                Image image = new Image(path);
                SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
                Animation animation = new Animation(spriteSheet, 1);

                AnimationInfo animationInfo = new AnimationInfo(animation);
                animationInfos.put(name, animationInfo);
            } else {
                int frameWidth = Integer.valueOf(animationElement.getAttribute("frameWidth"));
                int frameHeight = Integer.valueOf(animationElement.getAttribute("frameHeight"));
                double speedCoef = Double.valueOf(animationElement.getAttribute("speedCoef"));

                SpriteSheet spriteSheet = new SpriteSheet(path, frameWidth, frameHeight);
                Animation animation = new Animation(spriteSheet, 1);

                AnimationInfo animationInfo = new AnimationInfo(animation, speedCoef);
                animationInfos.put(name, animationInfo);
            }
        }

        // gameplay/masks
        XMLElement masksElement = gameplayElement.getChildrenByName("masks").get(0);
        XMLElementList maskList = masksElement.getChildrenByName("mask");
        for (int i = 0; i < maskList.size(); ++i) {
            XMLElement maskElement = maskList.get(i);

            String name = maskElement.getAttribute("name");
            int width = maskElement.getIntAttribute("width");
            int height = maskElement.getIntAttribute("height");
            int radius = maskElement.getIntAttribute("radius");

            maskInfos.put(name, new MaskInfo(width, height, radius));
        }

        // gameplay/items
        XMLElement itemsElement = gameplayElement.getChildrenByName("items").get(0);
        XMLElementList itemList = itemsElement.getChildrenByName("item");
        for (int i = 0; i < itemList.size(); ++i) {
            XMLElement itemElement = itemList.get(i);

            String name = itemElement.getAttribute("name");
            String description = itemElement.getAttribute("description");
            String path = itemElement.getAttribute("path");
            String type = itemElement.getAttribute("type");
            ArrayList<String> nameList = new ArrayList<String>(Arrays.asList(itemElement.getAttributeNames()));
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (int j = 0; j < nameList.size(); ++j) {
                if (!nameList.get(j).equals("name") &&
                        !nameList.get(j).equals("description") &&
                        !nameList.get(j).equals("path") &&
                        !nameList.get(j).equals("type")) {
                    map.put(nameList.get(j), Integer.valueOf(itemElement.getAttribute(nameList.get(j))));
                }
            }

            itemInfos.put(name, new ItemInfo(name, description, new Image(path), type, map));
        }

        // gameplay/images
        XMLElement imagesElement = gameplayElement.getChildrenByName("images").get(0);
        XMLElementList imageList = imagesElement.getChildrenByName("image");
        for (int i = 0; i < imageList.size(); ++i) {
            XMLElement imageElement = imageList.get(i);

            String name = imageElement.getAttribute("name");
            String path = imageElement.getAttribute("path");
            imageInfos.put(name, new Image(path));
        }

        // gameplay/fonts
        XMLElement fontsElement = gameplayElement.getChildrenByName("fonts").get(0);
        XMLElementList fontList = fontsElement.getChildrenByName("font");
        for (int i = 0; i < fontList.size(); ++i) {
            XMLElement fontElement = fontList.get(i);

            String name = fontElement.getAttribute("name");
            String fontName = fontElement.getAttribute("fontName");
            int size = fontElement.getIntAttribute("size");
            boolean isBold = fontElement.getBooleanAttribute("isBold");

            int fontStyle = isBold == true ? Font.BOLD : Font.PLAIN;
            Font font = new Font(fontName, fontStyle, size);
            TrueTypeFont ttf = new TrueTypeFont(font, true);

            fontInfos.put(name, new FontInfo(font, ttf));
        }

        in.close();
    }

    private void loadMenuStart() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement manustartElement = root.getChildrenByName("menustart").get(0);

        // menustart/fonts
        XMLElement fontsElement = manustartElement.getChildrenByName("fonts").get(0);
        XMLElementList fontList = fontsElement.getChildrenByName("font");
        for (int i = 0; i < fontList.size(); ++i) {
            XMLElement fontElement = fontList.get(i);

            String name = fontElement.getAttribute("name");
            String fontName = fontElement.getAttribute("fontName");
            int size = fontElement.getIntAttribute("size");
            boolean isBold = fontElement.getBooleanAttribute("isBold");

            int fontStyle = isBold == true ? Font.BOLD : Font.PLAIN;
            Font font = new Font(fontName, fontStyle, size);
            TrueTypeFont ttf = new TrueTypeFont(font, true);

            fontInfos.put(name, new FontInfo(font, ttf));
        }

        in.close();
    }

    private void loadMenuPause() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement menupauseElement = root.getChildrenByName("menupause").get(0);

        // menustart/fonts
        XMLElement fontsElement = menupauseElement.getChildrenByName("fonts").get(0);
        XMLElementList fontList = fontsElement.getChildrenByName("font");
        for (int i = 0; i < fontList.size(); ++i) {
            XMLElement fontElement = fontList.get(i);

            String name = fontElement.getAttribute("name");
            String fontName = fontElement.getAttribute("fontName");
            int size = fontElement.getIntAttribute("size");
            boolean isBold = fontElement.getBooleanAttribute("isBold");

            int fontStyle = isBold == true ? Font.BOLD : Font.PLAIN;
            Font font = new Font(fontName, fontStyle, size);
            TrueTypeFont ttf = new TrueTypeFont(font, true);

            fontInfos.put(name, new FontInfo(font, ttf));
        }

        in.close();
    }

    public void unload() {
        animationInfos = new HashMap<String, AnimationInfo>();
    }

    // GETTERS
    public Image getImage(String name) {
        return imageInfos.get(name);
    }

    public Animation getAnimation(String name) {
        return animationInfos.get(name).getAnimation();
    }

    public double getSpeedCoef(String name) {
        return animationInfos.get(name).getSpeedCoef();
    }

    public int getMaskWidth(String name) {
        return maskInfos.get(name).getWidth();
    }

    public int getMaskHeight(String name) {
        return maskInfos.get(name).getHeight();
    }

    public int getMaskRadius(String name) {
        return maskInfos.get(name).getRadius();
    }

    public TrueTypeFont getFont(String name) {
        return fontInfos.get(name).getFont();
    }

    public Image getItemImage(String name) {
        return itemInfos.get(name).getImage();
    }

    public String getItemName(String name) {
        return itemInfos.get(name).getName();
    }

    public String getItemDescription(String name) {
        return itemInfos.get(name).getDescription();
    }

    public List<Item> getItems() {
        List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>(itemInfos.values());
        List<Item> itemList = new ArrayList<Item>();
        for (ItemInfo itemInfo : itemInfoList) {
            itemList.add(itemInfo.getItem());
        }
        return itemList;
    }

}