package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.skills.AreaDamage;
import core.model.gameplay.skills.SkillInstanceKind;
import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.io.IOException;

public class VampireView extends UnitView {

    private ParticleSystem psPowerBeam;
    private ConfigurableEmitter cePowerBeam;
    private boolean usePowerBeam;

    private UnitState previousState;
    private SkillInstanceKind previousSkill;
    private Animation swordAttack;
    private Animation spinAttack;
    private Animation doom;
    private Animation throwKnife;
    private Animation powerBeam;
    private Animation walk;

    public VampireView(GameObject vampire) {
        super(vampire);
        walk = ResourceManager.getInstance().getAnimation("vampire_walk");
        swordAttack = ResourceManager.getInstance().getAnimation("vampire_sword_attack");
        spinAttack = ResourceManager.getInstance().getAnimation("vampire_spin_attack");
        doom = ResourceManager.getInstance().getAnimation("vampire_doom");
        throwKnife = ResourceManager.getInstance().getAnimation("vampire_throw_knife");
        powerBeam = ResourceManager.getInstance().getAnimation("vampire_power_beam");
        animation = walk;

        try {
            psPowerBeam = ParticleIO.loadConfiguredSystem("/res/particles/power_beam.xml");

            cePowerBeam = (ConfigurableEmitter) psPowerBeam.getEmitter(0);
            cePowerBeam.spread.setValue((float) (((AreaDamage)((Bot)vampire).getSkillByKind(SkillInstanceKind.POWER_BEAM)).getAngle() / Math.PI * 180));

            psPowerBeam.removeAllEmitters();
            psPowerBeam.setRemoveCompletedEmitters(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        psPowerBeam.update(delta);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Bot vampire = (Bot) gameObject;

        if (vampire.getCurrentState() != previousState) {
            switch (vampire.getCurrentState()) {
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (vampire.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("vampire_walk")));
                    animation.start();
                    break;
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    break;
            }
        }
        previousState = vampire.getCurrentState();

        if (vampire.getCastingSkill() != null && vampire.getCastingSkill().getKind() != previousSkill) {
            switch (vampire.getCastingSkill().getKind()) {
                case KNIFE_ATTACK:
                    ResourceManager.getInstance().getSound("sword_attack").play();
                    animation = swordAttack;
                    animation.restart();
                    vampire.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, vampire.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    animation.start();
                    break;
                case VAMPIRIC_KNIFE:
                    ResourceManager.getInstance().getSound("sword_attack").play();
                    animation = throwKnife;
                    animation.restart();
                    vampire.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, vampire.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    animation.start();
                    break;
                case SWORD_SPIN:
                    ResourceManager.getInstance().getSound("sword_attack").play();
                    animation = spinAttack;
                    animation.restart();
                    vampire.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, vampire.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    animation.start();
                    break;
                case DOOM:
                    ResourceManager.getInstance().getSound("sword_attack").play();
                    animation = doom;
                    animation.restart();
                    vampire.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, vampire.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    animation.start();
                    break;
                case POWER_BEAM:
                    animation = powerBeam;
                    animation.restart();
                    vampire.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, vampire.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    animation.start();
                    break;
                case SHIFT:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
            }
        }
        if (vampire.getCastingSkill() != null) {
            previousSkill = vampire.getCastingSkill().getKind();
        }
        if (vampire.getCastingSkill() != null && vampire.getCastingSkill().getKind() == SkillInstanceKind.POWER_BEAM) {
            int preCastTime = vampire.getCastingSkill().getPreApplyTime();
            int currentCastTime = vampire.getCastingSkill().getCastTime() - vampire.getCurrentSkillCastingTime();
            if (currentCastTime + cePowerBeam.length.getMax() > preCastTime && !usePowerBeam) {
                ConfigurableEmitter ce = cePowerBeam.duplicate();
                ce.setPosition((float) (gameObject.getX()), (float) (gameObject.getY()));
                ce.setEnabled(true);
                ce.angularOffset.setValue((float) (ce.angularOffset.getValue(0) + gameObject.getDirection() / Math.PI * 180));
                psPowerBeam.addEmitter(ce);
                ResourceManager.getInstance().getSound("power_beam").play();
                usePowerBeam = true;
            }
        } else {
            usePowerBeam = false;
        }

        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), -camera.getDirectionDegrees());
        psPowerBeam.render((float) (-camera.getX()), (float) (-camera.getY()));
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());

        super.render(g, camera);
    }

}