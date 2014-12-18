package core.resourcemanager;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import javafx.util.Pair;

import core.gamestates.GameState;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.*;
import core.model.gameplay.gameobjects.GameObjInstanceKind;
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
    private Map<String, MaskInfo> maskInfos;
    private Map<String, FontInfo> fontInfos;
    private Map<ItemInstanceKind, ItemInfo> itemInfos;
    private Map<SkillInstanceKind, SkillInfo> skillInfos;
    private Map<String, Image> imageInfos;
    private Map<GameObjInstanceKind, UnitInfo> unitInfos;
    private Map<GameObjInstanceKind, ObstacleInfo> obstacleInfos;
    private Map<GameObjInstanceKind, BulletInfo> bulletInfos;
    private Map<String, SoundInfo> soundInfos;

    //skills
    private Image skillFakeImage;
    private Image opacitySkillFakeImage;
    private Image cantCastImage;
    private Image opacityCantCastImage;
    private Image onCDImage;
    private Image opacityOnCDImage;

    private ResourceManager() {
        animationInfos = new HashMap<>();
        maskInfos = new HashMap<>();
        fontInfos = new HashMap<>();
        itemInfos = new HashMap<>();
        skillInfos = new LinkedHashMap<>();
        imageInfos = new HashMap<>();
        unitInfos = new HashMap<>();
        obstacleInfos = new HashMap<>();
        bulletInfos = new HashMap<>();
        soundInfos = new HashMap<>();
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
                case MENU:
                    loadMenuStart();
                    break;
                case GAMEPLAY:
                    loadGamePlay();
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
        loadObstacles(gameplayElement);
        loadBullets(gameplayElement);
        loadSounds(gameplayElement);

        setSkillFakeImage(gameplayElement);
        setOpacitySkillFakeImage(gameplayElement);
        setCantCastImage(gameplayElement);
        setOpacityCantCastImage(gameplayElement);
        setOnCDImage(gameplayElement);
        setOpacityOnCDImage(gameplayElement);

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

            String name = maskElement.getAttribute("name");
            String shape = maskElement.getAttribute("shape");
            int width = maskElement.getIntAttribute("width");
            int height = maskElement.getIntAttribute("height");
            int radius = maskElement.getIntAttribute("radius");

            maskInfos.put(name, new MaskInfo(shape, width, height, radius));
        }
    }

    private void loadItems(XMLElement gameStateElement) throws SlickException {
        XMLElement itemsElement = gameStateElement.getChildrenByName("items").get(0);
        XMLElementList itemList = itemsElement.getChildrenByName("item");
        for (int i = 0; i < itemList.size(); ++i) {
            XMLElement itemElement = itemList.get(i);

            String name = itemElement.getAttribute("name");
            ItemInstanceKind instanceKind = ItemInstanceKind.valueOf(name.toUpperCase());
            String description = itemElement.getAttribute("description");
            String path = itemElement.getAttribute("path");
            String type = itemElement.getAttribute("type");
            ArrayList<String> nameList = new ArrayList<>(Arrays.asList(itemElement.getAttributeNames()));
            Map<String, Integer> map = new HashMap<>();
            for (int j = 0; j < nameList.size(); ++j) {
                if (!nameList.get(j).equals("name") &&
                        !nameList.get(j).equals("description") &&
                        !nameList.get(j).equals("path") &&
                        !nameList.get(j).equals("type")) {
                    map.put(nameList.get(j), Integer.valueOf(itemElement.getAttribute(nameList.get(j))));
                }
            }
            itemInfos.put(instanceKind, new ItemInfo(name, description, new Image(path), type, map));
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
            ArrayList<String> nameList = new ArrayList<>(Arrays.asList(skillElement.getAttributeNames()));
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < nameList.size(); ++j) {
                if (!nameList.get(j).equals("name") &&
                        !nameList.get(j).equals("description") &&
                        !nameList.get(j).equals("path") &&
                        !nameList.get(j).equals("type")) {
                    map.put(nameList.get(j), skillElement.getAttribute(nameList.get(j)));
                }
            }


            SkillInstanceKind skillInstanceKind = SkillInstanceKind.valueOf(name.toUpperCase());
            skillInfos.put(skillInstanceKind, new SkillInfo(name, description, type, new Image(path), map, skillInstanceKind));
        }

        setOpacitiSkilllsImages(gameStateElement);
    }

    private void setOpacitiSkilllsImages(XMLElement gameStateElement) throws SlickException {
        XMLElement opacitySkillsElement = gameStateElement.getChildrenByName("opacitySkills").get(0);
        XMLElementList opSkillList = opacitySkillsElement.getChildrenByName("opskill");
        for (int i = 0; i < opSkillList.size(); ++i) {
            XMLElement skillElement = opSkillList.get(i);
            String path = skillElement.getAttribute("path");
            String name = skillElement.getAttribute("name");
            SkillInstanceKind kind = SkillInstanceKind.valueOf(name.toUpperCase());
            skillInfos.get(kind).setOpacityImage(new Image(path));
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

            fontInfos.put(name, new FontInfo(ttf));
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

            List<Pair<ItemInstanceKind, Integer>> itemsList = new ArrayList<Pair<ItemInstanceKind, Integer>>();
            XMLElement inventoryElement = unitElement.getChildrenByName("inventory").get(0);
            XMLElementList itemRecordList = inventoryElement.getChildrenByName("itemRecord");
            for (int j = 0; j < itemRecordList.size(); ++j) {
                XMLElement itemRecord = itemRecordList.get(j);

                String itemName = itemRecord.getAttribute("name");
                ItemInstanceKind instanceKind = ItemInstanceKind.valueOf(itemName.toUpperCase());
                Integer itemNumber = Integer.valueOf(itemRecord.getAttribute("number"));

                Pair<ItemInstanceKind, Integer> pair = new Pair<ItemInstanceKind, Integer>(instanceKind, itemNumber);
                itemsList.add(pair);
            }

            List<SkillInstanceKind> skillInstanceKindListList = new ArrayList<SkillInstanceKind>();
            XMLElement skillListElement = unitElement.getChildrenByName("skillList").get(0);
            XMLElementList skillList = skillListElement.getChildrenByName("skill");
            for (int j = 0; j < skillList.size(); ++j) {
                XMLElement skill = skillList.get(j);

                String skillName = skill.getAttribute("name");

                SkillInstanceKind skillInstanceKind = SkillInstanceKind.valueOf(skillName.toUpperCase());
                skillInstanceKindListList.add(skillInstanceKind);
            }

            List<Pair<ItemInstanceKind, Double>> dropList = new ArrayList<Pair<ItemInstanceKind, Double>>();
            XMLElement lootListElement = unitElement.getChildrenByName("lootList").get(0);
            XMLElementList lootList = lootListElement.getChildrenByName("loot");
            for (int j = 0; j < lootList.size(); ++j) {
                XMLElement drop = lootList.get(j);

                String itemName = drop.getAttribute("name");
                ItemInstanceKind instanceKind = ItemInstanceKind.valueOf(itemName.toUpperCase());
                Double probability = Double.valueOf(drop.getAttribute("probability"));

                Pair<ItemInstanceKind, Double> pair = new Pair<ItemInstanceKind, Double>(instanceKind, probability);
                dropList.add(pair);
            }

            unitInfos.put(GameObjInstanceKind.valueOf(unitName.toUpperCase()),
                    new UnitInfo(maskName, maximumHP, maximumMP,
                            maximumSpeed, pAttack, mAttack, pArmor, mArmor, itemsList, skillInstanceKindListList, dropList));
        }
    }

    private void loadObstacles(XMLElement gameStateElement) throws SlickXMLException {
        XMLElement obstaclesElement = gameStateElement.getChildrenByName("obstacles").get(0);
        XMLElementList obstacleList = obstaclesElement.getChildrenByName("obstacle");
        for (int i = 0; i < obstacleList.size(); ++i) {
            XMLElement obstacleElement = obstacleList.get(i);

            String obstacleName = obstacleElement.getAttribute("name");
            String maskName = obstacleElement.getAttribute("mask");

            ObstacleInfo obstacleInfo = new ObstacleInfo(maskName);

            obstacleInfos.put(GameObjInstanceKind.valueOf(obstacleName.toUpperCase()), obstacleInfo);
        }
    }

    private void loadBullets(XMLElement gameStateElement) throws SlickXMLException {
        XMLElement bulletsElement = gameStateElement.getChildrenByName("bullets").get(0);
        XMLElementList bulletList = bulletsElement.getChildrenByName("bullet");
        for (int i = 0; i < bulletList.size(); ++i) {
            XMLElement bulletElement = bulletList.get(i);

            String bulletName = bulletElement.getAttribute("name");
            String maskName = bulletElement.getAttribute("mask");

            BulletInfo bulletInfo = new BulletInfo(maskName);

            bulletInfos.put(GameObjInstanceKind.valueOf(bulletName.toUpperCase()), bulletInfo);
        }
    }

    private void loadSounds(XMLElement gameStateElement) throws SlickException {
        XMLElement soundsElement = gameStateElement.getChildrenByName("sounds").get(0);
        XMLElementList soundList = soundsElement.getChildrenByName("sound");
        for (int i = 0; i < soundList.size(); ++i) {
            XMLElement soundElement = soundList.get(i);

            String soundName = soundElement.getAttribute("name");
            Sound sound = new Sound(soundElement.getAttribute("path"));

            soundInfos.put(soundName, new SoundInfo(sound));
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

    protected Shape getMask(String name) {
        return maskInfos.get(name).getMask();
    }

    public TrueTypeFont getFont(String name) {
        return fontInfos.get(name).getFont();
    }

    public Image getItemImage(ItemInstanceKind instanceKind) {
        return itemInfos.get(instanceKind).getImage();
    }


    public Image getSkillImage(SkillInstanceKind instanceKind) {
        return skillInfos.get(instanceKind).getImageMain();
    }

    public String getItemDescription(String name) {
        return itemInfos.get(name).getDescription();
    }

    public List<Item> getItems() {
        List<ItemInfo> itemInfoList = new ArrayList<>(itemInfos.values());
        List<Item> itemList = new ArrayList<>();
        for (ItemInfo itemInfo : itemInfoList) {
            itemList.add(itemInfo.getItem());
        }
        return itemList;
    }

    public Item getItem(ItemInstanceKind instanceKind) {
        return itemInfos.get(instanceKind).getItem();
    }


    public Skill getSkill(SkillInstanceKind skillInstanceKind) {
        return skillInfos.get(skillInstanceKind).getSkill();
    }

    public Map<SkillInstanceKind, SkillInfo> getSkillInfos() {
        return skillInfos;
    }

    public UnitInfo getUnitInfo(GameObjInstanceKind type) {
        return unitInfos.get(type);
    }

    public ObstacleInfo getObstacleInfo(GameObjInstanceKind type) {
        return obstacleInfos.get(type);
    }

    public BulletInfo getBulletInfo(GameObjInstanceKind type) {
        return bulletInfos.get(type);
    }

    public Sound getSound(String name) {
        return soundInfos.get(name).getSound();
    }

    public Image getSkillFakeImage() {
        return skillFakeImage;
    }


        private void setSkillFakeImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(0).getAttribute("path");
        skillFakeImage = new Image(path);
    }

    public Image getOpacitySkillFakeImage() {
        return opacitySkillFakeImage;
    }

    public void setOpacitySkillFakeImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(1).getAttribute("path");
        opacitySkillFakeImage = new Image(path);
    }

    public Image getOpacityCantCastImage() {
        return opacityCantCastImage;
    }


    public void setCantCastImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(3).getAttribute("path");
        opacityCantCastImage = new Image(path);
    }

    public void setOpacityCantCastImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(2).getAttribute("path");
        cantCastImage = new Image(path);
    }

    public Image getCantCastImage() {
        return cantCastImage;
    }


    private void setOnCDImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(4).getAttribute("path");
        onCDImage = new Image(path);
    }

    private void setOpacityOnCDImage(XMLElement gameStateElement) throws SlickException {
        XMLElement fakesEl = gameStateElement.getChildrenByName("skillFakes").get(0);
        XMLElementList fakes = fakesEl.getChildrenByName("fake");
        String path = fakes.get(5).getAttribute("path");
        opacityOnCDImage = new Image(path);
    }

    public Image getOnCDImage() {
        return onCDImage;
    }

    public Image getOpacityOnCDImage() {
        return opacityOnCDImage;
    }
}