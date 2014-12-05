package core.model.gameplay.units;

public class Attribute {

    private AttributePair health;
    private AttributePair mana;

    private double currentSpeed;
    private double maximumSpeed;

    private double pArmor;
    private double mArmor;

    private double pAttack;
    private double mAttack;

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed) {
        health = new AttributePair(maximumHP);
        mana = new AttributePair(maximumMP);
        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = 0;
        this.pArmor = 0;
        this.mArmor = 0;
        this.pAttack = 0;
        this.mAttack = 0;
    }


    public void resetHpMp(int hpMaxValue, int mpMaxValue) {
        health.init(hpMaxValue);
        mana.init(mpMaxValue);
    }

    public AttributePair getHP() {
        return health;
    }

    public AttributePair getMP() {
        return mana;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public double getPArmor() {
        return pArmor;
    }

    public void setPArmor(double pArmor) {
        this.pArmor = pArmor;
    }

    public double getMArmor() {
        return mArmor;
    }

    public void setMArmor(double mArmor) {
        this.mArmor = mArmor;
    }

    public double getPAttack() {
        return pAttack;
    }

    public void setPAttack(double pAttack) {
        this.pAttack = pAttack;
    }

    public double getMAttack() {
        return mAttack;
    }

    public void setMAttack(double mAttack) {
        this.mAttack = mAttack;
    }

}