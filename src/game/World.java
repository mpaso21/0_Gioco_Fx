package game;

import entity.Bullet;
import entity.Player;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import macroEntity.Enemies;

public class World {
	
	public Player player;
	public Canvas canvas;
	
	private Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe
//	public Enemy enemy;
	
	public final List<Bullet> bulletsList = new ArrayList<>();
	public final List<Bullet> bulletsListenemy = new ArrayList<>();
	

    
	public World() {
		player = new Player();
		canvas = new Canvas(3200.0, 800.0);
		enemies = new Enemies();
		//enemy = new Enemy();
	}
	
	
	//////////////////////// ENEMIES ///////////////////////////////////////
	public void updateEnemies(double elapsedTime) {
		enemies.update(elapsedTime);
	}
	public void resetEnemies(double elapsedTime) {
		enemies.reset(elapsedTime);
	}
	public void renderEnemies(GraphicsContext gc) {
		enemies.render(gc);
	}
	//////////////////////// ENEMIES ///////////////////////////////////////
}
