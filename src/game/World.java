package game;

import entity.Bullet;
import entity.Enemy;
import entity.Ground;
import entity.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;

public class World {
	public Player player;
	public Canvas canvas;
	public Enemy enemy;
//	public Ground ground;
	
        public List<Bullet> bulletsList = new ArrayList<>();
        public List<Bullet> bulletsListenemy = new ArrayList<>();

 
        public World(){
		player = new Player();
		canvas = new Canvas(1600.0*3,800.0);
		enemy = new Enemy();
//		ground = new Ground();
        }
        
}
