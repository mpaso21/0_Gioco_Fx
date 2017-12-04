package entity;

import resources.Assets;
import utility.Sprite;

public class Ground extends Sprite{

	public Ground(){
		super();
		super.setImage(Assets.ground);
		super.setPosition(0, 440);
	}
	
}
