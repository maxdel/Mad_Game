package core.model.gameplay.units;

public class AttributePair {

    private double currentValue;
    private double maximumValue;

    public AttributePair(double maximumValue) {
       init(maximumValue);
    }

    public void init(double maximumValue) {
        this.currentValue = maximumValue;
        this.maximumValue = maximumValue;
    }

    public void heal(double value) {
        currentValue += value;
        if (currentValue > maximumValue) {
            currentValue = maximumValue;
        }
    }

    public void damage(double value) {
        currentValue -= value;
        if (currentValue < 0) {
            currentValue = 0;
        }
    }

    public void setMaximum(double value) {
        maximumValue = value;
        if (currentValue > maximumValue) {
            currentValue = maximumValue;
        }
    }

    public void setCurrent(int value)
    {
        currentValue = value;
        if (currentValue > maximumValue) {
            currentValue = maximumValue; //TODO: refactor
        }
    }

    public double getCurrent() {
        return currentValue;
    }

    public double getMaximum() {
        return maximumValue;
    }

}
