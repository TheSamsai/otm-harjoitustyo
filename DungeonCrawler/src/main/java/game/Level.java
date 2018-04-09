/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sami
 */
public class Level {
    private Tile[][] map;
    
    public Level() {
        generateMap();
    }
    
    // Generate a level based on the old Rogue algorithm
    // 1. Divide level into a 3x3 grid
    // 2. Populate one grid with a fitting room
    // 3. Populate up to 7 grids, always connecting new room to a previous room
    public void generateMap() {
        this.map = new Tile[30][40];
        Random rng = new Random();
        
        for (int y = 0; y < 30; y++) {
            for (int x = 0; x < 40; x++) {
                map[y][x] = new Tile(Tile.CHASM);
            }
        }
        
        boolean[][] grid = new boolean[3][3];
        
        ArrayList<Room> rooms = new ArrayList<>();
        
        for (int a = 0; a < 7; a++) {
            int gridY = rng.nextInt(3);
            int gridX = rng.nextInt(3);
            
            if (!grid[gridY][gridX]) {
                int width = rng.nextInt(13 - 5) + 5;
                int height = rng.nextInt(10 - 5) + 5;
                Room newRoom = new Room(13 - width + gridX * 13, 10 - height + gridY * 10, width, height);
                
                grid[gridY][gridX] = true;
                carveRoom(newRoom);
                
                if (!rooms.isEmpty()) {
                    Room randomRoom = rooms.get(rng.nextInt(rooms.size()));
                    carveTunnel(newRoom.getX() + (newRoom.getW() / 2), newRoom.getY() + (newRoom.getH() / 2), randomRoom.getX() + (randomRoom.getW() / 2), randomRoom.getY() + (randomRoom.getH() / 2));
                }
                
                rooms.add(newRoom);
            }
        }
    }
    
    public void carveTunnel(int x, int y, int dx, int dy) {
        this.map[y][x] = new Tile(Tile.FLOOR);
        
        // Fill surrounding empty tiles with walls
        if (this.map[y-1][x].getType() == Tile.CHASM) {
            this.map[y-1][x] = new Tile(Tile.WALL);
        } if (this.map[y+1][x].getType() == Tile.CHASM) {
            this.map[y+1][x] = new Tile(Tile.WALL);
        } if (this.map[y][x-1].getType() == Tile.CHASM) {
            this.map[y][x-1] = new Tile(Tile.WALL);
        } if (this.map[y][x+1].getType() == Tile.CHASM) {
            this.map[y][x+1] = new Tile(Tile.WALL);
        } if (this.map[y-1][x-1].getType() == Tile.CHASM) {
            this.map[y-1][x-1] = new Tile(Tile.WALL);
        } if (this.map[y+1][x-1].getType() == Tile.CHASM) {
            this.map[y+1][x-1] = new Tile(Tile.WALL);
        } if (this.map[y-1][x+1].getType() == Tile.CHASM) {
            this.map[y-1][x+1] = new Tile(Tile.WALL);
        } if (this.map[y+1][x+1].getType() == Tile.CHASM) {
            this.map[y+1][x+1] = new Tile(Tile.WALL);
        }
        
        // Recursively call self on the direction headed
        if (x < dx) {
            carveTunnel(x+1, y, dx, dy);
        } else if (x > dx) {
            carveTunnel(x-1, y, dx, dy);
        } else if (y < dy) {
            carveTunnel(x, y+1, dx, dy);
        } else if (y > dy) {
            carveTunnel(x, y-1, dx,dy);
        }
    }
    
    public void carveRoom(Room room) {
        for (int y = room.getY(); y < room.getY() + room.getH(); y++) {
            for (int x = room.getX(); x < room.getX() + room.getW(); x++) {
                // Make sure we aren't overwriting existing floors
                if (this.map[y][x].getType() != Tile.FLOOR) {
                    // If this is the top or the bottom row of the room, build a wall
                    if (y == room.getY() || y == room.getY() + room.getH() - 1) {
                        this.map[y][x] = new Tile(Tile.WALL);
                    }
                    // Same for the left and right side of the room
                    else if (x == room.getX() || x == room.getX() + room.getW() - 1) {
                        this.map[y][x] = new Tile(Tile.WALL);
                    } 
                    // Fill the tiles between with floors
                    else {
                        this.map[y][x] = new Tile(Tile.FLOOR);
                    }
                }
            }
        }
    }
    
    public Tile[][] getMap() {
        return this.map;
    }
}
