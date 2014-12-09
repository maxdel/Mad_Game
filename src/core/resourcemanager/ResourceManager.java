package core.resourcemanager;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import core.GameState;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.skills.Skill;
import core.model.gameplay.gameobjects.GameObjectSolidType;
import core.model.gameplay.gameobjects.Unit;
import javafx.util.Pair;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.xml.SlickXMLException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class ResourceManager {

    private static ResourceManager instance;

    private final String xmlFilePath = "res/resources.xml";

    private Map<String, AnimationInfo> animationInfos;
    private Map<GameObjectSolidType, MaskInfo> maskInfos;
    private Map<String, FontInfo> fontInfos;
    private Map<String, ItemInfo> itemInfos;
    private Map<String, SkillInfo> skillInfos;
    private Map<String, Image> imageInfos;
    private Map<GameObjectSolidType, UnitInfo> unitInfos;

    private ResourceManager() {
        animationInfos = new HashMap<String, AnimationInfo>();
        maskInfos = new HashMap<GameObjectSolidType, MaskInfo>();
        fontInfos = new HashMap<String, FontInfo>();
        itemInfos = new HashMap<String, ItemInfo>();
        skillInfos = new HashMap<String, SkillInfo>();
        imageInfos = new HashMap<String, Image>();
        unitInfos = new HashMap<GameObjectSolidType, UnitInfo>();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload() {
        /*animationInfos = new HashMap<String, AnimationInfo>();
        maskInfos = new HashMap<String, MaskInfo>();
        fontInfos = new HashMap<String, FontInfo>();
        itemInfos = new HashMap<String, ItemInfo>();
        skillInfos = new HashMap<String, SkillInfo>();
        imageInfos = new HashMap<String, Image>();*/
    }

    private void loadGamePlay() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement gameplayElement = root.getChildrenByName("gameplay").get(0);

        loadAnimations(gameplayElement);
        loadMasks(gameplayElement);
        loadItems(gameplayElement);
        loadSkills(gameplayElement);
        loadImages(gameplayElement);
        loadFonts(gameplayElement);
        loadUnits(gameplayElement);

        in.close();
    }

    private void loadMenuStart() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement manustartElement = root.getChildrenByName("menustart").get(0);

        loadFonts(manustartElement);

        in.close();
    }

    private void loadMenuPause() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement menupauseElement = root.getChildrenByName("menupause").get(0);

        loadFonts(menupauseElement);

        in.close();
    }

    private void loadAnimations(XMLElement gameStateElement) throws SlickException {
        XMLElement animationsElement = gameStateElement.getChildrenByName("animations").get(0);
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
    }

    private void loadMasks(XMLElement gameStateElement) throws SlickXMLException {
        XMLElement masksElement = gameStateElement.getChildrenByName("masks").get(0);
        XMLElementList maskList = masksElement.getChildrenByName("mask");
        for (int i = 0; i < maskList.size(); ++i) {
            XMLElement maskElement = maskList.get(i);

            GameObjectSolidType type = GameObjectSolidType.valueOf(maskElement.getAttribute("name").toUpperCase());
            String shape = maskElement.getAttribute("shape");
            int width = maskElement.getIntAttribute("width");
            int height = maskElement.getIntAttribute("height");
            int radius = maskElement.getIntAttribute("radius");

            maskInfos.put(type, new MaskInfo(shape, width, height, radius));
        }
    }

    private void loadItems(XMLElement gameStateElement) throws SlickException {
        XMLElement itemsElement = gameStateElement.getChildrenByName("items").get(0);
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
    }

    private void loadSkills(XMLElement gameStateElement) throws SlickException {
        XMLElement skillsElement = gameStateElement.getChildrenByName("skills").get(0);
        XMLElementList skillList = skillsElement.getChildrenByName("skill");
        for (int i = 0; i < skillList.size(); ++i) {
            XMLElement skillElement = skillList.get(i);

            String name = skillElement.getAttribute("name");
            String description = skillElement.getAttribute("description");
            String path = skillElement.getAttribute("path");
            String type = skillElement.getAttribute("type");
            ArrayList<String> nameList = new ArrayList<String>(Arrays.asList(skillElement.getAttributeNames()));
            Map<String, String> map = new HashMap<String, String>();
            for (int j = 0; j < nameList.size(); ++j) {
                if (!nameList.get(j).equals("name") &&
                        !nameList.get(j).equals("description") &&
                        !nameList.get(j).equals("path") &&
                        !nameList.get(j).equals("type")) {
                    map.put(nameList.get(j), skillElement.getAttribute(nameList.get(j)));
                }
            }
            skillInfos.put(name, new SkillInfo(name, description, type, new Image(path), map));
        }
    }

    private void loadImages(XMLElement gameStateElement) throws SlickException {
        XMLElement imagesElement = gameStateElement.getChildrenByName("images").get(0);
        XMLElementList imageList = imagesElement.getChildrenByName("image");
        for (int i = 0; i < imageList.size(); ++i) {
            XMLElement imageElement = imageList.get(i);

            String name = imageElement.getAttribute("name");
            String path = imageElement.getAttribute("path");
            imageInfos.put(name, new Image(path));
        }
    }

    private void loadFonts(XMLElement gameStateElement) throws SlickXMLException {
        XMLElement fontsElement = gameStateElement.getChildrenByName("fonts").get(0);
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
    }

    private void loadUnits(XMLElement gameStateElement) throws SlickXMLException {
        XMLElement unitsElement = gameStateElement.getChildrenByName("units").get(0);
        XMLElementList unitList = unitsElement.getChildrenByName("unit");
        for (int i = 0; i < unitList.size(); ++i) {
            XMLElement unitElement = unitList.get(i);

            String unitName = unitElement.getAttribute("name");
            String maskName = unitElement.getAttribute("mask");

            XMLElement attributeElement = unitElement.getChildrenByName("attribute").get(0);

            double maximumHP = Double.parseDouble(attributeElement.getAttribute("hp"));
            double maximumMP = Double.parseDouble(attributeElement.getAttribute("mp"));
            double maximumSpeed = Double.parseDouble(attributeElement.getAttribute("maximumSpeed"));
            double pAttack = Double.parseDouble(attributeElement.getAttribute("pAttack"));
            double mAttack = Double.parseDouble(attributeElement.getAttribute("mAttack"));
            double pArmor = Double.parseDouble(attributeElement.getAttribute("pArmor"));
            double mArmor = Double.parseDouble(attributeElement.getAttribute("mArmor"));

            List<Pair<String, Integer>> itemsList = new ArrayList<Pair<String, Integer>>();
            XMLElement inventoryElement = unitElement.getChildrenByName("inventory").get(0);
            XMLElementList itemRecordList = inventoryElement.getChildrenByName("itemRecord");
            for (int j = 0; j < itemRecordList.size(); ++j) {
                XMLElement itemRecord = itemRecordList.get(j);

                String itemName = itemRecord.getAttribute("name");
                Integer itemNumber = Integer.valueOf(itemRecord.getAttribute("number"));

                Pair<String, Integer> pair = new Pair<String, Integer>(itemName, itemNumber);
                itemsList.add(pair);
            }

            List<String> skillNameList = new ArrayList<String>();
            XMLElement skillListElement = unitElement.getChildrenByName("skillList").get(0);
            XMLElementList skillList = skillListElement.getChildrenByName("skill");
            for (int j = 0; j < skillList.size(); ++j) {
                XMLElement skill = skillList.get(j);

                String skillName = skill.getAttribute("name");

                skillNameList.add(skillName);
            }

            List<Pair<String, Double>> dropList = new ArrayList<Pair<String, Double>>();
            XMLElement lootListElement = unitElement.getChildrenByName("lootList").get(0);
            XMLElementList lootList = lootListElement.getChildrenByName("loot");
            for (int j = 0; j < lootList.size(); ++j) {
                XMLElement drop = lootList.get(j);

                String itemName = drop.getAttribute("name");
                Double probability = Double.valueOf(drop.getAttribute("probability"));

                Pair<String, Double> pair = new Pair<String, Double>(itemName, probability);
                dropList.add(pair);
            }

            unitInfos.put(GameObjectSolidType.valueOf(unitName.toUpperCase()),
                    new UnitInfo(GameObjectSolidType.valueOf(unitName.toUpperCase()), maskName, maximumHP, maximumMP,
                            maximumSpeed, pAttack, mAttack, pArmor, mArmor, itemsList, skillNameList, dropList));
        }
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

    public Shape getMask(GameObjectSolidType type) {
        return maskInfos.get(type).getMask();
    }

    public TrueTypeFont getFont(String name) {
        return fontInfos.get(name).getFont();
    }

    public Image getItemImage(String name) {
        return itemInfos.get(name).getImage();
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

    public Item getItem(String itemName) {
        return itemInfos.get(itemName).getItem();
    }

    public Skill getSkill(Unit owner, String name) {
        return skillInfos.get(name).getSkill(owner);
    }

    public UnitInfo getUnitInfo(GameObjectSolidType type) {
        return unitInfos.get(type);
    }

}