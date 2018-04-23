/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.item;

import dungeoncrawler.game.entities.Player;

/**
 *
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
    
    public void onUse() {
        this.player.getInventory().remove(this);
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
}
