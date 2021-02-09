/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppip_final;

import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 *
 * @author Alex
 */
public class PPIP_Final {

   public static void main(String[] args) {
		JFrame window = new JFrame("PIP Platform Game Involving Physics");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
    
}
