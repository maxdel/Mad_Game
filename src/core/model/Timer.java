package core.model;

public class Timer {

    private int value;

    public Timer(int value) {
        this.value = value;
    }

    public boolean update(int delta) {
        value -= delta;
        return value <= 0;
    }

}