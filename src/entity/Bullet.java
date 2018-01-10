package entity;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Sprite;

public class Bullet extends Sprite {

	public Bullet(double x, double y){
		super();
                super.setImageName("bullet");
                super.setImage(Assets.imagesMap.get(super.getImageName()));
                super.setPosition(x, y);
        }

        @Override
        public void render(GraphicsContext gc) {
            super.render(gc);
        }
}