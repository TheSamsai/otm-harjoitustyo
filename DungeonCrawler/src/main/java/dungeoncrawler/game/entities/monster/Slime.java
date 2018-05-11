/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.monster;

/**
 * Simple enemy with low health and damage
 * @author sami
 */
public class Slime extends Monster {
    
    public Slime(int x, int y) {
        super("Slime", "slime.png", 3, 0, 2, x, y);
    }
    
}
