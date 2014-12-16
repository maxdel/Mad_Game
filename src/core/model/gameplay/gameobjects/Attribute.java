package core.model.gameplay.gameobjects;

import core.MathAdv;

public class Attribute {

    private double currentHP;
    private double maximumHP;

    private double currentMP;
    private double maximumMP;

    private double currentSpeed;
    private double maximumSpeed;

    private double pAttack;
    private double mAttack;
    
    private double pArmor;
    private double mArmor;

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed, double pAttack, double mAttack,
                     double pArmor, double mArmor) {
        this.maximumHP = maximumHP;
        this.currentHP = maximumHP;
        this.maximumMP = maximumMP;
        this.currentMP = maximumMP;
        this.currentSpeed = 0;
        this.maximumSpeed = maximumSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.pArmor = pArmor;
        this.mArmor = mArmor;
    }

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed) {
        this(maximumHP, maximumMP, maximumSpeed, 0, 0, 0, 0);
    }

    // Getters
    public boolean isAlive() {
        return currentHP > 0;
    }

    public double getCurrentHP() {
        return currentHP;
    }

    public double getMaximumHP() {
        return maximumHP;
    }

    public double getCurrentMP() {
        return currentMP;
    }

    public double getMaximumMP() {
        return maximumMP;
    }

    public double getPAttack() {
        return pAttack;
    }

    public double getMAttack() {
        return mAttack;
    }

    public double getPArmor() {
        return pArmor;
    }

    public double getMArmor() {
        return mArmor;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getMaximumSpeed() {
        return maximumSpeed;
    }

    // Modifiers
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void changeHP(double HPDelta) {
        currentHP = MathAdv.clamp(0, currentHP + HPDelta, maximumHP);
    }

    public void changeMP(double MPDelta) {
        currentMP = MathAdv.clamp(0, currentMP + MPDelta, maximumMP);
    }

    public void changePArmor(double deltaPArmor) {
        pArmor += deltaPArmor;
    }

    public void changeMArmor(double deltaMArmor) {
        mArmor += deltaMArmor;
    }

    public void changePAttack(double deltaPAttack) {
        pAttack += deltaPAttack;
    }

    public void changeMAttack(double deltaMAttack) {
        mAttack += deltaMAttack;
    }

}