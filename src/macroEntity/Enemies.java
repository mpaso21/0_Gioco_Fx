package macroEntity;

import java.util.ArrayList;
import java.util.List;

import entity.Enemy;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import utility.Constants;

public class Enemies {

	//ogni quanto vengono spannati i nemici
	private double spawnTime;
	//enemies
    public final List<Enemy> enemies;
    
    public Enemies(){
    	spawnTime = 0;
    	enemies = new ArrayList<>();
    }
    
    public void update(double elapsedTime){
		for(final Enemy e: enemies){
			e.update(elapsedTime);
		}
		enemiesGeneration(elapsedTime);
		enemiesLimit();
		printEnemiesPositions();
	}
    
	public void reset(double playerPosition){
		double d;
		for(final Enemy e: enemies){
			d = e.getBoundary().getMinX() - playerPosition;
			e.setPosition(playerPosition-Constants.XPLAYER_ORIGIN+d, e.getBoundary().getMinY());
		}
	
	}
	
	public void render(GraphicsContext gc){
		for(Enemy e: enemies){
			e.render(gc);
		}
	}
	private void enemiesGeneration(double elapsedTime){
		if(spawnTime+ elapsedTime > Constants.ENEMIES_SPAWNTIME){
			enemies.add(new Enemy(Constants.rand.nextDouble()*300));
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

		for(final Enemy e: enemies){
			if(e.getBoundary().getMaxX()< Constants.SHIFT_AMOUNT){
				Platform.runLater(() -> { enemies.remove(e); });
			}
		}


	}
}
