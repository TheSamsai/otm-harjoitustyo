/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.level;

/**
 *
 * @author sami
 */
enum TileType {
    CHASM,
    WALL,
    FLOOR,
    WATER,
    LAVA,
    STAIRCASE
}

public class Tile {
    public static int chasm = 0;
    public static int wall = 2;
    public static int floor = 1;
    public static int water = 3;
    public static int lava = 4;
    public static int staircase = 5;
    
    private int type;
    
    public Tile(int type) {
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
    
    public boolean passable() {
        if (this.type == Tile.floor) {
            return true;
        } else if (this.type == Tile.water) {
            return true;
        } else if (this.type == Tile.staircase) {
            return true;
        } else {
            return false;
        }
    }
}