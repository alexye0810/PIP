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
/**
 *
 * @author Alex
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Alex
 */
public class Settings_State extends GameState{
    
        Image img;
    	private Font font;
	private int currentChoice = 0;
    
    	public Settings_State(GameStateManager gsm) {
		
		super(gsm);
            try{
               img = ImageIO.read(new File("Resources/Backgrounds/LVL2.png"));

            }
            catch (Exception ex){}
		// fonts
		font = new Font("Times New Roman", Font.PLAIN, 70);
		
	}
        
        	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH*2, GamePanel.HEIGHT*2);
                g.drawImage(img, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("SETTINGS", 500, 90);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
                g.drawString("Sounds Control", 500, 220);
		g.drawString("Main Menu", 500, 280);
                g.drawString("Credits", 500, 340);
                if(currentChoice == 0) g.fillOval(460, 190, 30, 30);
		else if(currentChoice == 1) g.fillOval(460, 250, 30, 30);
                else if(currentChoice == 2) g.fillOval(460, 310, 30, 30);
	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                   gsm.setState(GameStateManager.SOUNDSTATE);
		}
                else if(currentChoice ==1){
                    Audio_Player.play("menuselect", 0);
			gsm.setState(GameStateManager.MENUSTATE);
		}
                else if(currentChoice ==2){
                    Audio_Player.play("menuselect", 0);
			gsm.setState(GameStateManager.CREDITSSTATE);
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
			if(currentChoice < 2) {
				Audio_Player.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

    
}

