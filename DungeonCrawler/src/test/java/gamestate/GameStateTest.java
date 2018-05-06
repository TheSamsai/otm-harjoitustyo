package gamestate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.game.entities.Entity;
import dungeoncrawler.game.GameState;
import dungeoncrawler.game.entities.Player;
import dungeoncrawler.game.level.Room;
import dungeoncrawler.game.level.Tile;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sami
 */
public class GameStateTest {
    
    public static GameState gs;
    ArrayList<String> fakeInput;
    
    public GameStateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        gs = new GameState();
        fakeInput = new ArrayList<>();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void playerInARoomUponStartup() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        
        boolean found = false;
        
        for (Room r : rooms) {
            if (r.contains(player.getX(), player.getY())) {
                found = true;
                break;
            }
        }
        
        assertTrue(found);
    }
    
    @Test
    public void movingIntoEnemyDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.spawnMonsterAt(gs.randomMonster(), player.getX() - 1, player.getY());
        gs.spawnMonsterAt(gs.randomMonster(), player.getX() + 1, player.getY());
        gs.spawnMonsterAt(gs.randomMonster(), player.getX(), player.getY() - 1);
        gs.spawnMonsterAt(gs.randomMonster(), player.getX(), player.getY() + 1);
        
        gs.moveLeft(player);
        gs.moveLeft(player);
        gs.moveRight(player);
        gs.moveUp(player);
        gs.moveUp(player);
        gs.moveDown(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingIntoWallsDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.getLevel().getMap()[player.getY()][player.getX() - 1] = new Tile(Tile.wall);
        gs.getLevel().getMap()[player.getY()][player.getX() + 1] = new Tile(Tile.wall);
        gs.getLevel().getMap()[player.getY() - 1][player.getX()] = new Tile(Tile.wall);
        gs.getLevel().getMap()[player.getY() + 1][player.getX()] = new Tile(Tile.wall);
        
        gs.moveLeft(player);
        gs.moveLeft(player);
        gs.moveRight(player);
        gs.moveUp(player);
        gs.moveUp(player);
        gs.moveDown(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingIntoItemPicksUp() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Player player = (Player) gs.getPlayer();
        
        gs.spawnItemAt(gs.randomItem(), player.getX()-1, player.getY());
        gs.moveLeft(player);
        
        ArrayList<String> input = new ArrayList<>();
        input.add("ENTER");
        
        gs.processInput(input);
        
        assertTrue(player.getInventory().size() > 0);
    }
    
    @Test
    public void iKeyEntersMenu() {
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getInMenu());
    }
    
    @Test
    public void escapeGetsOutOfMenu() {
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        fakeInput.clear();
        fakeInput.add("ESCAPE");
        gs.processInput(fakeInput);
        
        assertFalse(gs.getInMenu());
    }
}
