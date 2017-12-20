package macroEntity;

import java.util.ArrayList;
import java.util.List;

import entity.Bullet;
import entity.Enemy;
import entity.Player;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;


public class Bullets {
    private final List<Bullet> bullets;
    private final Player p;
    
    public Bullets(final Player p){
    	bullets = new ArrayList<Bullet>();
    	this.p = p;
    }
    
    public void create(){
    	bullets.add(new Bullet(p.getBoundary().getMaxX(),
    							p.getBoundary().getMinY() + p.getBoundary().getWidth()/3));
    }
    
    public void update(double elapsedTime){
    	final ArrayList<Bullet> remove = new ArrayList<>();
    	
    	for (Bullet b : bullets) {
			b.setVelocity(0, 0);

			b.addVelocity(1000, 0);
			b.update(elapsedTime);

			if (b.getBoundary().getMinX() - p.getBoundary().getMinX() > 300) {
				remove.add(b);
			}
		}
    	
    	bullets.removeAll(remove);
    }
    
    public void render(GraphicsContext gc){
    	for(Bullet b: bullets){
    		b.render(gc);
    	}   	
    }
    
    public List<Enemy> intersects(Enemies enemies) {
    	ArrayList<Enemy> removed = new ArrayList<>();
    	final ArrayList<Bullet> remove = new ArrayList<>();
    	for(Bullet b : bullets) {
    		if(enemies.intersectBullet(b, removed)) {
    			remove.add(b);
    		}
    	}
    	bullets.removeAll(remove);
    	return removed;
    }
}
