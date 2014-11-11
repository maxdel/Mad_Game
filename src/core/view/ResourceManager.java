package core.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

import core.GameState;

public class ResourceManager {

    private static ResourceManager instance;

    private final String xmlFilePath = "res/resources.xml";

    private Map<String, AnimationInfo> animationInfos;

    private ResourceManager() {
        animationInfos = new HashMap<String, AnimationInfo>();
    }

    public static ResourceManager getInstance() throws SlickException {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void load(GameState gameState) throws SlickException {
        try {
            switch (gameState) {
                case STARTMENU:

                    break;
                case GAMEPLAY:
                    loadGamePlay();
                    break;
                case PAUSEMENU:

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

        in.close();
    }

    public void unload() {
        animationInfos = new HashMap<String, AnimationInfo>();
    }

    public Animation getAnimation(String name) {
        return animationInfos.get(name).getAnimation();
    }

    public double getSpeedCoef(String name) {
        return animationInfos.get(name).getSpeedCoef();
    }

    private static class AnimationInfo {

        private Animation animation;
        private double speedCoef;

        public AnimationInfo(Animation animation, double speedCoef) {
            this.animation = animation;
            this.speedCoef = speedCoef;
        }

        public AnimationInfo(Animation animation) {
            this(animation, 0);
        }

        public Animation getAnimation() {
            return animation;
        }

        public double getSpeedCoef() {
            return speedCoef;
        }

    }
}