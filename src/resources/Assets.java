package resources;

import javafx.scene.image.Image;

public class Assets {

	public static Image ufo;
	public static Image background;
	public static Image ground;
//	public static Image background;
	
	private static String getResource(String filename) {
		return Assets.class.getResource( filename).toExternalForm();
	}
	
	//carico immagine
	public static void load(){
//		background = new Image(getResource("bak"));
		ufo = new Image(getResource("ufo_0.png"));
		background = new Image(getResource("background.png"));
		ground = new Image(getResource("ground.png"));
	}
	
	
}
