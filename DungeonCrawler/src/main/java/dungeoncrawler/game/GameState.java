/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

import dungeoncrawler.game.entities.monster.Monster;
import dungeoncrawler.game.entities.Player;
import dungeoncrawler.game.entities.Entity;
import dungeoncrawler.game.entities.item.Item;
import dungeoncrawler.game.level.Level;
import dungeoncrawler.game.level.Room;
import dungeoncrawler.game.level.Tile;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sami
 */
enum MenuType {
    INVENTORY
}

public class GameState {
    private Level level;
    private ArrayList<Monster> monsters;
    private ArrayList<Item> loot;
    Player player;
    boolean inMenu;
    MenuType menuType;
    ArrayList<MenuItem> menuItems;
    
    public GameState() {
        player = new Player(30, 20);
        generateLevel(); 
    }
    
    public void generateLevel() {
        this.level = new Level();
        monsters = new ArrayList<>();
        loot = new ArrayList<>();
        
        movePlayerToRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        
        spawnItemInRandomRoom();
        spawnItemInRandomRoom();
        spawnItemInRandomRoom();
        spawnItemInRandomRoom();
        spawnItemInRandomRoom();
        spawnItemInRandomRoom();
    }
    
    public Entity getEntityAt(int x, int y) {
        for (Monster m : monsters) {
            if (m.getX() == x && m.getY() == y) {
                return m;
            }
        }
        
        if (player.getX() == x && player.getY() == y) {
            return player;
        } 
        
        return null;
    }
    
    public Item getItemAt(int x, int y) {
        for (Item i : loot) {
            if (i.getX() == x && i.getY() == y) {
                return i;
            }
        }
        
        return null;
    }
    
    public void spawnMonsterAt(int x, int y) {
        monsters.add(new Monster("slime", "slime.png", 5, 0, 0, 0, 1, x, y));
    }
    
    public void spawnMonsterInRandomRoom() {
        Room room = level.getRooms().get(new Random().nextInt(level.getRooms().size() - 1) + 1);
        
        int rx = room.getX() + (room.getW() / 2);
        int ry = room.getY() + (room.getH() / 2);
        
        spawnMonsterAt(rx, ry);
    }
    
    public void spawnItemAt(int x, int y) {
        loot.add(new Item(player, "Potion of Health", "green_potion.png", x, y));
    }
    
    public void spawnItemInRandomRoom() {
        Room room = level.getRooms().get(new Random().nextInt(level.getRooms().size() - 1) + 1);
        
        int rx = room.getX() + (room.getW() / 2);
        int ry = room.getY() + (room.getH() / 2);
        
        spawnItemAt(rx, ry);
    }
    
    public void movePlayerToRandomRoom() {
        Room room = level.getRooms().get(0);
        player.setX(room.getX() + (room.getW() / 2));
        player.setY(room.getY() + (room.getH() / 2));
    }
    
    public boolean canMoveUp(Entity entity) {
        if (level.getMap()[entity.getY() - 1][entity.getX()].passable()) {
            Entity other = getEntityAt(entity.getX(), entity.getY() - 1);
            
            if (other == null) {
                return true;
            } else if (entity != player && other == player) {
                return true;
            } else if (entity == player) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public boolean canMoveDown(Entity entity) {
        if (level.getMap()[entity.getY() + 1][entity.getX()].passable()) {
            Entity other = getEntityAt(entity.getX(), entity.getY() + 1);
            
            if (other == null) {
                return true;
            } else if (entity != player && other == player) {
                return true;
            } else if (entity == player) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public boolean canMoveLeft(Entity entity) {
        if (level.getMap()[entity.getY()][entity.getX() - 1].passable()) {
            Entity other = getEntityAt(entity.getX() - 1, entity.getY());
            
            if (other == null) {
                return true;
            } else if (entity != player && other == player) {
                return true;
            } else if (entity == player) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public boolean canMoveRight(Entity entity) {
        if (level.getMap()[entity.getY()][entity.getX() + 1].passable()) {
            Entity other = getEntityAt(entity.getX() + 1, entity.getY());
            
            if (other == null) {
                return true;
            } else if (entity != player && other == player) {
                return true;
            } else if (entity == player) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public void moveUp(Entity entity) {
        if (canMoveUp(entity)) {
            Entity other = getEntityAt(entity.getX(), entity.getY() - 1);
            
            if (other != null) {
                combat(entity, other);
            } else {
                entity.setY(entity.getY() - 1);
            }
        }
    }
    
    public void moveDown(Entity entity) {
        if (canMoveDown(entity)) {
            Entity other = getEntityAt(entity.getX(), entity.getY() + 1);
            
            if (other != null) {
                combat(entity, other);
            } else {
                entity.setY(entity.getY() + 1);
            }
        }
    }
    
    public void moveLeft(Entity entity) {
        if (canMoveLeft(entity)) {
            Entity other = getEntityAt(entity.getX() - 1, entity.getY());
            
            if (other != null) {
                combat(entity, other);
            } else {
                entity.setX(entity.getX() - 1);
            }
        }
    }
    
    public void moveRight(Entity entity) {
        if (canMoveRight(entity)) {
            Entity other = getEntityAt(entity.getX() + 1, entity.getY());
            
            if (other != null) {
                combat(entity, other);
            } else {
                entity.setX(entity.getX() + 1);
            }
        }
    }
    
    public void combat(Entity attacker, Entity defender) {
        attacker.attack(defender);
        
        if (defender.getHP() <= 0 && defender != player) {
            monsters.remove(defender);
        }
    }
    
    public void menuUp() {
        for (int x = 0; x < menuItems.size(); x++) {
            if (menuItems.get(x).selected) {
                if (x > 0) {
                    menuItems.get(x).selected = false;
                    menuItems.get(x - 1).selected = true;
                    break;
                } else {
                    menuItems.get(x).selected = false;
                    menuItems.get(menuItems.size() - 1).selected = true;
                    break;
                }
            }
        }
    }
    
    public void menuDown() {
        for (int x = 0; x < menuItems.size(); x++) {
            if (menuItems.get(x).selected) {
                if (x < menuItems.size() - 1) {
                    menuItems.get(x).selected = false;
                    menuItems.get(x + 1).selected = true;
                    break;
                } else {
                    menuItems.get(x).selected = false;
                    menuItems.get(0).selected = true;
                    break;
                }
            }
        }
    }
    
    public void menuEnter(ArrayList<String> input) {
        if (menuType == MenuType.INVENTORY) {
            if (input.contains("ENTER")) {
                for (int x = 0; x < menuItems.size(); x++) {
                    if (menuItems.get(x).selected) {
                        player.getInventory().get(x).onUse();
                        break;
                    }
                }
            } else {
                try {
                    System.out.println(input.get(0).replace("DIGIT", ""));
                    int index = Integer.parseInt(input.get(0).replace("DIGIT", ""));
                    if (player.getInventory().size() <= index) {
                        player.getInventory().get(index - 1).onUse();
                    }
                } catch (Exception e) {
                    inMenu = false;
                }
            }
        }
                
        inMenu = false;
    }
    
    public void processInput(ArrayList<String> input) {
        System.out.println(input.get(0));
        
        if (inMenu) {
            if (input.contains("ESCAPE")) {
                inMenu = false;
            } else if (input.contains("DOWN")) {
                menuDown();
            } else if (input.contains("UP")) {
                menuUp();
            } else {
                menuEnter(input);
            }
        } else {
            if (input.contains("UP")) {
                moveUp(player);
            } else if (input.contains("DOWN")) {
                moveDown(player);
            } else if (input.contains("LEFT")) {
                moveLeft(player);
            } else if (input.contains("RIGHT")) {
                moveRight(player);
            } else if (input.contains("I")) {
                inMenu = true;
                menuType = MenuType.INVENTORY;
                menuItems = new ArrayList<>();
            
                int counter = 1;
                for (Item i : player.getInventory()) {
                    menuItems.add(new MenuItem("" + counter, i.getName()));
                    counter++;
                }
                
                if (!menuItems.isEmpty()) {
                    menuItems.get(0).selected = true;
                }
            }
        }
        
        Item i = getItemAt(player.getX(), player.getY());
        
        if (i != null) {
            System.out.println("Picked up!");
            player.getInventory().add(i);
            loot.remove(i);
        }
        
        if (level.getMap()[player.getY()][player.getX()].getType() == Tile.staircase) {
            generateLevel();
        }
        
        for (Monster m : monsters) {
            m.decision(this);
        }
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public Entity getPlayer() {
        return this.player;
    }
    
    public boolean getInMenu() {
        return this.inMenu;
    }
    
    public ArrayList<MenuItem> getMenuItems() {
        return this.menuItems;
    }
    
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }
    
    public ArrayList<Item> getItems() {
        return this.loot;
    }
}
