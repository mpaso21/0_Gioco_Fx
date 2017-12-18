package entity;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import resources.Assets;
import utility.AnimatedImage;
import utility.Constants;
import utility.Sprite;

public class Player extends Sprite {

    public enum State {
        AIR,
        GROUND
    }

    public State state = State.AIR;

    public int yOrigin;
    public int maxJump;

    private AnimatedImage playerAnimation;

    public Player() {
        super();

        createAnimationFrames();

        yOrigin = 200;
        maxJump = 100;

        super.setImage(playerAnimation.getFrame(0));
        super.setPosition(100 + Constants.SHIFT_AMOUNT, yOrigin);
    }

    private void createAnimationFrames() {
        playerAnimation = new AnimatedImage();
        playerAnimation.frames = Assets.player_frames;
        playerAnimation.duration = 0.1;
    }

    public void update(double time, double t) {
        super.update(time);
        super.setImage(playerAnimation.getFrame(t));
        printFrameDistribution(t);
    }
    
    Map<String,Integer> myMap = new HashMap<>();
    private void printFrameDistribution(double t) {
        System.out.println("\n\n");
        System.out.println(t);
        myMap.forEach((k,v) -> {
            System.out.println("Key: "+k.toString() +" Val: "+v);
        });
        System.out.println("\n\n");
        
        
        if(myMap.containsKey(playerAnimation.getFrame(t).toString())) {
            int val = myMap.get(playerAnimation.getFrame(t).toString());
            myMap.replace(playerAnimation.getFrame(t).toString(), ++val);
        } else {
            myMap.put(playerAnimation.getFrame(t).toString(), 0);
        }
    }
//	public boolean maxHeightJump() {
//		System.out.println("y: " + yOrigin + " x: " + this.getBoundary().getMaxY());
////		if(yOrigin + this.getBoundary().getMinY() < maxJump) {
////			return true;
////		}
//		return false;
//	}
}
