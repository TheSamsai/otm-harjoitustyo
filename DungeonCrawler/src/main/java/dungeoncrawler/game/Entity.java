/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

/**
 *
 * @author sami
 */
public interface Entity {
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public void attack(Entity other);
    public void damage(int dmg);
    public int getHP();
}
