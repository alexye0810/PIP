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
public class SoundState extends GameState{
    
    	private Font font;
	private int currentChoice = 0;
    public SoundState(GameStateManager gsm) {
		
		super(gsm);
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
		g.drawString("SOUND CONTROL", 450, 90);
                g.drawString("Higher Volume", 500, 220);
		g.drawString("Lower Volume", 500, 290);
                g.drawString("Settings", 500, 360);
                if(currentChoice == 0) g.fillOval(460, 190, 30, 30);
		else if(currentChoice == 1) g.fillOval(460, 260, 30, 30);
                else if(currentChoice == 2) g.fillOval(460, 330, 30, 30);


	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.VolumeControl(Audio_Player.playing, "+");
                    Audio_Player.VolumeControl("menuoption", "+");
                    Audio_Player.VolumeControl("menuselect", "+");
                    Menu_State.altered=true;
		}
                else if(currentChoice ==1){
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.VolumeControl(Audio_Player.playing, "-");
                    Audio_Player.VolumeControl("menuoption", "-");
                    Audio_Player.VolumeControl("menuselect", "-");
                    Menu_State.altered=true;
                    
		}
                else if(currentChoice ==2){
                    Audio_Player.play("menuselect", 0);
                    gsm.setState(GameStateManager.SETTINGSSTATE);
			
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
			if(currentChoice < 3) {
				Audio_Player.play("menuoption", 0);
				currentChoice++;
			}
		}
	}
    
}
