package core.resourcemanager;

import org.newdawn.slick.Animation;

public class AnimationInfo {

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
