package game;

import entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utility.Constants;

import utility.WrapperValue;

public class WorldModel {

        private Map<String, Double> gameOverWait;
	public ArrayList<String> input = new ArrayList<String>();
	public WrapperValue<Boolean> shoot = new WrapperValue<Boolean>(false);

	final World world;

	public WorldModel(final World world) {
		this.world = world;
                this.gameOverWait = new HashMap<>();
	}

	public void update(double elapsedTime, double t) {
            
            if(world.player.state != Player.State.GAME_OVER) {
                world.player.intersectsEnemies(world.intersectsEnemies());
                    world.intersectsBullets();

                    world.player.update(elapsedTime, t);
                    handleInput();		
                    world.updateBullets(elapsedTime);                
            } else {                
                gameOverWait.putIfAbsent("initTime", t);
                gameOverWait.put("current", t);
                if (gameOverWait.get("current") - gameOverWait.get("initTime") >= 4) {
                    world.loadMenu();
                }
            }
		world.updateEnemies(elapsedTime);
		
		world.updateBackground(elapsedTime);
		
		world.updateExplosions(elapsedTime, t);
		
	}

	private void handleInput() {

		if (input.contains("Z")) {
			world.player.jump();
		}

		if (input.contains("X")) {

		    world.createBullet();
			shoot.value = true;
			input.remove("X");
		}
	}

//	private void handleCanvas() {// per scorrimento canvas con player posizione
//									// alto sx
//		world.canvas.setTranslateX(-(world.player.getBoundary().getMinX() - 10));
//	}




	
}
