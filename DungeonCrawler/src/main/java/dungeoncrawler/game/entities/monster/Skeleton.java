/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.monster;

/**
 *
 * @author sami
 */
public class Skeleton extends Monster {
    
    public Skeleton(int x, int y) {
        super("Skeleton", "skeleton.png", 6, 2, 3, x, y);
    }
    
    @Override
    public int xpGain() {
        return 100;
    }
}
