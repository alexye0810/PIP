package Handlers;

import java.awt.event.KeyEvent;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {
	
	public static final int NUM_KEYS = 16;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
        
        public static int up=KeyEvent.VK_W;
	public static int down=KeyEvent.VK_S;
	public static int right=KeyEvent.VK_D;
	public static int left = KeyEvent.VK_A;
        public static int run = KeyEvent.VK_SHIFT;
        public static int jump = KeyEvent.VK_SPACE;
	
	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	
	public static void keySet(int i, boolean b) {
		if(i == up) keyState[UP] = b;
		else if(i == left) keyState[LEFT] = b;
		else if(i == down) keyState[DOWN] = b;
		else if(i == right) keyState[RIGHT] = b;
		else if(i == jump) keyState[BUTTON1] = b;
		else if(i == run) keyState[BUTTON2] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEYS; i++) {
			if(keyState[i]) return true;
		}
		return false;
	}
        

	
}
