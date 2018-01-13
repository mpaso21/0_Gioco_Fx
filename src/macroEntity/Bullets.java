package macroEntity;

import java.util.ArrayList;
import java.util.List;
import entity.Bullet;
import entity.Player;
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import utility.Command;
import utility.Vector2d;
import utility.commands.DrawImageCommand;


public class Bullets implements Serializable {
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
                        //perchè il bullets non deve andare lontano
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
    //multiplayer
    public List<Command> renderCommands() {
        List<Command> com = new ArrayList<>();
        for(Bullet b: bullets){
            com.add(new DrawImageCommand(b.getImageName(),
                    b.getBoundary().getMinX(),
                    b.getBoundary().getMinY()));
            }
        return com;
    }
    
    public List<Vector2d> intersects(Enemies enemies) {
    	ArrayList<Vector2d> removed = new ArrayList<>();
    	final ArrayList<Bullet> r = new ArrayList<>();
    	for(Bullet b : bullets) {
    		if(enemies.intersectBullet(b, removed)) {
    			r.add(b);
    		}
    	}
    	bullets.removeAll(r);//una volta colpito il nemico rimuovo il proiettile in modo tale che non va avanti
    	return removed;// posizioni nemici colpiti
    }
}
