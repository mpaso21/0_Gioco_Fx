package entity;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Constants;
import utility.Sprite;

public class Enemy extends Sprite {

    public Enemy(double y) {// y tra 0 e 299 mentre x 1800 0 2500
        super();
        super.setImageName(Assets.enemies[Constants.RAND.nextInt(4)]);//mi da un intero tra 0 e 3 
        super.setImage(Assets.imagesMap.get(super.getImageName()));
        final int val = Constants.RAND.nextInt(2);
        super.setPosition((val == 0) ? 1800 : 2500, y);
        
    }

    @Override
    public void update(double time) {
        super.update(time);
        setVelocity(0, 0);
        addVelocity(-Constants.ENEMIES_SPEED, 0);//si muovo solo in orizzontale
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
