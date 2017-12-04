package game;

import java.util.ArrayList;

import entity.Player.State;

public class WorldModel {

	public ArrayList<String> input = new ArrayList<String>();

	final World world;
	
	public WorldModel(final World world){
		this.world = world;
	}
	
	public void update(double elapsedTime){
		world.player.update(elapsedTime);
		handleInput();
		handleCanvas();
		gravity();
		handleStatePlayer();
	}
	
	private void handleInput() {
		world.player.setVelocity(0, 0);
		
//		if(world.player.state == State.GROUND) {
			if (input.contains("LEFT")) {
				 world.player.addVelocity(-200,0);
			} else if (input.contains("RIGHT")) {
				 world.player.addVelocity(200,0);
			}
//		}
		
		if(world.player.maxHeightJump() == false ) {
			 handleJump();
		} 
     }
	
	private void handleCanvas() {//per scorrimento canvas con player posizione alto sx
		world.canvas.setTranslateX(-(world.player.getBoundary().getMinX()-100));
	}
	
	private void gravity() {//realistico soggetto alla forza di gravit�
		
		if(world.player.state != State.GROUND) {
			world.player.addVelocity(0, 200 );	
		}
	}
	
	private void handleStatePlayer(){
		if(world.player.intersects(world.ground)==true){
			world.player.state= State.GROUND;
		} else {
			world.player.state = State.AIR;
		}
                
                if(world.player.getBoundary().getMinX() < 0) {
                    world.player.setPosition(0, world.player.getBoundary().getMinY());
                } 
                if(world.player.getBoundary().getMinX() > 1600) {
                    world.player.setPosition(1600, world.player.getBoundary().getMinY());
                }
	}
	
	private int jumpSpeed = 500;
	
	private void handleJump(){
		
		if(input.contains("Z") && input.contains("RIGHT")) {
				  world.player.addVelocity(jumpSpeed,-jumpSpeed);
			  } else if(input.contains("Z") && input.contains("LEFT")) {
				  world.player.addVelocity(-jumpSpeed,-jumpSpeed);
			  } else if (input.contains("Z")) {
				  world.player.addVelocity(0,-jumpSpeed);	
			  }	
		
      }
}
