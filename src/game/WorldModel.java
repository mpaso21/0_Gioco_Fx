package game;

import entity.Bullet;
import java.util.ArrayList;

import entity.Player.State;
import java.util.List;
import javafx.application.Platform;
import utility.Constants;
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
		
		handleCanvas();
		handlePlayerLimit();
		
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

	private void handleCanvas() {// per scorrimento canvas con player posizione
									// alto sx
		world.canvas.setTranslateX(-(world.player.getBoundary().getMinX() - 10));
	}

	private void handlePlayerLimit() {
		// if(world.player.getBoundary().getMinX() < 1600) {
		// world.player.setPosition(0, world.player.getBoundary().getMinY());
		// }

		// if(world.player.getBoundary().getMinX() > 1600) {
		// world.player.setPosition(1600, world.player.getBoundary().getMinY());
		// }

		if (world.player.getBoundary().getMinY() < 0) {
			world.player.setPosition(world.player.getBoundary().getMinX(), 0);
		}
		if (world.player.getBoundary().getMinY() > 300) {
			world.player.setPosition(world.player.getBoundary().getMinX(), 300);
		}

		if (world.player.getBoundary().getMinX() > 2400) {
			double playerX = world.player.getBoundary().getMinX();

			world.resetEnemies(playerX);
                        
			world.player.setPosition(world.player.getBoundary().getMinX() - Constants.XPLAYER_ORIGIN, world.player.getBoundary().getMinY());
			
//			final double d = world.enemy.getBoundary().getMinX() - world.player.getBoundary().getMinX();
//			world.player.setPosition(world.player.getBoundary().getMinX() - Constants.XPLAYER_ORIGIN, world.player.getBoundary().getMinY());
//			world.enemy.setPosition(world.player.getBoundary().getMinX() + d, world.enemy.getBoundary().getMinY());
			
		}

	}
}
