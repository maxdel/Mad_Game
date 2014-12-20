package core.view.gameplay.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.model.gameplay.gameobjects.Hero;
import org.newdawn.slick.*;

import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.NPC;
import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;

public class NPCView extends UnitView {

    private TrueTypeFont replicaFont = ResourceManager.getInstance().getFont("replica_font");
    private Image selectedNPC;

    public NPCView(GameObject npc) {
        super(npc);
        animation = ResourceManager.getInstance().getAnimation("wanderer");
        selectedNPC = ResourceManager.getInstance().getImage("selected npc");
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        super.render(g, camera);

        NPC npc = (NPC) gameObject;
        if (Hero.getInstance().getSelectedNPC() == gameObject) {
            rotate(g, camera, true);
            selectedNPC.draw((float) (gameObject.getX() - selectedNPC.getWidth() / 2 - camera.getX()),
                    (float) (gameObject.getY() - selectedNPC.getHeight() / 2 - camera.getY()));
            rotate(g, camera, false);
        }
        if (npc.isActive()) {
            drawReplica(g, camera, npc.getCurrentReplica(), Color.darkGray, Color.lightGray, Color.black);
        }
    }

    private void drawReplica(Graphics g, Camera camera, String text, Color windowBorderColor, Color windowColor, Color textColor) {
        double x;
        double y;
        x = gameObject.getX() - camera.getX();
        y = gameObject.getY() - camera.getY();
        // On the screen where (0;0) in center of this screen
        double centredX;
        double centredY;
        centredX = x - camera.getCenterX();
        centredY = y - camera.getCenterY();
        // With rotation around the center (0;0) on viewAngle
        double inViewX;
        double inViewY;
        double viewRagianAngle = camera.getDirection();
        inViewX = centredX * Math.cos(-viewRagianAngle) - centredY * Math.sin(-viewRagianAngle) + camera.getCenterX();
        inViewY = centredX * Math.sin(-viewRagianAngle) + centredY * Math.cos(-viewRagianAngle) + camera.getCenterY();

        int replicaWidth = 150;
        List<String> replicaRowList = getReplicaRowList(Arrays.asList(text.split(" ")), replicaWidth);
        int replicaHeight = replicaRowList.size() * replicaFont.getHeight(text);
        int margin = 20;
        int padding = 10;
        int windowWidth = replicaWidth + 2 * padding;
        int windowHeight = replicaHeight + 2 * padding;
        int cornerRadius = 6;

        Color defaultColor = g.getColor();
        g.setColor(windowColor);
        g.fillRoundRect((float) (inViewX - windowWidth / 2),
                (float) (inViewY - windowHeight - margin), windowWidth, windowHeight, cornerRadius);
        g.setColor(windowBorderColor);
        g.drawRoundRect((float) (inViewX - windowWidth / 2),
                (float) (inViewY - windowHeight - margin), windowWidth, windowHeight, cornerRadius);
        for (int i = 0; i < replicaRowList.size(); ++i) {
            replicaFont.drawString((int) (inViewX - windowWidth / 2 + padding),
                    (int) (inViewY - windowHeight - margin + padding + replicaFont.getHeight() * i), replicaRowList.get(i), textColor);
        }
        g.setColor(defaultColor);
    }

    private List<String> getReplicaRowList(List<String> wordList, int maximumWidth) {
        List<String> replicaRowList = new ArrayList<>();
        StringBuffer currentRow = new StringBuffer();
        for (String word : wordList) {
            if (replicaFont.getWidth(currentRow.toString()) + replicaFont.getWidth(" ") + replicaFont.getWidth(word) <= maximumWidth) {
                if (currentRow.length() == 0) {
                    currentRow.append(word);
                } else {
                    currentRow.append(" " + word);
                }
            } else {
                replicaRowList.add(currentRow.toString());
                currentRow = new StringBuffer();
                currentRow.append(word);
            }
        }
        replicaRowList.add(currentRow.toString());
        return replicaRowList;
    }

}