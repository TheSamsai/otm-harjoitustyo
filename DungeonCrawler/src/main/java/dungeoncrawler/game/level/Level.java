/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game.level;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that contains the relevant information to the current map, like
 * the various rooms and the types of tiles in each location.
 */
public class Level {
    private Tile[][] map;
    ArrayList<Room> rooms;
    
    public Level() {
        generateMap();
    }
    
    // Generate a level based on the old Rogue algorithm
    // 1. Divide level into a 3x3 grid
    // 2. Populate one grid with a fitting room
    // 3. Populate up to 7 grids, always connecting new room to a previous room

    /**
     * Generates a map based on the old Rogue algorithm:
     * Fill the entire map with empty tiles and  divide the level into 3x3 grid.
     * Then populate up to 7 grids with randomly sized rooms and connect them with 
     * a tunnel to a previously generated room.
     */
    public void generateMap() {
        this.map = new Tile[30][45];
        Random rng = new Random();
        
        for (int y = 0; y < 30; y++) {
            for (int x = 0; x < 45; x++) {
                map[y][x] = new Tile(Tile.chasm);
            }
        }
        
        boolean[][] grid = new boolean[3][3];
        
        rooms = new ArrayList<>();
        
        for (int a = 0; a < 7; a++) {
            int gridY = rng.nextInt(3);
            int gridX = rng.nextInt(3);
            
            if (!grid[gridY][gridX]) {
                int width = rng.nextInt(15 - 5) + 5;
                int height = rng.nextInt(10 - 5) + 5;
                Room newRoom = new Room(15 - width + gridX * 15 - 1, 10 - height + gridY * 10 - 1, width, height);
                
                grid[gridY][gridX] = true;
                carveRoom(newRoom);
                
                if (!rooms.isEmpty()) {
                    Room randomRoom = rooms.get(rng.nextInt(rooms.size()));
                    carveTunnel(newRoom.getX() + (newRoom.getW() / 2), newRoom.getY() + (newRoom.getH() / 2), randomRoom.getX() + (randomRoom.getW() / 2), randomRoom.getY() + (randomRoom.getH() / 2));
                }
                
                rooms.add(newRoom);
            }
        }
        
        Room lastRoom = rooms.get(rooms.size() - 1);
        
        // Add a staircase
        map[lastRoom.getY() + (lastRoom.getH() / 2)][lastRoom.getX() + (lastRoom.getW() / 2)] = new Tile(Tile.staircase);
    }
    
    /**
     * Creates a hallway between two X,Y coordinates by making direct lines.
     * Surrounding map tiles are turned into Wall tiles.
     * @param x Starting X-coordinate
     * @param y Starting Y-coordinate
     * @param dx Target X-coordinate
     * @param dy Target Y-coordinate
     */
    public void carveTunnel(int x, int y, int dx, int dy) {
        this.map[y][x] = new Tile(Tile.floor);
        
        // Fill surrounding empty tiles with walls
        if (this.map[y - 1][x].getType() == Tile.chasm) {
            this.map[y - 1][x] = new Tile(Tile.wall);
        } 
        if (this.map[y + 1][x].getType() == Tile.chasm) {
            this.map[y + 1][x] = new Tile(Tile.wall);
        } 
        if (this.map[y][x - 1].getType() == Tile.chasm) {
            this.map[y][x - 1] = new Tile(Tile.wall);
        } 
        if (this.map[y][x + 1].getType() == Tile.chasm) {
            this.map[y][x + 1] = new Tile(Tile.wall);
        } 
        if (this.map[y - 1][x - 1].getType() == Tile.chasm) {
            this.map[y - 1][x - 1] = new Tile(Tile.wall);
        } 
        if (this.map[y + 1][x - 1].getType() == Tile.chasm) {
            this.map[y + 1][x - 1] = new Tile(Tile.wall);
        } 
        if (this.map[y - 1][x + 1].getType() == Tile.chasm) {
            this.map[y - 1][x + 1] = new Tile(Tile.wall);
        } 
        if (this.map[y + 1][x + 1].getType() == Tile.chasm) {
            this.map[y + 1][x + 1] = new Tile(Tile.wall);
        }
        
        // Recursively call self on the direction headed
        if (x < dx) {
            carveTunnel(x + 1, y, dx, dy);
        } else if (x > dx) {
            carveTunnel(x - 1, y, dx, dy);
        } else if (y < dy) {
            carveTunnel(x, y + 1, dx, dy);
        } else if (y > dy) {
            carveTunnel(x, y - 1, dx, dy);
        }
    }
    
    /**
     * Makes a room in the level based on a Room object
     * Tiles inside the room are made to be Floor tiles, surrounding tiles
     * are turned into Wall tiles.
     * @param room Room to be carved onto the level.
     */
    public void carveRoom(Room room) {
        for (int y = room.getY(); y < room.getY() + room.getH(); y++) {
            for (int x = room.getX(); x < room.getX() + room.getW(); x++) {
                // Make sure we aren't overwriting existing floors
                if (this.map[y][x].getType() != Tile.floor) {
                    // If this is the top or the bottom row of the room, build a wall
                    if (y == room.getY() || y == room.getY() + room.getH() - 1) {
                        this.map[y][x] = new Tile(Tile.wall);
                    } else if (x == room.getX() || x == room.getX() + room.getW() - 1) {
                        // Same for the left and right side of the room
                        this.map[y][x] = new Tile(Tile.wall);
                    } else {
                        // Fill the tiles between with floors
                        this.map[y][x] = new Tile(Tile.floor);
                    }
                }
            }
        }
    }
    
    public Tile[][] getMap() {
        return this.map;
    }
    
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
