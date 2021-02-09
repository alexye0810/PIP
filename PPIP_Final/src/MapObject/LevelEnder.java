/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import map.BlockMap;

/**
 *
 * @author bakup
 */
public class LevelEnder extends MapEntity {

    private BufferedImage[] sprites;

    public LevelEnder(BlockMap bm, String s) {
        super(bm);
        lookingRight = true;
        width = height = 200;
        hbWidth = 200;
        hbHeight = 200;
        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            sprites = new BufferedImage[2];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width, 0, width, height
                );
            }
            animations.setFrames(sprites);
            animations.setDelay(9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        animations.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
