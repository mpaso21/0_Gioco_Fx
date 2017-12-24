package game;

import java.util.ArrayList;

import utility.WrapperValue;

public class WorldModel {

	public ArrayList<String> input = new ArrayList<String>();
	public WrapperValue<Boolean> shoot = new WrapperValue<Boolean>(false);

	final World world;

	public WorldModel(final World world) {
		this.world = world;
	}

	public void update(double elapsedTime, double t) {
		world.player.intersectsEnemies(world.intersectsEnemies());
		world.intersectsBullets();
		
		world.player.update(elapsedTime, t);
		handleInput();		
		world.updateBullets(elapsedTime);
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
