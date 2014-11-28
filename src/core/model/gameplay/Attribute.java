package core.model.gameplay;

public class Attribute {

    private double currentHP;
    private double maximumHP;
    private double currentMP;
    private double maximumMP;
    private double currentSpeed;
    private double maximumSpeed;
    private double pArmor;
    private double mArmor;

    public Attribute(double maximumHP, double maximumMP, double maximumSpeed) {
        this.maximumHP = maximumHP;
        this.currentHP = maximumHP;
        this.maximumMP = maximumMP;
        this.currentMP = maximumMP;
        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = 0;
        this.pArmor = 5;
        this.mArmor = 5;
    }

    public double getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(double currentHP) {
        if (currentHP >= 0 && currentHP <= maximumHP) {
            this.currentHP = currentHP;
        } else if (currentHP < 0) {
            this.currentHP = 0;
        } else {
            this.currentHP = maximumHP;
        }
    }

    public double getMaximumHP() {
        return maximumHP;
    }

    public void setMaximumHP(double maximumHP) {
        this.maximumHP = maximumHP;
    }

    public double getCurrentMP() {
        return currentMP;
    }

    public void setCurrentMP(double currentMP) {
        if (currentMP >= 0 && currentMP <= maximumMP) {
            this.currentMP = currentMP;
        } else if (currentMP < 0) {
            this.currentMP = 0;
        } else {
            this.currentMP = maximumMP;
        }
    }

    public double getMaximumMP() {
        return maximumMP;
    }

    public void setMaximumMP(double maximumMP) {
        this.maximumMP = maximumMP;
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

}