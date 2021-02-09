package ppip_final.Game_State;

import Audio.Audio_Player;
import ppip_final.GamePanel;

public class GameStateManager {

    private GameState[] gameStates;
    private int currentState;

    private Pause_State pauseState;
    private boolean paused;

    public static final int NUMGAMESTATES = 10;
    public static final int MENUSTATE = 0;
    public static final int SETTINGSSTATE = 1;
    public static final int LEVEL1STATE = 2;
    public static final int LEVEL2STATE = 3;
    public static final int LEVEL3STATE = 4;
    public static final int SOUNDSTATE = 5;
    public static final int CREDITSSTATE = 6;
    public static final int LORESTATE = 7;
    public static final int FINISHSTATE = 8;
    public static final int GAMEOVERSTATE = 9;

    public GameStateManager() {

        Audio_Player.init();

        gameStates = new GameState[NUMGAMESTATES];

        pauseState = new Pause_State(this);
        paused = false;

        currentState = MENUSTATE;
        loadState(currentState);

    }

    private void loadState(int state) {
        if (state == MENUSTATE) {
            gameStates[state] = new Menu_State(this);
        } else if (state == LORESTATE) {
            gameStates[state] = new Lore_State(this);
        } else if (state == LEVEL1STATE) {
            gameStates[state] = new Level1State(this);
        } else if (state == LEVEL2STATE) {
            gameStates[state] = new Level2State(this);
        } else if (state == LEVEL3STATE) {
            gameStates[state] = new Level3State(this);
        } else if (state == SETTINGSSTATE) {
            gameStates[state] = new Settings_State(this);
        } else if (state == SOUNDSTATE) {
            gameStates[state] = new SoundState(this);
        } else if (state == CREDITSSTATE) {
            gameStates[state] = new CreditsState(this);
        } else if (state == FINISHSTATE) {
            gameStates[state] = new Finish_State(this);
        } else if (state == GAMEOVERSTATE) {
            gameStates[state] = new GameOverState(this);
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void update() {
        if (paused) {
            pauseState.update();
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        if (paused) {
            pauseState.draw(g);
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        } else {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }

}
