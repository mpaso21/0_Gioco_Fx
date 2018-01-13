package entity;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;

import resources.Assets;
import utility.AnimatedImageString;
import utility.Constants;
import utility.Sprite;
import utility.Vector2d;

public class Explosion extends Sprite {

    private AnimatedImageString a;
    private Map<String, Double> times;

    
    //v lista di posizioni enemi uccisi
    public Explosion(Vector2d v) {//posizione player
        a = new AnimatedImageString();
        a.frames = Assets.explosion_frames;
        a.duration = Constants.EXPLOSION_TIME/Constants.EXPLOSION_FRAME;
        super.setImageName(a.getFrame(0));
        super.setImage(Assets.imagesMap.get(super.getImageName()));
        super.setPosition(v.getX() - super.getBoundary().getWidth() / 2,
                v.getY() - super.getBoundary().getHeight() / 2);//mi fisso nella posizione esatta un po avanti del player
        times = new HashMap<>();
    }

    public void update(double time, double t) {
        times.putIfAbsent("initTime", t);
        times.put("current", t);

        super.update(time);// per farlo muovoere
        setVelocity(0, 0);
        addVelocity(-Constants.EXPLOSIONS_SPEED, 0);//mi sembra che io mi muovo mi avvicino all'esplosione

        super.setImageName(a.getFrame(times.get("current") - times.get("initTime")));//sono sicuro che mi va dalla 0 che è la prima al 5

//        System.out.println("\nInit: " + times.get("initTime"));
//        System.out.println("Current: " + times.get("current"));
    }

    public boolean animationEnd() {//chiiamato nell updat della macro entity explosions per la rimozione una volta terminata l'animazione
        if (times.get("current") - times.get("initTime") >= Constants.EXPLOSION_TIME) {
            return true;
        }
        return false;
    }
    
    @Override
    public void render(GraphicsContext gc) {
        super.setImage(Assets.imagesMap.get(super.getImageName()));//set image c'è qui e nel player perchè hanno animazione nel enemy o bullet non c'è animazione quindi render come sotto
        super.render(gc);
    }
}
