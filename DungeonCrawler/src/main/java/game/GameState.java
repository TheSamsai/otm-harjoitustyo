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
public class GameState {
    private Level level;
    
    public GameState() {
        this.level = new Level();
    }
    
    public Level getLevel() {
        return this.level;
    }
}
