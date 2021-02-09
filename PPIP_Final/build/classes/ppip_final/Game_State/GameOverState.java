/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;
import Audio.Audio_Player;
import Handlers.Keys;
import ppip_final.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Alex
 */
public class GameOverState extends GameState{
    
    	private Font font;
	private int currentChoice = 0;
    public GameOverState(GameStateManager gsm) {
		
		super(gsm);
                Audio_Player.load("/Music/CreditsMusic.wav", "creditsmusic");
                Audio_Player.load("/Sounds/menuselect.wav", "menuselect");
                Audio_Player.loop("creditsmusic", 600, Audio_Player.getFrames("creditsmusic") - 2200);
                Audio_Player.VolumeControlNum("creditsmusic", (float)0.7);
                Audio_Player.playing="creditsmusic";
		// fonts
		font = new Font("Times New Roman", Font.PLAIN, 50);
		
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
		g.drawString("GAME OVER", 450, 90);
                g.drawString("Oh No! The character has failed the prerequisites to go", 10, 150);
                g.drawString("to space in order to collect the moonrocks. He has been", 10, 210);
                g.drawString("brought back to his starting point. He still need your ", 10, 270);
                g.drawString("help to complete the stages and obtain the moonstones ", 10, 330);
                g.drawString("to pass his final Project! Do you want to try again?", 10, 390);
		g.drawString("Main Menu", 10, 450);
                g.drawString("Exit", 10, 510);
                if(currentChoice == 0) g.fillOval(250, 420, 30, 30);
		else if(currentChoice == 1) g.fillOval(100, 480, 30, 30);


	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop(Audio_Player.playing);
                    gsm.setState(GameStateManager.MENUSTATE);
		}
                else if(currentChoice ==1){
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
			if(currentChoice < 1) {
				Audio_Player.play("menuoption", 0);
				currentChoice++;
			}
		}
	}
    
}

