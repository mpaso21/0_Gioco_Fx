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

//	public BooleanValue shootenemy = new BooleanValue(false);

	final World world;

	public WorldModel(final World world) {
		this.world = world;
	}

	public void update(double elapsedTime, double t) {
		world.player.update(elapsedTime, t);
		handleInput();
		handleCanvas();
		gravity();
		handleStatePlayer();
		handlePlayerLimit();
		handleBullets(elapsedTime);

		world.updateEnemies(elapsedTime);
		// handleBulletsEnemy(elapsedTime);
		//handleEnemyLimit();
	}



//	private void handleBulletsEnemy(double elapsedTime) {
//		for (Bullet b : world.bulletsListenemy) {
//			b.setVelocity(0, 0);
//
//			b.addVelocity(-500, 0);
//			b.update(elapsedTime);
//
//			if (world.enemy.getBoundary().getMinX() - b.getBoundary().getMinX() > 600) {
//				Platform.runLater(() -> {
//					world.bulletsListenemy.remove(b);
//				});
//			}
//		}
//	}

	private void handleBullets(double elapsedTime) {
		for (Bullet b : world.bulletsList) {
			b.setVelocity(0, 0);

			b.addVelocity(1000, 0);
			b.update(elapsedTime);

			if (b.getBoundary().getMinX() - world.player.getBoundary().getMinX() > 300) {
				Platform.runLater(() -> {
					world.bulletsList.remove(b);
				});
			}
		}
	}

	private void handleInput() {
		world.player.setVelocity(0, 0);

		// if(world.player.state == State.GROUND) {
		// if (input.contains("LEFT")) {
		// world.player.addVelocity(-200,0);
		// } else if (input.contains("RIGHT")) {
		// world.player.addVelocity(200,0);
		// }
		// }

		world.player.addVelocity(200.0, 0);

		// if(world.player.maxHeightJump() == false ) {
		handleJump();
		// }
	}

	private void handleCanvas() {// per scorrimento canvas con player posizione
									// alto sx
		world.canvas.setTranslateX(-(world.player.getBoundary().getMinX() - 100));
	}

	private void gravity() {// realistico soggetto alla forza di gravit�

		if (world.player.state != State.GROUND) {
			world.player.addVelocity(0, 200);
		}
	}

	private void handleStatePlayer() {
		// if(world.player.intersects(world.ground)==true){
		// world.player.state= State.GROUND;
		// } else {
		// world.player.state = State.AIR;
		// }
		world.player.state = State.AIR;
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
                        
			world.player.setPosition(world.player.getBoundary().getMinX() - Constants.XPLAYER_ORIGIN, world.player.getBoundary().getMinY());
			world.resetEnemies(playerX);
			
//			final double d = world.enemy.getBoundary().getMinX() - world.player.getBoundary().getMinX();
//			world.player.setPosition(world.player.getBoundary().getMinX() - Constants.XPLAYER_ORIGIN, world.player.getBoundary().getMinY());
//			world.enemy.setPosition(world.player.getBoundary().getMinX() + d, world.enemy.getBoundary().getMinY());
			
		}

	}

	private int jumpSpeed = 500;
	// private int jumpDiagonalSpeed = 300;

	private void handleJump() {

		// if(input.contains("Z") && input.contains("RIGHT")) {
		// world.player.addVelocity(jumpDiagonalSpeed,-jumpDiagonalSpeed);
		// } else if(input.contains("Z") && input.contains("LEFT")) {
		// world.player.addVelocity(-jumpSpeed,-jumpSpeed);
		// } else if (input.contains("Z")) {
		if (input.contains("Z")) {
			world.player.addVelocity(0, -jumpSpeed);
		}

		if (input.contains("X")) {

			// System.out.println("spara_GRINGO");

			world.bulletsList
                            .add(new Bullet(world.player.getBoundary().getMaxX(), world.player.getBoundary().getMinY() + world.player.getBoundary().getWidth()/3));

			shoot.value = true;
			input.remove("X");
		}

		// FUNZIONE FUEL SPARO MILLE COLPI

		// if(input.contains("V")) {
		//
		//
		// world.bulletsList.add(new
		// Bullet(world.player.getBoundary().getMaxX(),
		// world.player.getBoundary().getMinY()+10));
		//
		// }

	}

//	private void handleShootEnemy() {
//		int i = 3;
//		while (i <= 4) {
//			world.bulletsListenemy
//					.add(new Bullet(world.enemy.getBoundary().getMinX(), world.enemy.getBoundary().getMinY() + 10));
//
//			shootenemy.value = true;
//			i++;
//		}
//		shootenemy.value = false;
//
//	}

//	private void handleEnemyLimit() {
//
//		if (world.enemy.getBoundary().getMaxX() < 800) {
//			world.enemy.setPosition(3200, world.enemy.getBoundary().getMinY());
//		}
//	}

}
