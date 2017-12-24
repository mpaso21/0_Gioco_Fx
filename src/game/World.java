package game;

import java.util.List;

import entity.Enemy;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import macroEntity.Background;
import macroEntity.Bullets;
import macroEntity.Enemies;
import macroEntity.Explosions;
import utility.Vector2d;

public class World {
	
	public Player player;
	public Canvas canvas;
	
	private Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe
	private Bullets bullets;
	private Background backgrounds;
	private Explosions explosions;
    
	public World() {
		player = new Player();
		canvas = new Canvas(1600.0, 800.0);
		enemies = new Enemies();
		bullets = new Bullets(player);
		backgrounds = new Background();
		explosions = new Explosions();
	}
	
	
	//////////////////////// ENEMIES ///////////////////////////////////////
	public void updateEnemies(double elapsedTime) {
		enemies.update(elapsedTime);
	}
//	public void resetEnemies(double elapsedTime) {
//		enemies.reset(elapsedTime);
//	}
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
		List<Vector2d> list = bullets.intersects(enemies);
		if(!list.isEmpty()) {
//			System.out.println(list);	
			explosions.create(list);
		}
	}
	/////////////////////BULLETS/////////////////////////////////////////////////
	
	
	/////////////////////////////EXPLOSIONS///////////////////////////////////////
	public void updateExplosions(double elapsedTime, double t){
		explosions.update(elapsedTime, t);
	}
	
	public void renderExplosion(GraphicsContext gc){
		explosions.render(gc);
	}
	//////////////////////////////EXPLOSIONS///////////////////////////////////
	
	
	///////////////////////BACKGROUND//////////////////////////////////////////
	public void updateBackground(double elapsedTime){
		backgrounds.update(elapsedTime);
	}
	public void renderBackground(GraphicsContext gc){
		backgrounds.render(gc);
	}
	/////////////////////////BACKGROUNDS//////////////////////////////////////
}
