/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sami
 */
public class HighscoreDao {
    private String db;
    
    public HighscoreDao(String db) {
        this.db = db;
    }
    
    private Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(db);
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Highscore (name string, score int)");
            create.executeUpdate();
            
            return conn;
        } catch (SQLException ex) {
            System.out.println("Database connection couldn't be established.");
            
            System.out.println(ex.getMessage());
            
            return null;
        }
    }
    
    public void addNewScore(Highscore score) throws SQLException {
        try {
            Connection conn = getConnection();
            
            if (conn != null) {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO Highscore (name, score) VALUES (?, ?)");
                insert.setString(1, score.getName());
                insert.setInt(2, score.getScore());
                
                insert.executeUpdate();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Highscore> getScores() throws SQLException {
        try {
            Connection conn = getConnection();
            
            if (conn != null) {
                PreparedStatement select = conn.prepareStatement("SELECT * FROM Highscore");
                
                ResultSet set = select.executeQuery();
                
                ArrayList<Highscore> results = new ArrayList<>();
                
                while (set.next()) {
                    results.add(new Highscore(set.getString("name"), set.getInt("score")));
                }
                
                return results;
            }
            
            return new ArrayList<Highscore>();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HighscoreDao.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Highscore>();
        }
    }
}
