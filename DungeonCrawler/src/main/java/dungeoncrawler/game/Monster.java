/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

import java.util.Random;

/**
 *
 * @author sami
 */
public class Monster implements Entity, AI {
    
    private int x;
    private int y;
    private String name;
    private String sprite_path; 
    private int hp;
    private int armor;
    private int accuracy;
    private int damageMin;
    private int damageMax;
    
    public Monster(String name, String sprite, int hp, int armor, int accuracy,
            int damageMin, int damageMax, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.sprite_path = sprite;
        this.hp = hp;
        this.armor = armor;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
    
    public String getSprite() {
        return this.sprite_path;
    }
    
    @Override
    public void AIDecision(GameState gs) {
        if (gs.getPlayer().getX() > this.x && gs.moveEntityDirection(this, "RIGHT")) {
            return;
        } else if (gs.getPlayer().getX() < this.x && gs.moveEntityDirection(this, "LEFT")) {
            return;
        } else if (gs.getPlayer().getY() > this.y && gs.moveEntityDirection(this, "DOWN")) {
            return;
        } else if (gs.getPlayer().getY() < this.y && gs.moveEntityDirection(this, "UP")) {
            return;
        }
    }

    @Override
    public void attack(Entity other) {
        other.damage(new Random().nextInt(damageMax - damageMin) + damageMin);
    }

    @Override
    public void damage(int dmg) {
        int damageDone = dmg - armor;
        
        if (damageDone > 0) {
            hp -= damageDone;
        }
    }

    @Override
    public int getHP() {
        return hp;
    }
    
    
}
