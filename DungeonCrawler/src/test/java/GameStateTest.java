/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.game.Entity;
import dungeoncrawler.game.GameState;
import dungeoncrawler.game.Room;
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
}
