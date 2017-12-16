package entity;

import resources.Assets;
import utility.Sprite;

public class Enemy extends Sprite{

	public Enemy(){
		super();
		super.setImage(Assets.nemico);
		super.setPosition(2700, 200);
	}
}
