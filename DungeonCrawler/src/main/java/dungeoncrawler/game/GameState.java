/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sami
 */
public class GameState {
    private Level level;
    private ArrayList<Monster> monsters; 
    Player player;
    
    public GameState() {
        this.level = new Level();
        monsters = new ArrayList<>();
        
        player = new Player(30, 20);
        movePlayerToRandomRoom();
        spawnMonsterInRandomRoom();
        spawnMonsterInRandomRoom();
        
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
    
    public void spawnMonsterAt(int x, int y) {
        monsters.add(new Monster("slime", "file:data/slime.png", 5, 0, 0, 0, 1, x, y));
    }
    
    public void spawnMonsterInRandomRoom() {
        Room room = level.getRooms().get(new Random().nextInt(level.getRooms().size()));
        
        int rx = room.getX() + (room.getW() / 2);
        int ry = room.getY() + (room.getH() / 2);
        
        spawnMonsterAt(rx, ry);
    }
    
    public void movePlayerToRandomRoom() {
        Room room = level.getRooms().get(new Random().nextInt(level.getRooms().size()));
        player.setX(room.getX() + (room.getW() / 2));
        player.setY(room.getY() + (room.getH() / 2));
    }
    
    public boolean moveEntityDirection(Entity entity, String direction) {
        if (direction.equals("UP")) {
            Entity other = getEntityAt(entity.getX(), entity.getY() - 1);
            
            if (other != null) {
                if (entity != player && other == player) {
                    entity.attack(other);
                    return true;
                } else if (entity == player) {
                    entity.attack(other);
                    
                    if (other.getHP() <= 0) {
                        monsters.remove(other);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            } else if (level.getMap()[entity.getY() - 1][entity.getX()].passable() && getEntityAt(entity.getX(), entity.getY() - 1) == null) {
                entity.setY(entity.getY() - 1);
                
                return true;
            } else {
                return false;
            }
        } else if (direction.equals("DOWN")) {
            Entity other = getEntityAt(entity.getX(), entity.getY() + 1);
            
            if (other != null) {
                if (entity != player && other == player) {
                    entity.attack(other);
                    return true;
                } else if (entity == player) {
                    entity.attack(other);
                    
                    if (other.getHP() <= 0) {
                        monsters.remove(other);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            } else if (level.getMap()[entity.getY() + 1][entity.getX()].passable()) {
                entity.setY(entity.getY() + 1);
                
                return true;
            } else {
                return false;
            }
        } else if (direction.equals("LEFT")) {
            Entity other = getEntityAt(entity.getX() - 1, entity.getY());
            
            if (other != null) {
                if (entity != player && other == player) {
                    entity.attack(other);
                    return true;
                } else if (entity == player) {
                    entity.attack(other);
                    
                    if (other.getHP() <= 0) {
                        monsters.remove(other);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            } else if (level.getMap()[entity.getY()][entity.getX() - 1].passable()) {
                entity.setX(entity.getX() - 1);
                
                return true;
            } else {
                return false;
            }
        } else if (direction.equals("RIGHT")) {
            Entity other = getEntityAt(entity.getX() + 1, entity.getY());
            
            if (other != null) {
                if (entity != player && other == player) {
                    entity.attack(other);
                    return true;
                } else if (entity == player) {
                    entity.attack(other);
                    
                    if (other.getHP() <= 0) {
                        monsters.remove(other);
                    }
                    
                    return true;
                } else {
                    return false;
                }
            } else if (level.getMap()[entity.getY()][entity.getX() + 1].passable()) {
                entity.setX(entity.getX() + 1);
                
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public void processInput(ArrayList<String> input) {
        if (input.contains("UP")) {
            moveEntityDirection(player, "UP");
        } else if (input.contains("DOWN")) {
            moveEntityDirection(player, "DOWN");
        } else if (input.contains("LEFT")) {
            moveEntityDirection(player, "LEFT");
        } else if (input.contains("RIGHT")) {
            moveEntityDirection(player, "RIGHT");
        }
        
        for (Monster m : monsters) {
            m.AIDecision(this);
        }
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public Entity getPlayer() {
        return this.player;
    }
    
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }
    
}
