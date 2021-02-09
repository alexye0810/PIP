/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import Audio.Audio_Player;
import Handlers.Keys;
import ppip_final.GamePanel;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu_State extends GameState {
	
        private int currentChoice = 0;
	private int options = 4;
	private Color titleColor= Color.BLACK;
	private Font titleFont = new Font("Times New Roman", Font.PLAIN, 70);
	public static boolean altered=false;
	private Font font = new Font("Times New Roman", Font.PLAIN, 50);
        Image img;
	
	public Menu_State(GameStateManager gsm) {
		
            super(gsm);
            try{
               img = ImageIO.read(new File("Resources/Backgrounds/LVL2.png"));
               Audio_Player.load("/Sounds/menuoption.wav", "menuoption");
                Audio_Player.load("/Sounds/menuselect.wav", "menuselect");
                Audio_Player.load("/Music/Level2.wav", "menumusic");
                Audio_Player.loop("menumusic", 600, Audio_Player.getFrames("menumusic") - 2200);
                if(!altered){
                Audio_Player.VolumeControlNum("menumusic", (float)0.5);
                Audio_Player.VolumeControlNum("menuoption", (float)0.5);
                Audio_Player.VolumeControlNum("menuselect", (float)0.5);
                }
                Audio_Player.playing="menumusic";
            }
            catch (Exception ex){}

        }
		
	public void init() {}
	
	public void update() {

		handleInput();
		
	}
	
 
    
    
    public void draw(Graphics2D g) {

		// draw bg
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT); 

		g.drawImage(img, 0,0,null);
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("PIP", 600, 90);
                
                //draw secondary title
                g.setColor(titleColor);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		g.drawString("Platformer Involving Physics", 320, 150);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Start", 600, 280);
		g.drawString("Settings", 600, 340);
		g.drawString("Exit", 600, 400);
		// draw floating head
		if(currentChoice == 0) g.fillOval(560, 250, 30, 30);
		else if(currentChoice == 1) g.fillOval(560, 310, 30, 30);
                else if(currentChoice == 2) g.fillOval(560, 370, 30, 30);
		
		// other
		g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		g.drawString("2020 Alex Y., Joris A. & Winyul Y.", 10, 700);
		
	}
    
    private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                   gsm.setState(GameStateManager.LORESTATE);
		}
                else if(currentChoice == 1) {
                    Audio_Player.play("menuselect", 0);
		gsm.setState(GameStateManager.SETTINGSSTATE);
		}
                else if(currentChoice == 2) {
                    Audio_Player.play("menuselect", 0);
                    System.exit(0);
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
			if(currentChoice < options - 1) {
				Audio_Player.play("menuoption", 0);
				currentChoice++;
			}
		}
	}
	
}










