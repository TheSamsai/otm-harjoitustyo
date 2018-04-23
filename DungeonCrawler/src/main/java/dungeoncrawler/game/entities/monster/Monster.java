/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities.monster;

import dungeoncrawler.game.GameState;
import dungeoncrawler.game.entities.Entity;
import java.util.Random;

/**
 *
 * @author sami
 */
public class Monster implements Entity {
    
    private int x;
    private int y;
    private String name;
    private String spritePath; 
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
        this.spritePath = sprite;
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
        return this.spritePath;
    }
    
    public void decision(GameState gs) {
        if (gs.getPlayer().getX() > this.x && gs.canMoveRight(this)) {
            gs.moveRight(this);
        } else if (gs.getPlayer().getX() < this.x && gs.canMoveLeft(this)) {
            gs.moveLeft(this);
        } else if (gs.getPlayer().getY() > this.y && gs.canMoveDown(this)) {
            gs.moveDown(this);
        } else if (gs.getPlayer().getY() < this.y && gs.canMoveUp(this)) {
            gs.moveUp(this);
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
