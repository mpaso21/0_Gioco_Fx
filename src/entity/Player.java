package entity;

import resources.Assets;
import utility.Sprite;

public class Player extends Sprite{

	public enum State {
		AIR, 
		GROUND
	}
	
	public State state = State.AIR;
	
	public int yOrigin;
	public int maxJump;
	
	public Player(){
		super();
		super.setImage(Assets.ufo);
		yOrigin = 200;
		maxJump = 100;
		super.setPosition(1700, yOrigin);
	}
	
//	public boolean maxHeightJump() {
//		System.out.println("y: " + yOrigin + " x: " + this.getBoundary().getMaxY());
////		if(yOrigin + this.getBoundary().getMinY() < maxJump) {
////			return true;
////		}
//		return false;
//	}
	
}
