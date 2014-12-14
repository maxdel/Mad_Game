package core.model.gameplay.gameobjects.ai;

public interface AIState {

    public void enter();
    public void run(int delta);
    public void update(int delta);

}