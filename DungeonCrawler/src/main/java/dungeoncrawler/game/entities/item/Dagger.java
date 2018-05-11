/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.item;

import dungeoncrawler.game.entities.Player;

/**
 * Basic weapon the player spawns with
 * @author sami
 */
public class Dagger extends Item {
    
    public Dagger(Player player, int x, int y) {
        super(player, "Dagger", "dagger.png", x, y);
    }
    
    @Override
    public void use() {
        super.getPlayer().getWeapon().unwield();
        super.getPlayer().setWeapon(this);
        super.getPlayer().increaseDamage(3);
        super.use();
    }
    
    public void unwield() {
        super.getPlayer().decreaseDamage(3);
        super.getPlayer().getInventory().add(this);
    }
}
