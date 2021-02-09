/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

/**
 *
 * @author Winyu
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import map.BlockMap;


public class Spring extends MapEntity {
     private BufferedImage[] sprites;
     private Player player;
    public Spring(BlockMap tm, Player p){
     super(tm);
                lookingRight = true;
                player=p;
		width = height = 80;
		hbWidth = hbHeight =80;
		
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Player/Spring.png")
			);
			sprites = new BufferedImage[2];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i*width, 0, width, height
				);
			}
			animations.setFrames(sprites);
			animations.setDelay(1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
        
	
	public void update() {
                bounce();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
        
        public void bounce(){
            if(inSpring(player.getx(),(int)player.get1PixelUnder()) ||
                    inSpring(player.getx()-player.hbWidth,(int)player.get1PixelUnder())){
                player.setPosition(player.getx(),this.y-player.hbHeight);
                player.setDY(player.getDY()*-1*1.5);
                animations.update();
            }
        }
        
        public boolean inSpring(int x, int y){
            if (x<=this.x && x>=this.x-hbWidth 
                    && y>=this.y && y<=this.y+hbHeight/2)
                return true;
            else
                return false;
        }
}
