package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ParticleSystem;

public class ParticleEffect {

    private double x;
    private double y;
    private ParticleSystem particleSystem;

    public ParticleEffect(ParticleSystem particleSystem) {
        this(0, 0, particleSystem);
    }

    public ParticleEffect(double x, double y, ParticleSystem particleSystem) {
        this.x = x;
        this.y = y;
        this.particleSystem = particleSystem;
    }

    public void render(Graphics g, Camera camera) {
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), -camera.getDirectionDegrees());
        particleSystem.render((float) (x - camera.getX()), (float) (y - camera.getY()));
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());
    }

    public void update(int delta) {
        particleSystem.update(delta);
    }

    public void reset() {
        particleSystem.reset();
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

}