package entity;

import java.util.HashMap;
import java.util.Map;

import resources.Assets;
import utility.AnimatedImage;
import utility.Constants;
import utility.Sprite;
import utility.Vector2d;

public class Explosion extends Sprite {

    private AnimatedImage a;
    private Map<String, Double> times;

    public Explosion(Vector2d v) {
        a = new AnimatedImage();
        a.frames = Assets.explosion_frames;
        a.duration = Constants.EXPLOSION_TIME/Constants.EXPLOSION_FRAME;
        super.setImage(a.getFrame(0));
        super.setPosition(v.getX() - super.getBoundary().getWidth() / 2,
                v.getY() - super.getBoundary().getHeight() / 2);
        times = new HashMap<>();
    }

    public void update(double time, double t) {
        times.putIfAbsent("initTime", t);
        times.put("current", t);

        super.update(time);
        setVelocity(0, 0);
        addVelocity(-Constants.EXPLOSIONS_SPEED, 0);

        super.setImage(a.getFrame(times.get("current") - times.get("initTime")));

//        System.out.println("\nInit: " + times.get("initTime"));
//        System.out.println("Current: " + times.get("current"));
    }

    public boolean animationEnd() {
        if (times.get("current") - times.get("initTime") >= Constants.EXPLOSION_TIME) {
            return true;
        }
        return false;
    }
}
