/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Audio.Audio_Player;
import javax.imageio.ImageIO;

import map.BlockMap;
/**
 *
 * @author bakup
 */
public class Player extends MapEntity{
    
    //characteristics
    private int lives;
    private int hp;
    
    //running?
    private boolean sprint;
    private boolean ending;
    
    // animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
		1, 5,  3, 3,  5,  1, 1
	};
	private final int[] FRAMEWIDTHS = {
		200, 200, 200, 200, 200, 200, 200
	};
	private final int[] FRAMEHEIGHTS = {
		200, 200, 200, 200, 200, 200, 200
	};
	private final int[] SPRITEDELAYS = {
		-1, 10, 6, 5, 2, -1, 1
	};
	
	
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int SPRINT = 4;
	private static final int DEAD = 5;
        private static final int ENDING = 6;

	
	public Player(BlockMap tm) {
		
		super(tm);
		
		width = 200;
		height = 200;
		hbWidth = 80;
		hbHeight = 160;
		
		xAccel = 1.6;
		maxSpeed = 10.6;
		stopSpeed = 10.6;
		fallSpeed = 01.5;
		maxFallSpeed = 20.0;
		jumpStart = -20.8;
		stopJumpSpeed = 3.0;
				
		lookingRight = true;
		
		lives = 3;
		hp = 5;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/SpaceSprites.png"
				)
			);
			
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		setAnimation(IDLE);
		
	}
	

	
	public int getHealth() { return hp; }
	
	
    @Override
	public void setJumping(boolean b) {
		jumping = b;
	}
	
	
	public void setDashing(boolean b) {
		if(!b) sprint = false;
		else if(b && !falling) {
			sprint = true;
		}
	}
	public boolean isDashing(){
            return sprint; 
        }
	
	public void setDead() {
		hp = 0;
		stop();
	}
	
        
        //Setters and getters
	
        public void setTeleporting(boolean b) {
            ending = b; 
        }
	public void setHealth(int i){
            hp = i; 
        }
	public void setLives(int i){
            lives = i; 
        }
	public void gainLife(){
            lives++; }
	public void loseLife(){ 
            lives--; 
        }
	public int getLives(){ 
            return lives; 
        }
	
	public void hit() {
		stop();
		hp -= 1;
	}
	
	public void reset() {
		hp = 1;
		lookingRight = true;
		currAction = -1;
		stop();
	}
	
	public void stop() {
		left = right = up = down =  
			sprint = jumping = false;
	}
	
	private void getNextPosition() {
		
                int bottomBlock = (int)(y + hbHeight/2 + 1)/sizeOfBlocks;
                int surfaceType=blockMap.getTangible(bottomBlock, (int)x/sizeOfBlocks);
            
		double maxSpeed = this.maxSpeed;
		if(sprint) maxSpeed *= 1.75;
                
                
                // movement
		if(left) {
                    if(surfaceType==1 ){
			dx -= xAccel;
                        stopSpeed = 10.6;
                    }
                    else if(surfaceType==2){
                        dx -= xAccel/2;
                        stopSpeed=stopSpeed*0.8;
                    }
                    else if(surfaceType==3){
                        dx -= xAccel/(Math.sqrt(2)/2);
                        stopSpeed=stopSpeed*0.8;
                    }
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
                    else if(surfaceType==0)
                        dx -= xAccel;
                        
		}
		else if(right) {
			if(surfaceType==1){
			dx += xAccel;
                        stopSpeed = 10.6;
                        }
                    else if(surfaceType==2){
                        dx += xAccel/2;
                    stopSpeed=stopSpeed*0.8;
                    }
                    else if(surfaceType==3){
                        dx += xAccel/(Math.sqrt(2)/2);
                            stopSpeed=stopSpeed*0.8;
                       
                    }
                    else if(surfaceType==0)
                        dx += xAccel;
                   
                        
                        
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// jumping
		if(jumping && !falling) {
			//sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
			Audio_Player.play("playerjump");
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) //bigger jumps by holding the key
                            dy += stopJumpSpeed;
			if(dy > maxFallSpeed) //prevent falling to fast
                            dy = maxFallSpeed;
                        
		}
		
	}
	
	private void setAnimation(int i) {
		currAction = i;
		animations.setFrames(sprites.get(currAction));
		animations.setDelay(SPRITEDELAYS[currAction]);
		width = FRAMEWIDTHS[currAction];
		height = FRAMEHEIGHTS[currAction];
	}
	
	public void update() {
                
                if(ending) {
		}
            
		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkBlockMapCollision();
		setPosition(tempX, tempY);
		if(isFalling && !falling) {
			Audio_Player.play("playerlands");
		}
		if(dx == 0) x = (int)x;
		
		// set animation, ordered by priority
                if(ending) {
			if(currAction != ENDING) {
				setAnimation(ENDING);
			}
		}
                
		if(hp == 0) {
			if(currAction != DEAD) {
				setAnimation(DEAD);
			}
		}
		else if(dy < 0) {
			if(currAction != JUMPING) {
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		else if(sprint && (left || right)) {
			if(currAction != SPRINT) {
				setAnimation(SPRINT);
			}
		}
		else if(left || right) {
			if(currAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(currAction != IDLE) {
			setAnimation(IDLE);
		}
		
		animations.update();
		
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}