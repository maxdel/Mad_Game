package core;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

/*
* Execute start menu
* */
public class StartMenuState extends BasicGameState {

    private static StartMenuState instance;

    public static final int STATE_ID = 0;

    private int NUMBER_OF_CHOICES = 4;
    private final int START = 0;
    private final int LOAD = 1;
    private final int HELP = 2;
    private final int EXIT = 3;
    private String[] playerChoices = new String[NUMBER_OF_CHOICES];
    private int currentChoice = NUMBER_OF_CHOICES - 1;


    private Font menuItemFont, helpMsgFont;
    private TrueTypeFont menuItemTTF, heplMsgTTF;




    private StartMenuState() {}

    public static StartMenuState getInstance() {
        if (instance == null) {
            instance = new StartMenuState();
        }
        return instance;
    }

    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        menuItemFont = new Font("Verdana", Font.BOLD, 40);
        helpMsgFont = new Font("Verdana", Font.PLAIN, 16);
        menuItemTTF = new TrueTypeFont(menuItemFont, true);
        heplMsgTTF = new TrueTypeFont(helpMsgFont, true);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            if (i != currentChoice) {
                menuItemTTF.drawString( 300, i * 50 + 200, playerChoices[i], new Color(200, 200, 200));
            }
            else {
                menuItemTTF.drawString( 300, i * 50 + 200, playerChoices[i], new Color(255, 153, 0));
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_S)) {
            if (currentChoice == 0) {
                currentChoice = NUMBER_OF_CHOICES - 1;
            }
            currentChoice--;
        }

        if (input.isKeyPressed(Input.KEY_W)) {
            if (currentChoice == NUMBER_OF_CHOICES - 1) {
                currentChoice = 0;
            }
            currentChoice++;
        }

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (currentChoice) {
                case START:
                    stateBasedGame.enterState(GamePlayState.STATE_ID);
                    break;
                case LOAD:
                    break;
                case HELP:
                    break;
                case EXIT:
                    gameContainer.exit();
                    break;
            }
        }
    }
}

