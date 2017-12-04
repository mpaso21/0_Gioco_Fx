package game;

import entity.Ground;
import entity.Player;
import javafx.scene.canvas.Canvas;

public class World {
	public Player player;
	public Canvas canvas;
	public Ground ground;
	
	public World(){
		player = new Player();
		canvas = new Canvas(1600,800);
		ground = new Ground();
	}
}
