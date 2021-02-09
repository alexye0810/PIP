/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;

import Audio.Audio_Player;
import Handlers.Keys;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import ppip_final.GamePanel;

/**
 *
 * @author Alex
 */
public class Lore_State extends GameState{
    	private Font font;
	private int currentChoice = 0;

    
    	public Lore_State(GameStateManager gsm) {
		super(gsm);
		// fonts
		font = new Font("Times New Roman", Font.PLAIN, 50);
                Audio_Player.load("/Sounds/menuselect.wav", "menuselect");
		
	}
        
        	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);	
                g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		g.drawString("In A Country Far Far Away", 10, 60);
                g.setFont(font);
                g.drawString("A character has been tasked to go on the moon", 10, 110);
                g.drawString(" to retrieve a moonstone for your final project.", 10, 150);
                g.drawString("However, you must be careful as you will need to", 10, 190);
                g.drawString("traverse 3 maps filled with physics concepts. Use", 10, 230);
		g.drawString("your skills to help the character retireve the", 10, 270);
                g.drawString("moonstone so he can pass his session. Be sure to", 10, 310);
                g.drawString("notice the physics concepts as they are similar", 10, 350);
                g.drawString("as in reality. You might find something unexpected ", 10, 390);
                g.drawString("at the end of the game...", 10, 430);
                g.drawString("Good luck and may the force be with you!", 10, 470);
                g.drawString("Start", 10, 570);
                g.drawString("Level 1", 10, 610);
                g.drawString("Level 2", 10, 650);
                g.drawString("Level 3", 10, 690);
                if(currentChoice == 0) g.fillOval(150, 540, 30, 30);
                else if(currentChoice == 1) g.fillOval(170, 580, 30, 30);
                else if(currentChoice == 2) g.fillOval(170, 620, 30, 30);
                else if(currentChoice == 3) g.fillOval(170, 660, 30, 30);
	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop("menumusic");
                        gsm.setState(GameStateManager.LEVEL1STATE);
                    
                }
                else if(currentChoice == 1) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop("menumusic");
                        gsm.setState(GameStateManager.LEVEL1STATE);
                    
                }
                if(currentChoice == 2) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop("menumusic");
                        gsm.setState(GameStateManager.LEVEL2STATE);
                    
                }
                if(currentChoice == 3) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop("menumusic");
                        gsm.setState(GameStateManager.LEVEL3STATE);
                    
                }


	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				Audio_Player.play("menuoption", 0);
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < 4) {
				Audio_Player.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

}
