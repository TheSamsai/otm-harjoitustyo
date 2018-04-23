/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities;

import dungeoncrawler.game.entities.item.Item;
import java.util.ArrayList;

/**
 *
 * @author sami
 */
public class Player implements Entity {
    private int x;
    private int y;
    private int hp;
    private int ac;
    private int xp;
    private ArrayList<Item> inventory;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.hp = 20;
        this.ac = 0;
        inventory = new ArrayList<>();
    }
    
    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void attack(Entity other) {
        other.damage(5);
    }

    @Override
    public void damage(int dmg) {
        
    }

    @Override
    public int getHP() {
        return hp;
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
