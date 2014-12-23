package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.io.IOException;

public class FireElementalView extends UnitView {

    private ParticleSystem trail;
    private ParticleSystem ps;
    private ConfigurableEmitter ce;
    private int spreadTime;
    private final int SPREAD_TIME = 30;

    public FireElementalView(GameObject fireElemental) {
        super(fireElemental);
        animation = ResourceManager.getInstance().getAnimation("empty");
        try {
            ps = ParticleIO.loadConfiguredSystem("/res/particles/fire_elemental.xml");
            trail = ParticleIO.loadConfiguredSystem("/res/particles/fire_elemental_trail.xml");
            ce = (ConfigurableEmitter) trail.getEmitter(0);
            trail.removeAllEmitters();
            trail.setRemoveCompletedEmitters(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        spreadTime = SPREAD_TIME;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        spreadTime -= delta;
        while(spreadTime < 0) {
            spreadTime += SPREAD_TIME;
            ConfigurableEmitter currentEmitter = ce.duplicate();
            currentEmitter.setPosition((float) (gameObject.getX()), (float) (gameObject.getY()));
            currentEmitter.setEnabled(true);
            trail.addEmitter(currentEmitter);
        }

        trail.update(delta);
        ps.update(delta);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        super.render(g, camera);
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), -camera.getDirectionDegrees());
        /*for (int i = 0; i < trail.getEmitterCount(); ++i) {
            ConfigurableEmitter ce = (ConfigurableEmitter) trail.getEmitter(i);
            ce.setPosition(ce.getX() - (float) (gameObject.getX() - camera.getX()),
                    ce.getY() - (float) (gameObject.getY() - camera.getY()));
        }*/

        trail.render((float) -camera.getX(), (float) -camera.getY());
        ps.render((float) (gameObject.getX() - camera.getX()), (float) (gameObject.getY() - camera.getY()));
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());
    }

}