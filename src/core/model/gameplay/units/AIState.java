package core.model.gameplay.units;

public interface AIState {

    public void enter();
    public void run();
    public void update(int delta);

}