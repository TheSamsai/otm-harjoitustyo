/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.level;

/**
 * A container class for a rectangular room with getters, setters and
 * a method for checking if an X,Y coordinate falls within the room.
 * 
 */
public class Room {
    private int x;
    private int y;
    private int w;
    private int h;
    
    public Room(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getW() {
        return w;
    }
    
    public int getH() {
        return h;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Checks if an X,Y coordinate is within a given room
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return true if the point is within a room, false otherwise
     */
    public boolean contains(int x, int y) {
        return (x >= this.x && x <= this.x + this.w) && (y >= this.y && y <= this.y + this.h);
    }
}
