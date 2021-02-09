/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapObject;

/**
 *
 * @author bakup
 */
public class Save {
    private static int lives = 3;
	private static int hp = 1;
	
	public static void init() {
		lives = 3;
		hp = 1;
	}
	
	public static int getLives(){
            return lives;
        }
	public static void setLives(int i){
            lives = i;
        }
	
	public static int getHP(){
            return hp;
        }
	public static void setHP(int i){
            hp = i; 
        }

}
