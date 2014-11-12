package core;

public enum GameState {

    GAMEPLAY(0), STARTMENU(1), PAUSEMENU(2);

    private int value;

    private GameState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}