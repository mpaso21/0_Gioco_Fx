package resources;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import utility.Constants;

public class Assets {

    public static Map<String, Image> imagesMap;
//    public static Image background;
//    public static Image bullet;

    public static String[] player_frames;
    public static String[] enemies;    
    public static String[] explosion_frames;

    private static String getResource(String filename) {
        return Assets.class.getResource(filename).toExternalForm();
    }

    //carico immagine
    public static void load() {
        imagesMap = new HashMap<>();
        imagesMap.put("background", new Image(getResource("background.png")));
        imagesMap.put("bullet", new Image(getResource("bullet.png")));
    
        imagesMap.put("player_0", new Image(getResource("player_0.png")));
        imagesMap.put("player_1", new Image(getResource("player_1.png")));
        imagesMap.put("player_2", new Image(getResource("player_2.png")));
        
        imagesMap.put("enemy_b", new Image(getResource("enemy_b.png")));
        imagesMap.put("enemy_p", new Image(getResource("enemy_p.png")));
        imagesMap.put("enemy_g", new Image(getResource("enemy_g.png")));
        imagesMap.put("enemy_y", new Image(getResource("enemy_y.png")));
        
        imagesMap.put("explosion1", new Image(getResource("explosion1.png")));
        imagesMap.put("explosion2", new Image(getResource("explosion2.png")));
        imagesMap.put("explosion3", new Image(getResource("explosion3.png")));
        imagesMap.put("explosion4", new Image(getResource("explosion4.png")));
        imagesMap.put("explosion5", new Image(getResource("explosion5.png")));
        
        
//        background = new Image(getResource("background.png"));
//        bullet = new Image(getResource("bullet.png"));

        player_frames = new String[3];
        player_frames[0] = "player_0";
        player_frames[1] = "player_1";
        player_frames[2] = "player_2";
        
        enemies = new String[4];
        enemies[0] = "enemy_b";
        enemies[1] = "enemy_p";
        enemies[2] = "enemy_g";
        enemies[3] = "enemy_y";
        
        explosion_frames = new String[Constants.EXPLOSION_FRAME];
        explosion_frames[0] = "explosion1";
        explosion_frames[1] = "explosion2";
        explosion_frames[2] = "explosion3";
        explosion_frames[3] = "explosion4";
        explosion_frames[4] = "explosion5";
        
        loadFonts();
    }

    // font loading for usage in css files after the loading.
    private static void loadFonts() {
            Font.loadFont(getResource("PermanentMarker-Regular.ttf"), 10);
            Font.loadFont(getResource("Bungee-Regular.ttf"), 10);
    }
}
