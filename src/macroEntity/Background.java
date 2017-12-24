package macroEntity;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Constants;
import utility.Sprite;

public class Background {
	private Sprite[] backgrounds;
	
	public Background(){
		backgrounds = new Sprite[2];
		for(int i =0; i<2; i++){
			backgrounds[i]= createSprite(Constants.BACKGROUND_WIDTH*i);
		}
	}
	
	private Sprite createSprite(double x){
		final Sprite s = new Sprite();
		s.setImage(Assets.background);
		s.setPosition(x, 0);
		return s;
	} 
	
	public void update(double time){
		for(int i =0; i<2; i++){
			backgrounds[i].update(time);
			backgrounds[i].setVelocity(0,0);
			backgrounds[i].addVelocity(-Constants.XPLAYER_SPEED, 0);
			if(backgrounds[i].getBoundary().getMinX() < -Constants.BACKGROUND_WIDTH){
				backgrounds[i].setPosition(Constants.BACKGROUND_WIDTH, 0);
			}
		}
	}
	
	public void render(GraphicsContext gc){
		for(int i =0; i<2; i++){
			backgrounds[i].render(gc);
		}
	}
}
