package core.view.gameplay.unit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.view.gameplay.ui.HeroInfoView;
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
        drawMask(g, camera.getX(), camera.getY());

        // Drawing bars
        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));

        // draw hp bar
        HeroInfoView.drawBar(g, (int) (unit.getX() - camera.getX()), (int) (unit.getY() - camera.getY()) - 46, 60, 6,
                unit.getAttribute().getCurrentHP(),
                unit.getAttribute().getMaximumHP(), Color.red);
        // draw mp bar
        HeroInfoView.drawBar(g, (int) (unit.getX() - camera.getX()), (int) (unit.getY() - camera.getY()) - 38, 60, 6,
                unit.getAttribute().getCurrentMP(),
                unit.getAttribute().getMaximumMP(), Color.blue);


        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                - (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));

        rotate(g, camera, false);

        renderParticleSystem(g, camera);
    }



    private void renderParticleSystem(Graphics g, Camera camera) throws SlickException {
        Unit unit = (Unit) gameObject;

        if (unit.getCurrentState() == UnitState.SKILL) {
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