/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.monster;

/**
 * Enemy with strong attacks but otherwise fairly weak armour and health
 * @author sami
 */
public class Beholder extends Monster {
    
    public Beholder(int x, int y) {
        super("Beholder", "beholder.png", 3, 1, 5, x, y);
    }
    
    @Override
    public int xpGain() {
        return 50;
    }
}
