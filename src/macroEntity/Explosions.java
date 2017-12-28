package macroEntity;

import java.util.ArrayList;
import java.util.List;
import entity.Explosion;
import javafx.scene.canvas.GraphicsContext;
import utility.Vector2d;

public class Explosions {
	private final List<Explosion> explosions;
	
	public Explosions(){
		explosions = new ArrayList<Explosion>();
	}
	
	public void update(double time, double t){
		for(Explosion e: explosions){
			e.update(time, t);
		}
		remove();
	}
	
	public void render(GraphicsContext gc){
		for(Explosion e: explosions){
			e.render(gc);
		}
	}
	
	public void create(List<Vector2d> list) {
		for(Vector2d v : list) {
			explosions.add(new Explosion(v));	
		}
	}
	
	private void remove() {
		final List<Explosion> remove = new ArrayList<>();
		for(Explosion e : explosions) {
			if(e.animationEnd()) {
				remove.add(e);
			}
		}
		explosions.removeAll(remove);
	}
}