package resources;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import utility.Constants;

public class Assets {

    public static Image[] player_frames;
    
    public static Image background;

    public static Image bullet;
    public static Image enemy;
    
    public static Image[] enemies;
    
    public static Image[] explosion_frames;

    private static String getResource(String filename) {
        return Assets.class.getResource(filename).toExternalForm();
    }

    //carico immagine
    public static void load() {
        background = new Image(getResource("background.png"));

        bullet = new Image(getResource("bullet.png"));
        enemy = new Image(getResource("enemy.png"));

        player_frames = new Image[3];
        player_frames[0] = new Image(getResource("player_0.png"));
        player_frames[1] = new Image(getResource("player_1.png"));
        player_frames[2] = new Image(getResource("player_2.png"));
        
        enemies = new Image[4];
        enemies[0] = new Image(getResource("enemy_b.png"));
        enemies[1] = new Image(getResource("enemy_p.png"));
        enemies[2] = new Image(getResource("enemy_g.png"));
        enemies[3] = new Image(getResource("enemy_y.png"));
        
        explosion_frames = new Image[Constants.EXPLOSION_FRAME];
        explosion_frames[0] = new Image(getResource("explosion1.png"));
        explosion_frames[1] = new Image(getResource("explosion2.png"));
        explosion_frames[2] = new Image(getResource("explosion3.png"));
        explosion_frames[3] = new Image(getResource("explosion4.png"));
        explosion_frames[4] = new Image(getResource("explosion5.png"));
        
        loadFonts();
    }

    // font loading for usage in css files after the loading.
    private static void loadFonts() {
            Font.loadFont(getResource("PermanentMarker-Regular.ttf"), 10);
    }
}
