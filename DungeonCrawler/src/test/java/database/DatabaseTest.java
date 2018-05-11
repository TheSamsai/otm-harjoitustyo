/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import db.Highscore;
import db.HighscoreDao;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DatabaseTest {
    
    private HighscoreDao dao;
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new HighscoreDao("jdbc:sqlite:test.db");
    }
    
    @After
    public void tearDown() {
        File db = new File("test.db");
        
        if (db.exists()) {
            db.delete();
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void dbStoringNameWorks() {
        try {
            dao.addNewScore(new Highscore("test", 1234));
            
            ArrayList<Highscore> scores = dao.getScores();
            
            assertTrue(scores.get(0).getName().equals("test"));
        } catch (SQLException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void dbEmptyNamesNotStored() {
        try {
            dao.addNewScore(new Highscore("", 1234));
            
            ArrayList<Highscore> scores = dao.getScores();
            
            assertTrue(scores.isEmpty());
        } catch (SQLException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void dbStoringScoreWorks() {
        try {
            dao.addNewScore(new Highscore("test", 1234));
            
            ArrayList<Highscore> scores = dao.getScores();
            
            assertTrue(scores.get(0).getScore() == 1234);
        } catch (SQLException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void dbOrderIsCorrect() {
        try {
            dao.addNewScore(new Highscore("test", 2));
            dao.addNewScore(new Highscore("test", 3));
            dao.addNewScore(new Highscore("test", 1));
            
            ArrayList<Highscore> scores = dao.getScores();
            
            assertTrue(scores.get(0).getScore() == 3);
            assertTrue(scores.get(1).getScore() == 2);
            assertTrue(scores.get(2).getScore() == 1);;
        } catch (SQLException ex) {
            assertTrue(false);
        }
    }
}
