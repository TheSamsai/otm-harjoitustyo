/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.item;

/**
 *
 * @author sami
 */

import dungeoncrawler.game.entities.Player;

/**
 *
 * @author sami
 */
public class Shortsword extends Item {
    
    public Shortsword(Player player, int x, int y) {
        super(player, "Shortsword", "shortsword.png", x, y);
    }
    
    @Override
    public void use() {
        super.getPlayer().getWeapon().unwield();
        super.getPlayer().setWeapon(this);
        super.getPlayer().increaseDamage(5);
        super.use();
    }
    
    public void unwield() {
        super.getPlayer().decreaseDamage(5);
        super.getPlayer().getInventory().add(this);
    }
}
