/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.*;

import ppip_final.GamePanel;

/**
 *
 * @author bakup
 */
public class BlockMap {

    //Player parameters
    private int x;
    private int y;

    //Window boundaries
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private double tween;

    //the map parameters
    //in terms of pixels
    private int mapHeight;
    private int mapWidth;
    //in terms of blocks
    private int sizeOfBlocks;
    private int numberColumns;
    private int numberRows;

    private int[][] map;

    //Texture parameters
    private BufferedImage blockTexture;
    private Block[][] Blocks;
    private int numbBlocksAcross;

    //Drawing parameters
    private int offSetX;
    private int offSetY;
    private int rowsToDraw;
    private int columnsToDraw;

    public BlockMap(int blockSize) {
        this.sizeOfBlocks = blockSize;
        rowsToDraw = GamePanel.HEIGHT / sizeOfBlocks + 2;
        columnsToDraw = GamePanel.WIDTH / sizeOfBlocks + 2;
        tween = 0.07; //for smoother map sliding
    }

    public int getTangible(int row, int col) {
        try {
            int rc = map[row][col];
            int r = rc / numbBlocksAcross;
            int c = rc % numbBlocksAcross;
            return Blocks[r][c].getTangible();
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }

    }

    //List of getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getRows() {
        return numberRows;
    }

    public int getColumns() {
        return numberColumns;
    }

    public int getSizeOfBlocks() {
        return sizeOfBlocks;
    }

    public void loadBlocks(String s) {
        try {
            blockTexture = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            numbBlocksAcross = blockTexture.getWidth() / sizeOfBlocks;
            Blocks = new Block[4][numbBlocksAcross];

            BufferedImage subimage;
            for (int col = 0; col < numbBlocksAcross; col++) {
                subimage = blockTexture.getSubimage(
                        col * sizeOfBlocks,
                        0,
                        sizeOfBlocks,
                        sizeOfBlocks
                );
                Blocks[0][col] = new Block(subimage, Block.INTANGIBLE); //intangible block
                subimage = blockTexture.getSubimage(
                        col * sizeOfBlocks,
                        sizeOfBlocks,
                        sizeOfBlocks,
                        sizeOfBlocks
                );
                Blocks[1][col] = new Block(subimage, Block.TANGIBLE); //tangible block
                subimage = blockTexture.getSubimage(
                        col * sizeOfBlocks,
                        2 * sizeOfBlocks,
                        sizeOfBlocks,
                        sizeOfBlocks
                );
                Blocks[2][col] = new Block(subimage, Block.ICE); //Reduced friction block
                subimage = blockTexture.getSubimage(
                        col * sizeOfBlocks,
                        3 * sizeOfBlocks,
                        sizeOfBlocks,
                        sizeOfBlocks
                );
                Blocks[3][col] = new Block(subimage, Block.SLIDE); //Slide block
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String s) {

        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in)
            );

            numberColumns = Integer.parseInt(br.readLine());
            numberRows = Integer.parseInt(br.readLine());
            map = new int[numberRows][numberColumns];
            mapWidth = numberColumns * sizeOfBlocks;
            mapHeight = numberRows * sizeOfBlocks;

            xMin = GamePanel.WIDTH - mapWidth;
            xMax = 0;
            yMin = GamePanel.HEIGHT - mapHeight;
            yMax = 0;

            String delims = "\\s+";
            for (int row = 0; row < numberRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numberColumns; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTween(double d) {
        tween = d;
    }

    public void setBounds(int i1, int i2, int i3, int i4) {
        xMin = GamePanel.WIDTH - i1;
        yMin = GamePanel.WIDTH - i2;
        xMax = i3;
        yMax = i4;
    }

    public void setPosition(double x, double y) {

        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        setBoundaries();

        offSetY = (int) -this.x / sizeOfBlocks;
        offSetX = (int) -this.y / sizeOfBlocks;

    }

    public void setBoundaries() {
        if (x < xMin) {
            x = xMin;
        }
        if (y < yMin) {
            y = yMin;
        }
        if (x > xMax) {
            x = xMax;
        }
        if (y > yMax) {
            y = yMax;
        }
    }

    public void update() {
    }

    public void draw(Graphics2D g) {
        for (int row = offSetX; row < offSetX + rowsToDraw; row++) {

            if (row >= numberRows) {
                break;
            }

            for (int col = offSetY; col < offSetY + columnsToDraw; col++) {

                if (col >= numberColumns) {
                    break;
                }
                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numbBlocksAcross;
                int c = rc % numbBlocksAcross;

                g.drawImage(
                        Blocks[r][c].getTexture(),
                        (int) x + col * sizeOfBlocks,
                        (int) y + row * sizeOfBlocks,
                        null
                );

            }

        }
    }
}
