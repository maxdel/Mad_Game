package core.model;

public class Timer {

    private int value;
    private boolean active;

    public Timer() {
        this.value = 0;
        this.active = false;
    }

    public Timer(int value) {
        activate(value);
    }

    public boolean update(int delta) {
        if (active) {
            if (value > 0) {
                value -= delta;
                if (value < 0) {
                    value = 0;
                }
            }
            if (value == 0) {
                active = false;

            }
            return value == 0;
        } else {
            return false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void activate(int time) {
        value = time;
        active = true;
    }

}