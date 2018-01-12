package macroEntity;

import java.util.ArrayList;
import java.util.List;

import entity.Bullet;
import entity.Enemy;
import java.io.Serializable;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import utility.Command;
import utility.Constants;
import utility.Sprite;
import utility.Vector2d;
import utility.commands.DrawImageCommand;

public class Enemies implements Serializable {

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
    
//	public void reset(double playerPosition){
//		double d;
//		double pos;
//		System.out.println("\n");
//		for(final Enemy e: enemies){
//			d = e.getBoundary().getMinX() - playerPosition;
//                        pos = playerPosition-Constants.XPLAYER_ORIGIN+d;
//                        e.setPosition(pos, e.getBoundary().getMinY());
//                        
//                        System.out.println(pos);
//		}
//	
//	}
	
	public void render(GraphicsContext gc){
		for(Enemy e: enemies){
			e.render(gc);
		}
	}
        
        public List<Command> renderCommands() {
            List<Command> com = new ArrayList<>();
            for(Enemy e: enemies){
                com.add(new DrawImageCommand(e.getImageName(),
                        e.getBoundary().getMinX(),
                        e.getBoundary().getMinY()));
            }
            return com;
        }
	
	public boolean intersects(Sprite p){
		for(Enemy e: enemies){
			if(e.intersects(p)) {
				return true; //return se torna vero esce
			}
		}
		return false;
	}
	
	public boolean intersectBullet(Bullet b, List<Vector2d> l) {
		boolean hit = false;
		
		final ArrayList<Enemy> remove = new ArrayList<>();
		
		for(Enemy e : enemies) {
			if(b.intersects(e)) {
				//e.intersectsBullet();
				
				l.add(new Vector2d(e.getBoundary().getMinX()+ e.getBoundary().getWidth()/2,
						           e.getBoundary().getMinY()+ e.getBoundary().getHeight()/2));
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
