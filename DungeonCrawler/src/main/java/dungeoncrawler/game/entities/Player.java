/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.entities;

import dungeoncrawler.game.entities.item.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class containing player-specific information such as health and various
 * helper methods
 * @author sami
 */
public class Player implements Entity {
    private int x;
    private int y;
    private int level;
    private int hp;
    private int ac;
    private int damage;
    private int xp;
    private ArrayList<Item> inventory;
    private Item weapon;
    private Item armor;
    private Item shield;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 1;
        this.hp = 20;
        this.ac = 0;
        this.damage = 1;
        inventory = new ArrayList<>();
        Dagger startingDagger = new Dagger(this, 0, 0);
        startingDagger.use();
        armor = null;
        shield = null;
    }
    
    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Method for the player attacking another entity,
     * damage is calculated as player.damage + player.level
     * @param other Another Entity to be attacked
     * @return The amount of damage that was done to the entity
     */
    @Override
    public int attack(Entity other) {
        int actualDamage = other.damage(new Random().nextInt(damage + level));
        return actualDamage;
    }

    /**
     * Damage the player with a certain amount of incoming damage
     * @param dmg Raw damage to be done to the player
     * @return The actual damage that passed player's defence
     */
    @Override
    public int damage(int dmg) {
        int actualDamage = dmg - ac;
        
        if (actualDamage > 0) {
            hp -= actualDamage;
        }
        
        return actualDamage;
    }
    
    /**
     * Increase player's health up to player's maximum health
     * @param hp
     */
    public void heal(int hp) {
        if (this.hp + hp > maxHP()) {
            this.hp = maxHP();
        } else {
            this.hp += hp;
        }
        
    }
    
    /**
     * Give the player experience points, if points go above 100
     * player's level increases
     * @param exp Experience points given to the player
     * @return True if player levels up, false otherwise
     */
    @Override
    public boolean grantXP(int exp) {
        xp += exp;
        
        if (xp > 100 * level) {
            xp = 0;
            level++;
            heal(2);
            return true;
        }
        return false;
    }

    @Override
    public int getHP() {
        return hp;
    }
    
    public int getAC() {
        return ac;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int dmg) {
        damage = dmg;
    } 
    
    /**
     * Calculates and returns player's maximum health
     * maxHP is calculated as 20 + player.level * 2
     * @return Player's current maximum health 
     */
    public int maxHP() {
        return 20 + level * 2;
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public Item getWeapon() {
        if (weapon == null) {
            return new Item(this, "None", "", 0, 0);
        }
        
        return weapon;
    }
    
    public Item getArmor() {
        if (armor == null) {
            return new Item(this, "None", "", 0, 0);
        }
        
        return armor;
    }
    
    public Item getShield() {
        if (shield == null) {
            return new Item(this, "None", "", 0, 0);
        }
        
        return shield;
    }
    
    public void setWeapon(Item wep) {
        weapon = wep;
    }
    
    public void setArmor(Item armor) {
        this.armor = armor;
    }
    
    public void setShield(Item shield) {
        this.shield = shield;
    }

    @Override
    public String getName() {
        return "You";
    }

    public void increaseAC(int i) {
        this.ac += i;
    }
    
    public void increaseDamage(int i) {
        this.damage += i;
    }
    
    public void decreaseAC(int i) {
        this.ac += i;
    }
    
    public void decreaseDamage(int i) {
        this.damage -= i;
    }

    @Override
    public int xpGain() {
        return 0;
    }
}
