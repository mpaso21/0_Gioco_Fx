package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import utility.LongValue;


public class GameController extends Group{

	private WorldModel model;
	private WorldView view;
	private World world;
	
	public GameController() {
		world = new World();//contiene le cose
		model = new WorldModel(world);//muovo le cose
		view = new WorldView(this, world);//visualizzo le cose this game controller/gruppo per buttarci dentro la canvas
		start();
	}
	
	private void start(){//loop gioco
		new AnimationTimer(){//interfaccia funzionale
			
			LongValue lastNanoTime = new LongValue( System.nanoTime() );
			@Override
			public void handle(long currentNanoTime) {
				 
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
	            lastNanoTime.value = currentNanoTime;
	            
				model.update(elapsedTime);
				view.update(elapsedTime);
			}
			
		}.start();//start animation timeer
	}
	
	public void initEvents(Scene scena) {
		//quando clicco tasto tastiera
		scena.setOnKeyPressed(e -> {
			String code = e.getCode().toString();
            if ( !model.input.contains(code) )
                model.input.add( code );
		});
		
		scena.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            model.input.remove( code );
		
		});
		
	}
}
