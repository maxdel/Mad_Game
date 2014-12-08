package core.model.gameplay.gameobjects;

public interface AIState {

    public void enter();
    public void run();
    public void update(int delta);

}