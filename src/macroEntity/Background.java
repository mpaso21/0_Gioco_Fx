package macroEntity;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Command;
import utility.Constants;
import utility.Sprite;
import utility.commands.DrawImageCommand;

public class Background {
	private Sprite[] backgrounds;
	
	public Background(){//mio background composto da due sprite di lunghezza 1600
            //la prima posta in posizione 0,0 la seconda 1600, 0. le canvas si sposta a -una certa velocita
            //quando arrivo a -1600, la sprite viene spostata a 1600,0 cosi ho l'illusione di un backgroudn infinito
            //io player non mi muovo, e vedo solo i primi 800,il mio background scorre
		backgrounds = new Sprite[2];
		for(int i =0; i<2; i++){
			backgrounds[i]= createSprite(Constants.BACKGROUND_WIDTH*i);
		}
	}
	
	private Sprite createSprite(double x){
		final Sprite s = new Sprite();
		s.setImageName("background");
                s.setImage(Assets.imagesMap.get(s.getImageName()));
//                s.setImage(Assets.imagesMap.get("background"));
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
        //multiplayer
        public List<Command> renderCommands() {
            List<Command> com = new ArrayList<>();
            for(int i =0; i<2; i++){
                com.add(new DrawImageCommand(backgrounds[i].getImageName(),
                        backgrounds[i].getBoundary().getMinX(),
                        backgrounds[i].getBoundary().getMinY()));
            }
            return com;
        }
       
}
