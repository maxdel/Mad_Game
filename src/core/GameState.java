package core;

public enum GameState {

    GAMEPLAY(0), MENU(1);

    private int value;

    private GameState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}