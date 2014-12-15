package core.view.gameplay;

import core.model.gameplay.skills.Skill;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.SkillInfo;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkillPanelView {

    private List<Image> imgsToDraw;
    private GameContainer gc;

    final int X = 0;
    final int Y = 1;

    int IMAGE_SIDE_LN;

    // Panel in Cartesian coordinate system
    int PANEL_RADIUS;
    int PANEL_CENTER_X;
    int PANEL_CENTER_Y;
    int[][] imgsCartesianCoordinates;

    // Panel in Polar coordinate system
    int PANEL_SIZE_DEGREES;
    int IMAGE_SECTOR_DEGREES;
    int[] imgsPolarCoordinates;


    public SkillPanelView(List<Skill> skillsToDraw, Map<SkillInstanceKind, SkillInfo> skillInfos, GameContainer gc) {
        // filling imgs to draw
        imgsToDraw = new ArrayList<>();
        for (Skill skillToDraw: skillsToDraw) {
            imgsToDraw.add(skillInfos.get(skillToDraw.getKind()).getImage());
        }

        this.gc = gc;

        IMAGE_SIDE_LN = imgsToDraw.get(0).getWidth();
        PANEL_CENTER_X = 0 + 110;
        PANEL_CENTER_Y = gc.getHeight() - 100;
        // Panel in Cartesian coordinate system
        PANEL_RADIUS = IMAGE_SIDE_LN * 2;

        // Panel in Polar coordinate system
        PANEL_SIZE_DEGREES = 360;
        IMAGE_SECTOR_DEGREES = PANEL_SIZE_DEGREES / imgsToDraw.size();
        //--

        // where draw on circle panel
        int imgPositionOnCircle = imgsToDraw.size() % 2 == 0 ? 0 : IMAGE_SIDE_LN / 2;

        imgsPolarCoordinates = new int[imgsToDraw.size()];
        for (int i = 0; i < imgsPolarCoordinates.length; i++) {
            imgsPolarCoordinates[i] = imgPositionOnCircle;
            imgPositionOnCircle += IMAGE_SECTOR_DEGREES;
        }
    }

    public void render(Graphics g) {
        int currImageCenterX;
        int currImageCenterY;

        // where really draw
        /* it's here, because we have full screen mode */
        imgsCartesianCoordinates = new int[imgsToDraw.size()][2];
        for (int i = 0; i < imgsPolarCoordinates.length; i++) {
            imgsCartesianCoordinates[i] = getImgCartesianCoordinates(imgsPolarCoordinates[i]);
        }

        for (int i = 0; i < imgsCartesianCoordinates.length; i++) {
            currImageCenterX = imgsCartesianCoordinates[i][X];
            currImageCenterY = imgsCartesianCoordinates[i][Y];

            g.drawImage(imgsToDraw.get(i), leftUpperCorner(currImageCenterX), leftUpperCorner(currImageCenterY));
        }
    }

    private int leftUpperCorner(int centerOfImage) {
        return centerOfImage - IMAGE_SIDE_LN / 2;
    }

    private int[] getImgCartesianCoordinates(int imgOnCircleAngle) {
        PANEL_CENTER_X = 0 + 110;
        PANEL_CENTER_Y = gc.getHeight() - 100;

        int cartesianX = (int) (PANEL_RADIUS *  Math.cos((float) imgOnCircleAngle / 180f * Math.PI));
        int cartesianY = (int) (PANEL_RADIUS *  Math.sin((float) imgOnCircleAngle / 180f * Math.PI));
        int[] resultPoint = new int[2];
        resultPoint[X] = PANEL_CENTER_X + cartesianX;
        resultPoint[Y] = PANEL_CENTER_Y + cartesianY;
        return resultPoint;
    }


}
