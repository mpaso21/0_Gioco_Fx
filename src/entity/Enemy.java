package entity;

import resources.Assets;
import utility.Constants;
import utility.Sprite;

public class Enemy extends Sprite {

	private static int index = 0;
	public int myIndex;
	
	
	public Enemy(double y){
		super();
		super.setImage(Assets.enemies[Constants.rand.nextInt(4)]);
		final int val = Constants.rand.nextInt(2);
		super.setPosition((val==0)? 3200 : 4000, y);
		myIndex = index++;
	}
	
    public void update(double time)
    {
    	super.update(time);
		setVelocity(0, 0);
		addVelocity(-100.0, 0);
    }
}
