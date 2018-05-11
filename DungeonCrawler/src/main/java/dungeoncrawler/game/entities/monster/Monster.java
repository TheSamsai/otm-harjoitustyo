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
 * Base class for enemy sub-classes to inherit from.
 * @author sami
 */
public class Monster implements Entity {
    
    private int x;
    private int y;
    private String name;
    private String spritePath; 
    private int hp;
    private int armor;
    private int damage;
    
    public Monster(String name, String sprite, int hp, int armor,
            int damage, int x, int y) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.spritePath = sprite;
        this.hp = hp;
        this.armor = armor;
        this.damage = damage;
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
    
    /**
     * Base AI decision system. By default monsters move towards a player in a
     * straight line, if possible. No special pathfinding.
     * @param gs Current game state
     */
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

    /**
     * Rolls for damage based on the monster's defined maximum damage
     * and attacks the defender with it, returning the actual damage done.
     * @param other The defending Entity
     * @return The amount of damage that was done
     */
    @Override
    public int attack(Entity other) {
        int actualDamage = other.damage(new Random().nextInt(damage));
        return actualDamage;
    }

    /**
     * Damages this monster for a certain amount of damage, returning the
     * damage that wasn't blocked by the monster's armor class.
     * @param dmg Damage targeted against the monster
     * @return Unblocked damage that was done to the monster
     */
    @Override
    public int damage(int dmg) {
        int damageDone = dmg - armor;
        
        if (damageDone > 0) {
            hp -= damageDone;
        }
        
        return damageDone;
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public boolean grantXP(int exp) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int xpGain() {
        return 10;
    }
    
    
}
