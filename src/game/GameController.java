package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import utility.WrapperValue;


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

            final long startNanoTime = System.nanoTime();

		new AnimationTimer(){//interfaccia funzionale
			
			WrapperValue<Long> lastNanoTime = new WrapperValue<Long>( System.nanoTime() );
			@Override
			public void handle(long currentNanoTime) {
				 
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                                lastNanoTime.value = currentNanoTime;
	            
                                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
				
                                model.update(elapsedTime, t);
				view.update(elapsedTime);
			}
			
		}.start();//start animation timeer
	}

    
    public void initEvents(Scene scena) {
        //quando clicco tasto tastiera
        scena.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!model.input.contains(code)) {
                
                if(code.equals("X")) {
                      if(!model.shoot.value) {
                            model.input.add(code);
                        }
                } else {
                    model.input.add(code);
                }
            }
        });

        scena.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            if (code.equals("X")) {
                model.shoot.value = false;
                model.input.remove(code);

            } else {
                model.input.remove(code);

            }

        });

    }
}
