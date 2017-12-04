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
		yOrigin = 300;
		maxJump = 300;
		super.setPosition(100, yOrigin);
	}
	
	public boolean maxHeightJump() {
		System.out.println(yOrigin + " " + this.getBoundary().getMaxY());
		if(yOrigin + this.getBoundary().getMinY() < maxJump) {
			return true;
		}
		return false;
	}
	
}
