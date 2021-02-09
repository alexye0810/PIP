/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.image.BufferedImage;

/**
 *
 * @author bakup
 */
public class Block {

    private BufferedImage texture;
    private int tangibility;
    //Tangibility = 0 means the player can walk through the block, so it's air
    //Tangibility = 1 means the player's velocity will be reduced to zero if they collide

    public Block(BufferedImage txtr, int t) {
        this.texture = txtr;
        this.tangibility = t;
    }

    // block tangibility types
    public static final int INTANGIBLE = 0;
    public static final int TANGIBLE = 1;
    public static final int ICE = 2;
    public static final int SLIDE = 3;

    public int getTangible() {
        return tangibility;
    }

    public BufferedImage getTexture() {
        return texture;
    }
}
