/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package item;

import dungeoncrawler.game.GameState;
import dungeoncrawler.game.entities.item.*;
import static gamestate.GameStateTest.gs;
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
public class ItemTest {
    
    public static GameState gs;
    ArrayList<String> fakeInput;
    
    public ItemTest() {
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
    @Test
    public void potionsHeal() {
        gs.getPlayer().damage(5);
        
        int hpNow = gs.getPlayer().getHP();
        
        HealthPotion potion = new HealthPotion(gs.getPlayer(), 0, 0);
        potion.use();
        
        assertTrue(hpNow < gs.getPlayer().getHP());
    }
    
    @Test
    public void shieldIncreasesAC() {
        gs.getPlayer().damage(5);
        
        int acNow = gs.getPlayer().getAC();
        
        Roundshield rs = new Roundshield(gs.getPlayer(), 0, 0);
        rs.use();
        
        assertTrue(acNow < gs.getPlayer().getAC());
    }
}
