/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Audio.Audio_Player;
import ppip_final.GamePanel;
import MapObject.LevelEnder;

import MapObject.Player;
import MapObject.Save;
import Handlers.Keys;
import ppip_final.GamePanel;
import map.Background;
import map.BlockMap;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Level3State extends GameState {

    private Background background;
    private Player player;
    private BlockMap blockMap;
    private LevelEnder teleport;

    // events
    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;

    public Level3State(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        // backgrounds
        background = new Background("/Backgrounds/LVL3.png", 0);

        // tilemap
        blockMap = new BlockMap(80);
        blockMap.loadBlocks("/Tilesets/tileset3.gif");
        blockMap.loadMap("/Maps/level3.map");
        blockMap.setPosition(160, 160);
        blockMap.setBounds(
                blockMap.getMapWidth() - 1 * blockMap.getSizeOfBlocks(),
                blockMap.getMapHeight() - 2 * blockMap.getSizeOfBlocks(),
                0, 0
        );
        blockMap.setTween(1);

        // player
        player = new Player(blockMap);
        player.setPosition(300, 161);
        player.setHealth(Save.getHP());
        player.setLives(Save.getLives());

        //Player Moon physics
        player.setXAccel(1.6 * 1.16);
        player.setMaxFallSpeed(20.0 / 6);
        player.setFallSpeed(1.5 / 6);
        player.setJumpStart(-20.8 * 1.16);

        // teleport
        teleport = new LevelEnder(blockMap, "/Sprites/Player/LvLEND3.png");
        teleport.setPosition(8900 - 40, 440 + 20);

        // start event
        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        // sfx
        Audio_Player.load("/Sounds/teleport.wav", "teleport");

    }

    public void update() {

        // check keys
        handleInput();

        // check if end of level event should start
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }

        // check if player dead event should start
        if (player.getHealth() == 0 || player.gety() > blockMap.getMapHeight()) {
            eventDead = blockInput = true;
        }

        // play events
        if (eventStart) {
            eventStart();
        }
        if (eventDead) {
            eventDead();
        }
        if (eventFinish) {
            eventFinish();
        }

        // update player
        player.update();

        // update tilemap
        blockMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );
        blockMap.update();
        blockMap.setBoundaries();

        // update teleport
        teleport.update();

    }

    public void draw(Graphics2D g) {

        // draw background
        background.draw(g);

        // draw tilemap
        blockMap.draw(g);

        // draw player
        player.draw(g);

        // draw teleport
        teleport.draw(g);

        // draw transition boxes
        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(true);
        }
        if (blockInput || player.getHealth() == 0) {
            return;
        }
        player.setUp(Keys.keyState[Keys.UP]);
        player.setLeft(Keys.keyState[Keys.LEFT]);
        player.setDown(Keys.keyState[Keys.DOWN]);
        player.setRight(Keys.keyState[Keys.RIGHT]);
        player.setJumping(Keys.keyState[Keys.BUTTON1]);
        player.setSprinting(Keys.keyState[Keys.BUTTON2]);

    }

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
    // reset level
    private void reset() {
        player.reset();
        player.setPosition(300, 161);
        blockInput = true;
        eventCount = 0;
        //blockMap.setShaking(false, 0);
        eventStart = true;
        eventStart();

    }

    // level started
    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }

        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            tb.clear();

        }

    }

    // player has died
    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getLives() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    // finished level
    private void eventFinish() {
        eventCount++;
        if (eventCount == 1) {
            Audio_Player.play("teleport");
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
            Audio_Player.stop("teleport");
        }
        if (eventCount == 180) {
            //Save.setHealth(player.getHealth());
            Save.setLives(player.getLives());
            gsm.setState(GameStateManager.FINISHSTATE);
        }

    }

}
