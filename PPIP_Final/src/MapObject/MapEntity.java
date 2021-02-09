/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

import map.BlockMap;
import map.Block;
import ppip_final.GamePanel;
import java.awt.Rectangle;

/**
 *
 * @author bakup
 */
public abstract class MapEntity {

    // block variables
    protected BlockMap blockMap;
    protected int sizeOfBlocks;
    protected double xmap;
    protected double ymap;

    // position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // dimensions
    protected int width;
    protected int height;

    // hitbox
    protected int hbWidth;
    protected int hbHeight;

    // collision (current. prediction, and transition)
    protected int currY;
    protected int currX;
    protected double destX;
    protected double destY;
    protected double tempX;
    protected double tempY;
    //corners of hitboxes
    protected boolean topLeftCorner;
    protected boolean topRightCorner;
    protected boolean bottomLeftCorner;
    protected boolean bottomRightCorner;

    // animation
    protected Animations animations;
    protected int currAction; //to prevent midair jumps
    protected int lastAction;
    protected boolean lookingRight; //check player orientation

    // movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes
    protected double xAccel;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    // constructor
    public MapEntity(BlockMap bm) {
        blockMap = bm;
        sizeOfBlocks = bm.getSizeOfBlocks();
        animations = new Animations();
        lookingRight = true;
    }

    //Collision Checking booleans
    public boolean intersecting(MapEntity e) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = e.getRectangle();
        return r1.intersects(r2);
    }

    public boolean intersecting(Rectangle r) {
        return getRectangle().intersects(r);
    }

    public boolean contains(MapEntity e) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = e.getRectangle();
        return r1.contains(r2);
    }

    public boolean contains(Rectangle r) {
        return getRectangle().contains(r);
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) x - hbWidth / 2, (int) y - hbHeight / 2, hbWidth, hbHeight
        );
    }

    public void calculateCorners(double x, double y) {
        int leftBlock = (int) (x - hbWidth / 2) / sizeOfBlocks;
        int rightBlock = (int) (x + hbWidth / 2 - 1) / sizeOfBlocks;
        int topBlock = (int) (y - hbHeight / 2) / sizeOfBlocks;
        int bottomBlock = (int) (y + hbHeight / 2 - 1) / sizeOfBlocks;
        if (topBlock < 0 || bottomBlock >= blockMap.getRows()
                || leftBlock < 0 || rightBlock >= blockMap.getColumns()) {
            topLeftCorner = topRightCorner = bottomLeftCorner = bottomRightCorner = false;
            return;
        }
        int tl = blockMap.getTangible(topBlock, leftBlock);
        int tr = blockMap.getTangible(topBlock, rightBlock);
        int bl = blockMap.getTangible(bottomBlock, leftBlock);
        int br = blockMap.getTangible(bottomBlock, rightBlock);
        topLeftCorner = tl != Block.INTANGIBLE;
        topRightCorner = tr != Block.INTANGIBLE;
        bottomLeftCorner = bl != Block.INTANGIBLE;
        bottomRightCorner = br != Block.INTANGIBLE;
    }

    public void checkBlockMapCollision() {

        currX = (int) x / sizeOfBlocks;
        currY = (int) y / sizeOfBlocks;

        destX = x + dx;
        destY = y + dy;

        tempX = x;
        tempY = y;

        calculateCorners(x, destY);
        if (dy < 0) {
            if (topLeftCorner || topRightCorner) {
                dy = 0;
                tempY = currY * sizeOfBlocks + hbHeight / 2;
            } else {
                tempY += dy;
            }
        }
        if (dy > 0) {
            if (bottomLeftCorner || bottomRightCorner) {
                dy = 0;
                falling = false;
                tempY = (currY + 2) * sizeOfBlocks - hbHeight / 2;
            } else {
                tempY += dy;
            }
        }

        calculateCorners(destX, y);
        if (dx < 0) {
            if (topLeftCorner || bottomLeftCorner) {
                dx = 0;
                tempX = currX * sizeOfBlocks + hbWidth / 2;
            } else {
                tempX += dx;
            }
        }
        if (dx > 0) {
            if (topRightCorner || bottomRightCorner) {
                dx = 0;
                tempX = (currX + 1) * sizeOfBlocks - hbWidth / 2;
            } else {
                tempX += dx;
            }
        }

        if (!falling) {
            calculateCorners(x, destY + 1);
            if (!bottomLeftCorner && !bottomRightCorner) {
                falling = true;
            }
        }

    }

    //Getters
    public int getx() {
        return (int) x;
    }

    public int gety() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHBWidth() {
        return hbWidth;
    }

    public int getHBHeight() {
        return hbHeight;
    }

    public boolean islookingRight() {
        return lookingRight;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        xmap = blockMap.getX();
        ymap = blockMap.getY();
    }

    //Control
    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setJumping(boolean b) {
        jumping = b;
    }

    public boolean outsideScreen() {
        return x + xmap + width < 0
                || x + xmap - width > GamePanel.WIDTH
                || y + ymap + height < 0
                || y + ymap - height > GamePanel.HEIGHT;
    }

    public void draw(java.awt.Graphics2D g) {
        setMapPosition();
        if (lookingRight) {
            g.drawImage(
                    animations.getImage(),
                    (int) (x + xmap - width / 2),
                    (int) (y + ymap - height / 2),
                    null
            );
        } else {
            g.drawImage(
                    animations.getImage(),
                    (int) (x + xmap - width / 2 + width),
                    (int) (y + ymap - height / 2),
                    -width,
                    height,
                    null
            );
        }
        /*
                // draw collision box
                g.setColor(java.awt.Color.BLUE);
		Rectangle r = getRectangle();
		r.x += xmap;
                r.y += ymap;
		g.draw(r);
         */
    }

    public void setXAccel(double i) {
        xAccel = i;
    }

    public void setJumpStart(double j) {
        jumpStart = j;
    }

    public void setFallSpeed(double f) {
        fallSpeed = f;
    }

    public void setMaxFallSpeed(double mf) {
        maxFallSpeed = mf;
    }

    public double getDY() {
        return dy;
    }

    public void setDY(double d) {
        dy = d;
    }

    public double get1PixelUnder() {
        return (int) (y + hbHeight / 2 - 1);
    }

    public double get1PixelRight() {
        return (int) (x + hbWidth / 2 - 1);
    }

}
