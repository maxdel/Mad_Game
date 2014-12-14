package core.gamestates;

public enum GameState {

    GAMEPLAY(0), MENU(1), SETTINGS(2), AUTHORS(3);

    private int value;

    private GameState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}