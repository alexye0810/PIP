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

/**
 *
 * @author Alex
 */
public class CreditsState extends GameState{

    	private Font font;
	private int currentChoice = 0;

    
    	public CreditsState(GameStateManager gsm) {
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
		g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		g.drawString("CREDITS", 90, 40);
                g.setFont(font);
                g.drawString("The Awesome Programmers:", 10, 90);
                g.drawString("Alex Wai-wah Ye", 10, 140);
		g.drawString("Winyul Yin", 10, 190);
                g.drawString("Joris Ah-Kane", 10, 240);
                g.drawString("With special appreciation to our teachers:", 10, 320);
                g.drawString("Ellaheh Mozzafari", 10, 370);
                g.drawString("M.D Istiaque Shariar", 10, 420);
                g.drawString("Champlain St-Lambert College 2020", 10, 690);
                g.drawString("Main Menu", 10, 600);
                if(currentChoice == 0) g.fillOval(250, 570, 30, 30);
	}
        
            private void select() {
		if(currentChoice == 0) {
                    Audio_Player.play("menuselect", 0);
                    Audio_Player.stop(Audio_Player.playing);
                        gsm.setState(GameStateManager.MENUSTATE);
                    
                }


	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) 
                    select();
	}

    
}
