/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.item;

import dungeoncrawler.game.entities.Player;

/**
 * Base class which various items will inherit from
 * By default using an item removes it from user inventory
 * @author sami
 */
public class Item {
    private String name;
    private String sprite;
    private Player player;
    private int x, y;
    
    public Item(Player player, String name, String sprite, int x, int y) {
        this.player = player;
        this.name = name;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Base functionality for the use of an item. By default an item will remove
     * itself from the player's inventory when used. This is overridden for
     * item-specific functionality.
     */
    public void use() {
        this.player.getInventory().remove(this);
    }
    
    /**
     * Base method for functionality that should be called when an item is no
     * longer affecting the player. This method is overridden for item specific
     * functionality. By default items are assumed not to be unwielded.
     */
    public void unwield() {
        
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public String getSprite() {
        return sprite;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setX(int nX) {
        x = nX;
    }
    
    public void setY(int nY) {
        y = nY;
    }
}
