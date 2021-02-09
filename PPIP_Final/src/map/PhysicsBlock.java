/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.image.BufferedImage;

/**
 *
 * @author Winyu
 */
public abstract class PhysicsBlock extends Block {
    private int type;
    private double fictionCoefficient;
    
    // block types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
    
   public PhysicsBlock(BufferedImage image, int type) {
		super(image,1);
		this.type = type;
                this.fictionCoefficient=1;
	}
    
    public double getFriction(){
        return this.fictionCoefficient;
    }
    public void setFriction(double k){
        this.fictionCoefficient=k;
    }
   
	public int getType() { return type; }
    
       
}
