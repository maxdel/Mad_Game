package core;

public enum GameState {

    GAMEPLAY(0), MENUSTART(1), MENUPAUSE(2);

    private int value;

    private GameState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}