/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

import db.Highscore;
import db.HighscoreDao;
import dungeoncrawler.game.entities.monster.*;
import dungeoncrawler.game.entities.Player;
import dungeoncrawler.game.entities.Entity;
import dungeoncrawler.game.entities.item.*;
import dungeoncrawler.game.level.Level;
import dungeoncrawler.game.level.Room;
import dungeoncrawler.game.level.Tile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author sami
 */
enum MenuType {
    INVENTORY,
    NAMEENTRY,
    SCOREBOARD,
}

/**
 * The class modelling the current state of the game, containing
 * various functions for probing and modifying the state.
 */
public class GameState {
    private HighscoreDao highscoredb;
    private Level level;
    private int stage;
    private ArrayList<Monster> monsters;
    private ArrayList<Item> loot;
    Player player;
    boolean inMenu;
    MenuType menuType;
    ArrayList<MenuItem> menuItems;
    Random rng;
    ArrayList<String> actionLog;
    
    public GameState() {
        highscoredb = new HighscoreDao("jdbc:sqlite:scores.db");
        actionLog = new ArrayList<>();
        player = new Player(30, 20);
        rng = new Random();
        stage = 1;
        generateLevel();
    }
    
    /**
    * Generate a new level and place some items and monsters in it
    * according to the current stage. Moves the player in one of the safe
    * rooms of the level.
    */
    public void generateLevel() {
        this.level = new Level();
        monsters = new ArrayList<>();
        loot = new ArrayList<>();
        
        movePlayerToRandomRoom();
        
        for (int x = 0; x < stage; x++) {
            spawnMonsterInRandomRoom();
            spawnMonsterInRandomRoom();
        }
        
        int items = rng.nextInt(5);
        for (int x = 0; x < items; x++) {
            spawnItemInRandomRoom(randomItem());
        }
        
        if (stage == 10) {
            spawnItemInRandomRoom(new Treasure(player, 0, 0));
        }
    }
    
    /**
     * Finds an Entity in the given X,Y coordinate. Returns null if no
     * applicable Entity is found.
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return Entity, null if not found
     */
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
    
    /**
     * Finds and returns an Item at given X,Y coordinate, null if not found
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return Item, null if not found
     */
    public Item getItemAt(int x, int y) {
        for (Item i : loot) {
            if (i.getX() == x && i.getY() == y) {
                return i;
            }
        }
        
        return null;
    }
    
    /**
     * Spawns a Monster on the level in a given X, Y coordinate
     * @param m Monster to be spawned
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public void spawnMonsterAt(Monster m, int x, int y) {
        m.setX(x);
        m.setY(y);
        monsters.add(m);
    }
    
    /**
     * Creates an instance of a random monster and returns it.
     * @return A random Monster instance
     */
    public Monster randomMonster() {
        int monsterNum = rng.nextInt(3);
        
        if (monsterNum == 0) {
            return new Slime(0, 0);
        } else if (monsterNum == 1) {
            return new Skeleton(0, 0);
        } else {
            return new Beholder(0, 0);
        }
    }
    
    /**
     * Creates a random Monster instance and places it in a room at random.
     * Does not spawn a monster in the level spawn room.
     */
    public void spawnMonsterInRandomRoom() {
        Room room = level.getRooms().get(rng.nextInt(level.getRooms().size() - 1) + 1);
        
        int rx = room.getX() + (rng.nextInt(room.getW() - 2) + 1);
        int ry = room.getY() + (rng.nextInt(room.getH() - 2) + 1);
        
        spawnMonsterAt(randomMonster(), rx, ry);
    }
    
    /**
     * Spawns a given item at an X,Y coordinate on the level.
     * @param i Item to be spawned
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public void spawnItemAt(Item i, int x, int y) {
        i.setX(x);
        i.setY(y);
        loot.add(i);
    }
    
    /**
     * Creates a random Item instance and returns it.
     * @return randomly created Item
     */ 
    public Item randomItem() {
        int itemNum = rng.nextInt(4);
        
        if (itemNum == 0) {
            return new HealthPotion(player, 0, 0);
        } else if (itemNum == 1) {
            return new Dagger(player, 0, 0);
        } else if (itemNum == 2) {
            return new Shortsword(player, 0, 0);
        } else {
            return new Roundshield(player, 0, 0);
        }
    }
    
    /**
     * Spawns a supplied Item in a random room on the level.
     * Items are not spawned in the spawn room.
     * @param item Item instance to be spawned
     */
    public void spawnItemInRandomRoom(Item item) {
        Room room = level.getRooms().get(new Random().nextInt(level.getRooms().size() - 1) + 1);
        
        int rx = room.getX() + (rng.nextInt(room.getW() - 2) + 1);
        int ry = room.getY() + (rng.nextInt(room.getH() - 2) + 1);
        
        spawnItemAt(item, rx, ry);
    }
    
    /**
     * Takes the first room that was generated on the level and moves the player
     * there.
     */
    public void movePlayerToRandomRoom() {
        Room room = level.getRooms().get(0);
        player.setX(room.getX() + (room.getW() / 2));
        player.setY(room.getY() + (room.getH() / 2));
    }
    
    /**
     * Checks if an Entity can move upwards without being blocked by friendly
     * entities or terrain.
     * @param entity Entity whose movement is checked
     * @return true if movement is possible, false otherwise.
     */
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
    
    /**
     * Checks if an Entity can move upwards without being blocked by friendly
     * entities or terrain.
     * @param entity Entity whose movement is checked
     * @return true if movement is possible, false otherwise.
     */
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
    
    /**
     * Checks if an Entity can move upwards without being blocked by friendly
     * entities or terrain.
     * @param entity Entity whose movement is checked
     * @return true if movement is possible, false otherwise.
     */
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
    
    /**
     * Checks if an Entity can move upwards without being blocked by friendly
     * entities or terrain.
     * @param entity Entity whose movement is checked
     * @return true if movement is possible, false otherwise.
     */
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
    
    /**
     * Moves an Entity if movement is possible. Ability to move is checked with
     * canMove_() methods.
     * @param entity Entity to move.
     */
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
    
    /**
     * Moves an Entity if movement is possible. Ability to move is checked with
     * canMove_() methods.
     * @param entity Entity to move.
     */
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
    
    /**
     * Moves an Entity if movement is possible. Ability to move is checked with
     * canMove_() methods.
     * @param entity Entity to move.
     */
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
    
    /**
     * Moves an Entity if movement is possible. Ability to move is checked with
     * canMove_() methods.
     * @param entity Entity to move.
     */
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
    
    /**
     * Initiates combat between two entities. Attacker will roll for damage
     * and the damage is compared against defender's armor class (AC).
     * @param attacker Entity that is attacking
     * @param defender Entity that is defending
     */
    public void combat(Entity attacker, Entity defender) {
        int doneDamage = attacker.attack(defender);
        
        if (doneDamage < 0) {
            doneDamage = 0;
        }
        
        log(attacker.getName() + " attacked " + defender.getName() + " doing " + doneDamage + " damage.");
        
        if (defender.getHP() <= 0 && defender != player) {
            boolean levelUp = attacker.grantXP(defender.xpGain());
            
            if (levelUp) {
                log("You feel stronger!");
            }
            
            monsters.remove(defender);
        } else if (defender.getHP() <= 0 && defender == player) {
            log("You have perished!");
            
            endgame();
        }
    }
    
    public void endgame() {
        inMenu = true;
        menuType = MenuType.NAMEENTRY;
        menuItems = new ArrayList<>();
            
        menuItems.add(new MenuItem("Enter your name: ", "Player"));
                
        if (!menuItems.isEmpty()) {
            menuItems.get(0).selected = true;
        }
    }
    
    /**
     * Moves up on a menu selection
     */
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
    
    /**
     * Moves down on a menu selection
     */
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
    
    /**
     * Uses an option on a menu
     * @param input Player input in a list
     */
    public void menuKey(ArrayList<String> input) {
        if (menuType == MenuType.INVENTORY) {
            if (input.contains("ENTER")) {
                for (int x = 0; x < menuItems.size(); x++) {
                    if (menuItems.get(x).selected) {
                        player.getInventory().get(x).use();
                        break;
                    }
                }
                 
                inMenu = false;
            }
        } else if (menuType == MenuType.NAMEENTRY) {
            if (input.contains("ENTER")) {
                int score = stage * 100 + (player.maxHP() - 22) * 10;
                
                for (Item i : player.getInventory()) {
                    if (i.getName() == "Unfathomable Treasures") {
                        score += 1000;
                    }
                }
                
                try {
                    highscoredb.addNewScore(new Highscore(menuItems.get(0).content, score));
                } catch (SQLException ex) {
                    System.out.println("DB connection failure");
                }
                
                menuType = MenuType.SCOREBOARD;
                menuItems = new ArrayList<>();
            
                try {
                    int x = 1;
                    
                    for (Highscore hscore : highscoredb.getScores()) {
                        menuItems.add(new MenuItem(x + ": ", hscore.getName() + " - " + hscore.getScore()));
                    }
                } catch (SQLException ex) {
                    System.out.println("DB connection failure");
                }
            } else if (input.contains("BACK_SPACE")) {
                String content = menuItems.get(0).content;
                if (content.length() > 0) {
                    menuItems.get(0).content = content.substring(0, content.length() - 1);
                }
            } else if (input.get(0).length() == 1) {
                String content = menuItems.get(0).content;
                menuItems.get(0).content = content.concat(input.get(0));
            }
        }
    }
    
    public void processMenuInput(ArrayList<String> input) {
        if (input.contains("ESCAPE")) {
            if (menuType == MenuType.INVENTORY) {
                inMenu = false;
            }
        } else if (input.contains("DOWN")) {
            menuDown();
        } else if (input.contains("UP")) {
            menuUp();
        } else {
            menuKey(input);
        }
    }
    
    /**
     * Takes user's input and processes it, executing various game actions
     * depending on the input.
     * @param input Player input in a list
     */
    public void processInput(ArrayList<String> input) {
        System.out.println(input.get(0));
        //actionLog.clear();
        
        if (inMenu) {
            processMenuInput(input);
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
            } else if (input.contains("N")) {
                inMenu = true;
                menuType = MenuType.NAMEENTRY;
                menuItems = new ArrayList<>();
            
                menuItems.add(new MenuItem("Enter your name: ", "Player"));
                
                if (!menuItems.isEmpty()) {
                    menuItems.get(0).selected = true;
                }
            }
        }
        
        Item i = getItemAt(player.getX(), player.getY());
        
        if (i != null) {
            log("Picked up " + i.getName() + ".");
            player.getInventory().add(i);
            loot.remove(i);           
        }
        
        if (level.getMap()[player.getY()][player.getX()].getType() == Tile.staircase) {
            log("You enter the next level of the dungeon.");
            stage++;
            generateLevel();
        }
        
        if (!inMenu) {
            for (Monster m : monsters) {
                m.decision(this);
            }
        }
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public Player getPlayer() {
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
    
    public ArrayList<String> getLog() {
        return this.actionLog;
    }
    
    public void log(String action) {
        actionLog.add(action);
    }
}
