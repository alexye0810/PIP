/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;


import java.awt.image.BufferedImage;

/**
 *
 * @author bakup
 */
public class Animations {
 private BufferedImage[] frames;
	private int currFrame;
	private int numFrames;
	
	private int count;
	private int delay;
	
	private int timesPlayed;
	
	public Animations() {
		timesPlayed = 0;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currFrame = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		numFrames = frames.length;
	}
	
	public void setDelay(int i){
            delay = i; 
        }
	public void setFrame(int i){
            currFrame = i; 
        }
	public void setNumFrames(int i){
            numFrames = i; 
        }
	
	public void update() {
		
		if(delay == -1) return;
		
		count++;
		
		if(count == delay) {
			currFrame++;
			count = 0;
		}
		if(currFrame == numFrames) {
			currFrame = 0;
			timesPlayed++;
		}
		
	}
	
	public int getFrame(){ 
            return currFrame; 
        }
	public int getCount(){
            return count;
        }
	public BufferedImage getImage(){ 
            return frames[currFrame]; 
        }
	public boolean hasPlayedOnce(){
            return timesPlayed > 0;
        }
	public boolean hasPlayed(int i){
            return timesPlayed == i; 
        }
	   
}
