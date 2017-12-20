package game;

import java.util.List;

import entity.Enemy;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import macroEntity.Bullets;
import macroEntity.Enemies;

public class World {
	
	public Player player;
	public Canvas canvas;
	
	private Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe
	private Bullets bullets;
    
	public World() {
		player = new Player();
		canvas = new Canvas(3200.0, 800.0);
		enemies = new Enemies();
		bullets = new Bullets(player);
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
	public boolean intersectsEnemies(){ //creo questo metoodo perchè player e enemies si "rivolgono" al world, non comunicano direttamente
		return enemies.intersects(player); //non metto paramentri perchè player ce l'ho nella classe
	}
	//////////////////////// ENEMIES ///////////////////////////////////////
	
	
	/////////////////////////BULLETS/////////////////////////////////////////
	public void createBullet(){
		bullets.create();
	}
	public void updateBullets(double elapsedTime){
		bullets.update(elapsedTime);
	}
	public void renderBullets(GraphicsContext gc){
		bullets.render(gc);
	}
	public void intersectsBullets() {
		List<Enemy> list = bullets.intersects(enemies);
		if(!list.isEmpty()) {
//			System.out.println(list);	
		}
	}
	/////////////////////BULLETS/////////////////////////////////////////////////
}
