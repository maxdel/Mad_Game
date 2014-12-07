package core.model.gameplay.units;

public class Attribute {

    private AttributePair hp;
    private AttributePair mp;

    private double currentSpeed;
    private double maximumSpeed;
    
    private double pArmor;
    private double mArmor;

    private double pAttack;
    private double mAttack;

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed) {
        hp = new AttributePair(maximumHP);
        mp = new AttributePair(maximumMP);
        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = 0;
        this.pArmor = 0;
        this.mArmor = 0;
        this.pAttack = 0;
        this.mAttack = 0;
    }



    // Getters
    public boolean hpAreEnded() {
        return hp.getCurrent() == 0;
    }

    public AttributePair getHP() {
        return hp;
    }

    public AttributePair getMP() {
        return mp;
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
    public void resetHpMp(double hpValue, double mpValue) {
        hp.init(hpValue);
        mp.init(mpValue);
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public void increaseMArmor(double value) {
        mArmor += value;
    }

    public void increasePArmor(double value) {
        pArmor += value;
    }

    public void increasePAttack(double value) {
        pAttack += value;
    }

    public void increaseMAttack(double value) {
        pAttack += value;
    }
    
    public void decreaseMArmor(double value) {
        mArmor -= value;
    }

    public void decreasePArmor(double value) {
        pArmor -= value;
    }

    public void decreasePAttack(double value) {
        pAttack -= value;
    }

    public void decreaseMAttack(double value) {
        pAttack -= value;
    }

}