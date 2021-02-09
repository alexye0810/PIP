/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import map.BlockMap;
import Handlers.Keys;

/**
 *
 * @author Winyu
 */
public class Rope extends MapEntity {
       private BufferedImage[] sprites;
       private Player player;
    
       public Rope(BlockMap tm,Player p){
     super(tm);
        
    player=p;
    lookingRight = true;
                width=20;
		height = 80;
		hbWidth = 20;
		hbHeight = 80;
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Player/rope.gif")
			);
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					80-20, 0, width, height
				);
			}
			animations.setFrames(sprites);
			animations.setDelay(9);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		animations.update();
                
                
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
        
        public boolean inRope(int y, int x){
            if (y<=this.y && y>=this.y-hbHeight 
                    && x>=this.x && x<=this.y+hbWidth/2)
                return true;
            else
                return false;
        }
        
        public void climb(){
            if(inRope(player.gety(),(int)player.get1PixelRight())){    
                animations.update();
                
        }
        }    
       @Override
        public void setUp(boolean b) { 
            up=b;
            if(inRope(player.gety(),(int)player.get1PixelRight())){
            player.setDY(-10);
                animations.update();
                
        }
        }
        
       @Override
	public void setDown(boolean b) { 
            down = b; 
            if(inRope(player.gety(),(int)player.get1PixelRight())){
            player.setDY(10);
                animations.update();
                
        }
        }
    
}


