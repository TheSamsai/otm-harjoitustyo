package dungeoncrawler;

import game.GameState;
import game.Player;
import game.Tile;
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
        
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        final GameState gs = new GameState();
        final ArrayList<String> input = new ArrayList<>();
        final Player player = new Player(20, 15);
        
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
            public void handle(long currentNanoTime) {
                
                // Render dungeon
                for (int y = 0; y < 30; y++) {
                    for (int x = 0; x < 40; x++) {
                        int type = gs.getLevel().getMap()[y][x].getType();
                
                        if (type == Tile.FLOOR) {
                            gc.setFill(Color.GRAY);
                        } else if (type == Tile.WALL) {
                            gc.setFill(Color.WHITE);
                        } else if (type == Tile.WATER) {
                            gc.setFill(Color.BLUE);
                        } else if (type == Tile.LAVA) {
                            gc.setFill(Color.RED);
                        } else if (type == Tile.CHASM) {
                            gc.setFill(Color.BLACK);
                        }
                
                        gc.fillRect(x*20, y*20, 20, 20);
                    }
                }
                
                gc.setFill(Color.GREEN);
                gc.fillRect(player.getX()*20, player.getY()*20, 20, 20);
                
                if (input.contains("UP")) {
                    player.setY(player.getY() - 1);
                } else if (input.contains("DOWN")) {
                    player.setY(player.getY() + 1);
                } else if (input.contains("LEFT")) {
                    player.setX(player.getX() - 1);
                } else if (input.contains("RIGHT")) {
                    player.setX(player.getX() + 1);
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
