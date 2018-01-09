package macroEntity;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Constants;
import utility.Sprite;

public class Background implements Serializable {
	private Sprite[] backgrounds;
	
	public Background(){
		backgrounds = new Sprite[2];
		for(int i =0; i<2; i++){
			backgrounds[i]= createSprite(Constants.BACKGROUND_WIDTH*i);
		}
	}
	
	private Sprite createSprite(double x){
		final Sprite s = new Sprite();
		s.setImageName("background");
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
                        backgrounds[i].setImage(
                                Assets.imagesMap.get(backgrounds[i].getImageName()));
			backgrounds[i].render(gc);
		}
	}
}
