/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final.Game_State;

import Audio.Audio_Player;
import Handlers.Keys;
import ppip_final.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class Finish_State extends GameState {

    Image img;
    private Font font;
    private int currentChoice = 0;

    public Finish_State(GameStateManager gsm) {
        super(gsm);
        // fonts
        font = new Font("Times New Roman", Font.PLAIN, 50);
        Audio_Player.stop("menumusic");
        Audio_Player.load("/Music/CreditsMusic.wav", "creditsmusic");
        Audio_Player.load("/Sounds/menuselect.wav", "menuselect");
        Audio_Player.loop("creditsmusic", 600, Audio_Player.getFrames("creditsmusic") - 2200);
        Audio_Player.VolumeControlNum("creditsmusic", (float) 0.7);
        Audio_Player.playing = "creditsmusic";

    }

    public void init() {
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        g.drawString("GAME FINISHED", 10, 50);
        g.setFont(font);
        g.drawString("Congratulations. You have cleared", 10, 100);
        g.drawString("the game and helped the character", 10, 140);
        g.drawString("pass his final project by getting", 10, 180);
        g.drawString("a moonstone. However, he unexpectly", 10, 220);
        g.drawString("met an alien on the moon!!! Does", 10, 260);
        g.drawString("extraterristorial life really exists?", 10, 300);
        g.drawString("Find out more in the next Game in 2021", 10, 340);
        g.drawString("with more physics features than the current", 10, 380);
        g.drawString("game with friction, springs and waves.", 10, 420);
        g.drawString("The chracter is waiting for your return! :).", 10, 460);
        g.drawString("Thank you for playing the game!", 10, 500);
        if (currentChoice == 0) {
            g.fillOval(700, 470, 30, 30);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            Audio_Player.play("menuselect", 0);
            gsm.setState(GameStateManager.CREDITSSTATE);

        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }
    }

}
