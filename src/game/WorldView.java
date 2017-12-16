package game;

import entity.Bullet;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import resources.Assets;

public class WorldView {


	final World world;
	private GraphicsContext gc;
	
	public WorldView(final Group group, final World world){
		this.world = world;
		init(group);
	}
	//aggiorna
	//pulisco e ridisegno per 60 volte usando le caratteristiche degli oggetti di world
	public void update(double elapsedTime){
		render();
	}
	
	private void render() {
		gc.clearRect(0, 0, 1600.0*3, 800);
//		gc.fillRect(0, 0, 1600, 800);
		

        gc.drawImage(Assets.sfondo, 0, 0);
        gc.drawImage(Assets.sfondo1, 1600, 0);                    
        gc.drawImage(Assets.sfondo2, 3200, 0);                    
        
            //    gc.drawImage(Assets.background, 0, 0);
            //    gc.drawImage(Assets.backgroundCopy, 1600, 0);                    
            //    gc.drawImage(Assets.backgroundCopy2, 3200, 0);                    
                
//                world.ground.render(gc);
		world.player.render(gc);
        
                for(Bullet b : world.bulletsList) {
                    b.render(gc);
                }
                
                world.enemy.render(gc);
                for(Bullet b : world.bulletsListenemy) {
                    b.render(gc);
                }
	}
	
	private void init(Group group){

		group.getChildren().add(world.canvas);
	
		gc = world.canvas.getGraphicsContext2D();
		
//		gc.setFill(Color.RED);
//		gc.fillRect(0, 0, 1600, 800);
//		gc.setFill(Color.BLUE);
//		gc.fillRect(1300, 200, 100, 100);
		gc.setFill(Color.BLACK);
//		
//		canvas.setTranslateX(400);
//		
//		Random r = new Random();
//		for(int i=0; i<1000; i++) {
//			gc.setFill(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
//			gc.fillRect(r.nextInt(200), r.nextInt(200), r.nextInt(1800), r.nextInt(1600));
//		}
//		
	}
}
