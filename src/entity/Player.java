package entity;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.AnimatedImage;
import utility.AnimatedImageString;
import utility.Constants;
import utility.Sprite;

public class Player extends Sprite {

    public enum State {
        PLAY,
        GAME_OVER
    }

    public State state = State.PLAY; //stato iniziale

    public int yOrigin;
    public int maxJump;

    private AnimatedImageString playerAnimation;

    public Player() {
        super();

        createAnimationFrames();

        yOrigin = 200;
        maxJump = 100;

        super.setImageName(playerAnimation.getFrame(0));
        super.setPosition(100 + Constants.SHIFT_AMOUNT, yOrigin);
    }

    private void createAnimationFrames() {
        playerAnimation = new AnimatedImageString();
        playerAnimation.frames = Assets.player_frames;
        playerAnimation.duration = 0.1;
    }
    
    private void handlePlayerLimit() {
		if (getBoundary().getMinY() < 0) {
			setPosition(getBoundary().getMinX(), 0);
		}
		if (getBoundary().getMinY() > Constants.PLAYER_MAXY) {
			setPosition(getBoundary().getMinX(), Constants.PLAYER_MAXY);
		}
    }

    public void update(double time, double t) {
        super.update(time);
        super.setVelocity(0, 0);
       // super.addVelocity(Constants.XPLAYER_SPEED, 0);
        super.addVelocity(0, Constants.GRAVITY_SPEED);
        super.setImageName(playerAnimation.getFrame(t));
        handlePlayerLimit();
//        printFrameDistribution(t);
    }
    
    @Override
    public void render(GraphicsContext gc) {
        super.setImage(Assets.imagesMap.get(super.getImageName()));
        super.render(gc);
    }
    
    public void jump(){
    	super.addVelocity(0, -Constants.JUMP_SPEED);
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

	public void intersectsEnemies(boolean intersectsEnemies) {
		if(intersectsEnemies) {
                    state = State.GAME_OVER;
                    // esplosione
                }
	}
}
