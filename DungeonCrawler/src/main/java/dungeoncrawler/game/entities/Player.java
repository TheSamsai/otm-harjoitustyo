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
 *
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

    @Override
    public int attack(Entity other) {
        int actualDamage = other.damage(new Random().nextInt(damage + level));
        return actualDamage;
    }

    @Override
    public int damage(int dmg) {
        int actualDamage = dmg - ac;
        
        if (actualDamage > 0) {
            hp -= actualDamage;
        }
        
        return actualDamage;
    }
    
    public void heal(int hp) {
        if (this.hp + hp > maxHP()) {
            this.hp = maxHP();
        } else {
            this.hp += hp;
        }
        
    }
    
    @Override
    public boolean grantXP(int exp) {
        xp += exp;
        
        if (xp > 100) {
            xp = 0;
            level++;
            heal(5);
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
