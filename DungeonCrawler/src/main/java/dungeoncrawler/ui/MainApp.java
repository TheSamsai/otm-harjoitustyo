package dungeoncrawler.ui;

import dungeoncrawler.game.GameState;
import dungeoncrawler.game.MenuItem;
import dungeoncrawler.game.entities.Entity;
import dungeoncrawler.game.entities.item.Item;
import dungeoncrawler.game.entities.monster.Monster;
import dungeoncrawler.game.level.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    private HashMap<String, Image> spriteStore;
    
    public Image getSprite(String sprite) {
        if (spriteStore.containsKey(sprite)) {
            return spriteStore.get(sprite);
        } else {
            spriteStore.put(sprite, new Image(sprite));
            return spriteStore.get(sprite);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dungeon Crawler");
        
        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene( theScene );
        
        Canvas canvas = new Canvas(1024, 768);
        root.getChildren().add(canvas);
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        final GameState gs = new GameState();
        final ArrayList<String> input = new ArrayList<>();
        spriteStore = new HashMap<>();
        
        final Image player_sprite = new Image("player.png");
        
        // Keyboard input
        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) {
                        input.add(code);
                    }
                }
            }
        );
        
        // Main render loop
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, 1024, 768);
                // Render dungeon
                for (int y = 0; y < 30; y++) {
                    for (int x = 0; x < 45; x++) {
                        int type = gs.getLevel().getMap()[y][x].getType();
                
                        if (type == Tile.floor) {
                            gc.setFill(Color.GRAY);
                        } else if (type == Tile.wall) {
                            gc.setFill(Color.WHITE);
                        } else if (type == Tile.water) {
                            gc.setFill(Color.BLUE);
                        } else if (type == Tile.lava) {
                            gc.setFill(Color.RED);
                        } else if (type == Tile.chasm) {
                            gc.setFill(Color.BLACK);
                        } else if (type == Tile.staircase) {
                            gc.setFill(Color.GREEN);
                        }
                
                        gc.fillRect(x*20, y*20, 20, 20);
                    }
                }
                
                for (Item i : gs.getItems()) {
                    gc.drawImage(getSprite(i.getSprite()), i.getX()*20, i.getY()*20);
                }
                
                for (Monster m : gs.getMonsters()) {
                    gc.drawImage(getSprite(m.getSprite()), m.getX()*20, m.getY()*20);
                }
                
                gc.setFill(Color.GREEN);
                
                gc.drawImage(player_sprite, gs.getPlayer().getX()*20, gs.getPlayer().getY()*20);
                
                // Player info
                gc.setFill(Color.WHITE);
                gc.fillText("HP: " + gs.getPlayer().getHP() + "/" + gs.getPlayer().maxHP(), 900, 20);
                gc.fillText("AC: " + gs.getPlayer().getAC(), 900, 35);
                gc.fillText("Wpn: " + gs.getPlayer().getWeapon().getName(), 900, 50);
                gc.fillText("Arm: " + gs.getPlayer().getArmor().getName(), 900, 65);
                gc.fillText("Shl: " + gs.getPlayer().getShield().getName(), 900, 80);
                
                // Action log
                ArrayList<String> actionLog = (ArrayList<String>) gs.getLog().clone();
                Collections.reverse(actionLog);
                
                int yCoord = 760;
                int read = 0;
                for (String action : actionLog) {
                    gc.fillText(action, 100, yCoord);
                    read++;
                    yCoord -= 20;
                    
                    if (read > 4) {
                        break;
                    }
                }
                
                // Menu
                gc.setFill(Color.DARKGRAY);
                
                if (gs.getInMenu()) {
                    ArrayList<MenuItem> menuContents = gs.getMenuItems();
                    
                    gc.fillRect(200, 280, 300, 20 * menuContents.size() + 10);
                    gc.setFill(Color.WHITE);
                    
                    int y = 300;
                    
                    for (MenuItem mi : menuContents) {
                        if (mi.selected) {
                            gc.setFill(Color.GREEN);
                        } else {
                            gc.setFill(Color.WHITE);
                        }
                        gc.fillText(mi.key + " : " + mi.content, 220, y);
                        y += 20;
                    }
                }
                
                if (!input.isEmpty()) {
                    gs.processInput(input);
                }
                
                input.clear();
            }
        }.start();
        
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
