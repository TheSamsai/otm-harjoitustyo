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
public class Treasure extends Item {
    
    public Treasure(Player player, int x, int y) {
        super(player, "Unfathomable Treasures", "treasure.png", x, y);
    }
    
}
