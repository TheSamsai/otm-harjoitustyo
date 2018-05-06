package gamestate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.game.entities.Entity;
import dungeoncrawler.game.GameState;
import dungeoncrawler.game.MenuItem;
import dungeoncrawler.game.entities.Player;
import dungeoncrawler.game.entities.item.HealthPotion;
import dungeoncrawler.game.entities.item.Roundshield;
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
    public void noEnemiesInSpawnRoom() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        
        boolean found = false;
        
        int playerRoom = 0;
        
        boolean same = false;
        
        for (Entity e : gs.getMonsters()) {
            if (rooms.get(playerRoom).contains(e.getX(), e.getY())) {
                same = true;
            }
        }
        
        assertTrue(!same);
    }
    
    @Test
    public void movingLeftIntoEnemyDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.spawnMonsterAt(gs.randomMonster(), player.getX() - 1, player.getY());
        gs.moveLeft(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingRightIntoEnemyDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.spawnMonsterAt(gs.randomMonster(), player.getX() + 1, player.getY());
        gs.moveRight(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingUpIntoEnemyDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.spawnMonsterAt(gs.randomMonster(), player.getX(), player.getY() - 1);
        gs.moveUp(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingDownIntoEnemyDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.spawnMonsterAt(gs.randomMonster(), player.getX(), player.getY() + 1);
        
        gs.moveDown(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingLeftIntoWallsDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.getLevel().getMap()[player.getY()][player.getX() - 1] = new Tile(Tile.wall);
        
        gs.moveLeft(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingRightIntoWallsDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.getLevel().getMap()[player.getY()][player.getX() + 1] = new Tile(Tile.wall);
        gs.moveRight(player);
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingUpIntoWallsDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.getLevel().getMap()[player.getY() - 1][player.getX()] = new Tile(Tile.wall);
        gs.moveUp(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingDownIntoWallsDoesNotMovePlayer() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Entity player = gs.getPlayer();
        int startX = player.getX();
        int startY = player.getY();
        
        gs.getLevel().getMap()[player.getY() + 1][player.getX()] = new Tile(Tile.wall);
        gs.moveDown(player);
        
        assertTrue((startX == player.getX()) == (startY == player.getY()));
    }
    
    @Test
    public void movingIntoItemPicksUp() {
        ArrayList<Room> rooms = gs.getLevel().getRooms();
        Player player = (Player) gs.getPlayer();
        
        gs.spawnItemAt(gs.randomItem(), player.getX()-1, player.getY());
        fakeInput.add("LEFT");
        
        gs.processInput(fakeInput);
        
        assertTrue(player.getInventory().size() > 0);
    }
    
    @Test
    public void upArrowMovesUp() {
        fakeInput.add("UP");
        
        int y = gs.getPlayer().getY();
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getPlayer().getY() == y - 1);
    }
    
    @Test
    public void downArrowMovesDown() {
        fakeInput.add("DOWN");
        
        int y = gs.getPlayer().getY();
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getPlayer().getY() == y + 1);
    }
    
    @Test
    public void leftArrowMovesLeft() {
        fakeInput.add("LEFT");
        
        int x = gs.getPlayer().getX();
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getPlayer().getX() == x - 1);
    }
    
    @Test
    public void rightArrowMovesRight() {
        fakeInput.add("RIGHT");
        
        int x = gs.getPlayer().getX();
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getPlayer().getX() == x + 1);
    }
    
    @Test
    public void iKeyEntersMenu() {
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getInMenu());
    }
    
    @Test
    public void inventoryUsingItemsWorks() {
        gs.getPlayer().getInventory().add(new HealthPotion(gs.getPlayer(), 0, 0));
        
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        fakeInput.clear();
        fakeInput.add("ENTER");
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getPlayer().getInventory().size() == 0);
    }
    
    @Test
    public void inventoryReturnsRightAmountOfItems() {
        gs.getPlayer().getInventory().add(new Roundshield(gs.getPlayer(), 0, 0));
        gs.getPlayer().getInventory().add(new Roundshield(gs.getPlayer(), 0, 0));
        
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        assertTrue(gs.getMenuItems().size() == 2);
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
    
    @Test
    public void menuDownWorks() {
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        ArrayList<MenuItem> menu = gs.getMenuItems();
        menu.clear();
        
        menu.add(new MenuItem("wrong", "wrong"));
        menu.add(new MenuItem("right", "right"));
        menu.get(0).selected = true;
        
        gs.menuDown();
        
        assertTrue(menu.get(1).selected);
    }
    
    @Test
    public void menuUpWorks() {
        fakeInput.add("I");
        
        gs.processInput(fakeInput);
        
        ArrayList<MenuItem> menu = gs.getMenuItems();
        menu.clear();
        
        menu.add(new MenuItem("right", "right"));
        menu.add(new MenuItem("wrong", "wrong"));
        menu.get(1).selected = true;
        
        gs.menuDown();
        
        assertTrue(menu.get(0).selected);
    }
}
