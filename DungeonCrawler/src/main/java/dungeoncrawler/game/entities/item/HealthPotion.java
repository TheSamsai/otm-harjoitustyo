/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.item;

import dungeoncrawler.game.entities.Player;

/**
 * Potion that heals the player when used
 * @author sami
 */
public class HealthPotion extends Item {
    
    public HealthPotion(Player player, int x, int y) {
        super(player, "Health Potion", "green_potion.png", x, y);
    }
    
    @Override
    public void use() {
        super.getPlayer().heal(10);
        super.use();
    }
}
