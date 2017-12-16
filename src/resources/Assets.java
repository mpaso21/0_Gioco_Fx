package resources;

import javafx.scene.image.Image;

public class Assets {

	public static Image ufo;
	public static Image background;
        public static Image backgroundCopy;
        public static Image backgroundCopy2;
	public static Image ground;
//	public static Image background;
	public static Image bullet;
	public static Image nemico;
    public static Image sfondo; 
    public static Image sfondo1;
    public static Image sfondo2;
	private static String getResource(String filename) {
		return Assets.class.getResource( filename).toExternalForm();
	}
	
	//carico immagine
	public static void load(){
//		background = new Image(getResource("bak"));
		ufo = new Image(getResource("ufo_0.png"));
		background = new Image(getResource("background.png"));
                backgroundCopy = new Image(getResource("background.png"));
                backgroundCopy2 = new Image(getResource("background.png"));
		ground = new Image(getResource("ground.png"));
                
        bullet = new Image(getResource("bullet.png"));
        nemico = new Image(getResource("enemy.png"));
        sfondo = new Image(getResource("sfondod.png"));
        sfondo1 = new Image(getResource("sfondod.png"));
        sfondo2 = new Image(getResource("sfondod.png"));
        }
	
	
}
