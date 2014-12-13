package core.model.gameplay.gameobjects;

public class Attribute {

    private AttributePair hp;
    private AttributePair mp;

    private double currentSpeed;
    private double maximumSpeed;

    private double pAttack;
    private double mAttack;
    
    private double pArmor;
    private double mArmor;

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed, double pAttack, double mAttack,
                     double pArmor, double mArmor) {
        this.hp = new AttributePair(maximumHP);
        this.mp = new AttributePair(maximumMP);
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