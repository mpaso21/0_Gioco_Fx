package resources;

import javafx.scene.image.Image;

public class Assets {

    public static Image[] player_frames;
    
    public static Image background;

    public static Image bullet;
    public static Image enemy;
    
    public static Image[] enemies;

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
        
    }

}
