package macroEntity;

import java.util.ArrayList;
import java.util.List;

import entity.Bullet;
import entity.Enemy;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import utility.Constants;
import utility.Sprite;

public class Enemies {

	//ogni quanto vengono spannati i nemici
	private double spawnTime;
	//enemies
    private final List<Enemy> enemies;
    
    public Enemies(){
    	spawnTime = 0;
    	enemies = new ArrayList<>();
    }
    
    public void update(double elapsedTime){
		enemiesLimit();
		enemiesGeneration(elapsedTime);
		for(final Enemy e: enemies){
			e.update(elapsedTime);
		}
//		printEnemiesPositions();
	}
    
	public void reset(double playerPosition){
		double d;
		double pos;
		System.out.println("\n");
		for(final Enemy e: enemies){
			d = e.getBoundary().getMinX() - playerPosition;
                        pos = playerPosition-Constants.XPLAYER_ORIGIN+d;
                        e.setPosition(pos, e.getBoundary().getMinY());
                        
                        System.out.println(pos);
		}
	
	}
	
	public void render(GraphicsContext gc){
		for(Enemy e: enemies){
			e.render(gc);
		}
	}
	
	public boolean intersects(Sprite p){
		for(Enemy e: enemies){
			if(e.intersects(p)) {
				return true; //return se torna vero esce
			}
		}
		return false;
	}
	
	public boolean intersectBullet(Bullet b, List<Enemy> l) {
		boolean hit = false;
		
		final ArrayList<Enemy> remove = new ArrayList<>();
		
		for(Enemy e : enemies) {
			if(b.intersects(e)) {
				e.intersectsBullet();
				l.add(e);
				remove.add(e);
				hit = true;
			}
		}
		
		enemies.removeAll(remove);
		
		return hit;
	}
	
	private void enemiesGeneration(double elapsedTime){
		if(spawnTime+ elapsedTime > Constants.ENEMIES_SPAWNTIME){
			enemies.add(new Enemy(Constants.RAND.nextDouble()*300));
//			System.out.println("SPAWN "+ spawnTime);
			spawnTime = 0;
		}
		else{
			spawnTime+= elapsedTime;
		}
	}
	
	private void printEnemiesPositions() {

		for(final Enemy e: enemies){
			System.out.println("----ENEMY "+e.myIndex+"----");
			System.out.println("X: " + e.getBoundary().getMinX() + "Y: " + e.getBoundary().getMinY());
		}
		System.out.println("DIMENSIONE: "+ enemies.size());


	}
	private void enemiesLimit() {

		final ArrayList<Enemy> remove = new ArrayList<>();
		
		for(final Enemy e: enemies){
			if(e.getBoundary().getMaxX()< Constants.SHIFT_AMOUNT){
				remove.add(e);
			}
		}
		enemies.removeAll(remove);
	}
}
