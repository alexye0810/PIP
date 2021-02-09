/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;

import Audio.Audio_Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.Keys;
import ppip_final.GamePanel;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Pause_State extends GameState {
	
	private Font font;
	private int currentChoice = 0;
	public Pause_State(GameStateManager gsm) {
		
		super(gsm);

		// fonts
		font = new Font("Times New Roman", Font.PLAIN, 60);
		
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", 450, 90);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
                g.drawString("Resume", 500, 220);
		g.drawString("Higher Volume", 500, 280);
                g.drawString("Lower Volume", 500, 340);
                g.drawString("Menu", 500, 400);
                g.drawString("Exit", 500, 460);
                if(currentChoice == 0) g.fillOval(460, 190, 30, 30);
		else if(currentChoice == 1) g.fillOval(460, 250, 30, 30);
                else if(currentChoice == 2) g.fillOval(460, 310, 30, 30);
                else if(currentChoice == 3) g.fillOval(460, 370, 30, 30);
                else if(currentChoice == 4) g.fillOval(460, 430, 30, 30);
                
	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                        gsm.setPaused(false);
		}
                else if(currentChoice == 1) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.VolumeControl(Audio_Player.playing, "+");
                    Audio_Player.VolumeControl("menuoption", "+");
                    Audio_Player.VolumeControl("menuselect", "+");
		}
                else if(currentChoice == 2) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.VolumeControl(Audio_Player.playing, "-");
                    Audio_Player.VolumeControl("menuoption", "-");
                    Audio_Player.VolumeControl("menuselect", "-");
		}
                else if(currentChoice == 3) {
                    Audio_Player.play("menuselect", 0);
			gsm.setPaused(false);
                        Audio_Player.stop(Audio_Player.playing);
			gsm.setState(GameStateManager.MENUSTATE);
		}
                else if(currentChoice == 4) {
                    Audio_Player.play("menuselect", 0);
                    System.exit(0);
                }

	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) 
                    select();
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

