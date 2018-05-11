/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.game;

/**
 * Container class for constructing and relaying menu information
 * @author sami
 */
public class MenuItem {
    public String key;
    public String content;
    public boolean selected;
    
    public MenuItem(String key, String content) {
        this.key = key;
        this.content = content;
        selected = false;
    }
}
