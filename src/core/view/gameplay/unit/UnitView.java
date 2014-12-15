package core.view.gameplay.unit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleIO;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.skills.AreaDamage;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;
import core.view.gameplay.ParticleEffect;
import core.view.gameplay.gameobjectsolid.GameObjectSolidView;

public abstract class UnitView extends GameObjectSolidView {

    private enum ParticleType {
        SPARKS, LEAFS;
    }

    private Map<ParticleType, ParticleEffect> particleMap;
    private int castTimeStamp;

    public UnitView(GameObject unit) {
        super(unit);

        particleMap = new HashMap<>();
        try {
            particleMap.put(ParticleType.SPARKS, new ParticleEffect(ParticleIO.loadConfiguredSystem("/res/particles/sparks.xml")));
            particleMap.put(ParticleType.LEAFS, new ParticleEffect(ParticleIO.loadConfiguredSystem("/res/particles/leafs.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int delta) {
        particleMap.forEach((particleType, particleEffect) -> particleEffect.update(delta));
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Unit unit = (Unit) gameObject;

        rotate(g, camera, true);

        draw(camera);
        animation.draw((float) (gameObject.getX() - camera.getX() - animation.getWidth() / 2),
                (float) (gameObject.getY() - camera.getY() - animation.getHeight() / 2));
        //drawMask(g, camera.getX(), camera.getY());

        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(unit.getX() - camera.getX()), (int)(unit.getY() - camera.getY()) - 46, 60, 6,
                unit.getAttribute().getHP().getCurrent(),
                unit.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, (int) (unit.getX() - camera.getX()), (int) (unit.getY() - camera.getY()) - 38, 60, 6,
                unit.getAttribute().getMP().getCurrent(),
                unit.getAttribute().getMP().getMaximum(), Color.blue);
        drawSkillProcessBar(g, (int) (unit.getX() - camera.getX()), (int) (unit.getY() - camera.getY()) + 38, 150, 4,
                Color.magenta, Color.cyan);
        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                - (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));

        rotate(g, camera, false);

        renderParticleSystem(g, camera);
    }

    protected void drawHealthbar(Graphics g, int x, int y, int width, int height, double current, double maximum,
                                 Color color) {
        Color tempColor = g.getColor();
        g.setColor(Color.white);
        g.fillRect(x - width / 2, y - height / 2, width, height);
        g.setColor(color);
        g.fillRect(x - width / 2, y - height / 2, width * (float) (current / maximum), height);
        g.setColor(Color.darkGray);
        g.drawRect(x - width / 2, y - height / 2, width, height);
        g.setColor(tempColor);
    }

    protected void drawSkillProcessBar(Graphics g, int x, int y, int width, int height, Color preCastColor,
                                          Color postCastColor) {
        Unit unit = (Unit) gameObject;

        if (unit.getCurrentState() == GameObjectState.SKILL) {
            int castTime = unit.getCastingSkill().getCastTime();
            int preCastTime = unit.getCastingSkill().getPreApplyTime();
            int postCastTime = castTime - preCastTime;
            int currentCastTime = unit.getCastingSkill().getCastTime() - unit.getCurrentSkillCastingTime();

            int preWidth = width * preCastTime / castTime;
            int postWidth = width * postCastTime / castTime;
            int preX = - postWidth / 2;
            int postX = preWidth / 2;

            int currentPreCastTime;
            if (currentCastTime > preCastTime) {
                currentPreCastTime = preCastTime;
            } else {
                currentPreCastTime = currentCastTime;
            }
            drawHealthbar(g, x + preX, y, preWidth, height, currentPreCastTime, preCastTime, preCastColor);

            int currentPostCastTime;
            if (currentCastTime - preCastTime < 0) {
                currentPostCastTime = 0;
            } else {
                currentPostCastTime = currentCastTime - preCastTime;
            }
            drawHealthbar(g, x + postX, y, postWidth, height, currentPostCastTime, postCastTime, postCastColor);
        }
    }

    private void renderParticleSystem(Graphics g, Camera camera) throws SlickException {
        Unit unit = (Unit) gameObject;

        if (unit.getCurrentState() == GameObjectState.SKILL) {
            if (unit.getCastingSkill().getKind() == SkillInstanceKind.SWORD_ATTACK) {
                for (GameObject gameObject : World.getInstance().getGameObjectList()) {
                    if (gameObject instanceof GameObjectSolid) {
                        GameObjectSolid target = (GameObjectSolid) gameObject;

                        AreaDamage areaDamage = (AreaDamage) unit.getCastingSkill();
                        if (areaDamage.isHitTheTarget(unit, target)) {
                            double currentCastTime = unit.getCastingSkill().getCastTime() - unit.getCurrentSkillCastingTime();
                            if (castTimeStamp < unit.getCastingSkill().getPreApplyTime() &&
                                    currentCastTime >= unit.getCastingSkill().getPreApplyTime()) {
                                ParticleEffect pe;
                                switch (target.getType()) {
                                    case WALL:
                                        pe = particleMap.get(ParticleType.SPARKS);
                                        pe.setX(unit.getX() + MathAdv.lengthDirX(unit.getDirection(), areaDamage.getRadius()));
                                        pe.setY(unit.getY() + MathAdv.lengthDirY(unit.getDirection(), areaDamage.getRadius()));
                                        pe.reset();
                                        ResourceManager.getInstance().getSound("sword_stone").play();
                                        break;
                                    case TREE:
                                        pe = particleMap.get(ParticleType.LEAFS);
                                        pe.setX(unit.getX() + MathAdv.lengthDirX(unit.getDirection(), areaDamage.getRadius()));
                                        pe.setY(unit.getY() + MathAdv.lengthDirY(unit.getDirection(), areaDamage.getRadius()));
                                        pe.reset();
                                        ResourceManager.getInstance().getSound("sword_tree").play();
                                        break;
                                }
                                if (target instanceof Unit && target != unit) {
                                    Unit targetUnit = (Unit) target;
                                    if (targetUnit.getCastingSkill() != null && targetUnit.getCastingSkill().getKind() == SkillInstanceKind.SWORD_ATTACK) {
                                        pe = particleMap.get(ParticleType.SPARKS);
                                        pe.setX(unit.getX() + MathAdv.lengthDirX(unit.getDirection(), areaDamage.getRadius()));
                                        pe.setY(unit.getY() + MathAdv.lengthDirY(unit.getDirection(), areaDamage.getRadius()));
                                        pe.reset();
                                        ResourceManager.getInstance().getSound("sword_stone").play();
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            castTimeStamp = unit.getCastingSkill().getCastTime() - unit.getCurrentSkillCastingTime();
        }

        particleMap.forEach((particleType, particleEffect) -> particleEffect.render(g, camera));
    }
    
}