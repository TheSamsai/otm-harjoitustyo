/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

/**
 *
 * @author sami
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
    
    public boolean contains(int x, int y) {
        return (x <= this.x + this.w) && (y <= this.y + this.h);
    }
    
    public boolean overlaps(Room room) {
        if (contains(room.x, room.y)) {
            return true;
        } else if (contains(room.x + room.w, room.y)) {
            return true;
        } else if (contains(room.x, room.y + room.h)) {
            return true;
        } else if (contains(room.x + room.w, room.y + room.h)) {
            return true;
        } else {
            return false;
        }
    }
}
