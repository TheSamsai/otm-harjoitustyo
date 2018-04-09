/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author sami
 */
public class Tile {
    public static int CHASM = 0;
    public static int WALL = 2;
    public static int FLOOR = 1;
    public static int WATER = 3;
    public static int LAVA = 4;
    
    private int type;
    
    public Tile(int type) {
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
}
