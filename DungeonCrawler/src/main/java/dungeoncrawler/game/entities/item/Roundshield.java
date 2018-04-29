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
public class Roundshield extends Item {
    
    public Roundshield(Player player, int x, int y) {
        super(player, "Roundshield", "roundshield.png", x, y);
    }
    
    @Override
    public void use() {
        super.getPlayer().getShield().unwield();
        super.getPlayer().setShield(this);
        super.getPlayer().increaseAC(1);
        super.use();
    }
    
    public void unwield() {
        super.getPlayer().decreaseAC(1);
        super.getPlayer().getInventory().add(this);
    }
    
}
