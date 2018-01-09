package entity;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Constants;
import utility.Sprite;

public class Enemy extends Sprite {

    private static int index = 0;
    public int myIndex;

    public Enemy(double y) {
        super();
        super.setImageName(Assets.enemies[Constants.RAND.nextInt(4)]);
        final int val = Constants.RAND.nextInt(2);
        super.setPosition((val == 0) ? 1800 : 2500, y);
        myIndex = index++;
    }

    @Override
    public void update(double time) {
        super.update(time);
        setVelocity(0, 0);
        addVelocity(-Constants.ENEMIES_SPEED, 0);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.setImage(Assets.imagesMap.get(super.getImageName()));
        super.render(gc);
    }
    
    public void intersectsBullet() {
//		System.out.println(myIndex + " colpito dal proiettile");
    }
}
