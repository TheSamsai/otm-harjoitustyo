package dungeoncrawler.ui;

import dungeoncrawler.game.GameState;
import dungeoncrawler.game.Entity;
import dungeoncrawler.game.Monster;
import dungeoncrawler.game.Tile;
import java.util.ArrayList;
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
        
        final Image player_sprite = new Image("file:data/player.png");
        
        // Keyboard input
        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
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
                        }
                
                        gc.fillRect(x*20, y*20, 20, 20);
                    }
                }
                
                for (Monster m : gs.getMonsters()) {
                    gc.drawImage(new Image(m.getSprite()), m.getX()*20, m.getY()*20);
                }
                
                gc.setFill(Color.GREEN);
                
                gc.drawImage(player_sprite, gs.getPlayer().getX()*20, gs.getPlayer().getY()*20);
                
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
