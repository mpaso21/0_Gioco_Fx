package entity;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.AnimatedImageString;
import utility.Command;
import utility.Constants;
import utility.Sprite;
import utility.commands.DrawImageCommand;

public class Player extends Sprite {
 //player sempre fermo sulla x si modifica solo la y e muovo la sprite del background
    public enum State {
        PLAY,
        GAME_OVER
    }
    //enum per multiplayer player 1 rosso 2 giallo
    public enum Type {
        A, B
    }

    public State state = State.PLAY; //stato iniziale

    private AnimatedImageString playerAnimation;

    public Player() {
        super();

        createAnimationFrames();

        super.setImageName(playerAnimation.getFrame(0));//prendo il nome dell'immagine
        super.setImage(Assets.imagesMap.get(super.getImageName()));//setto immagine vera e propria
        super.setPosition(100, 200);
    }
 //multiplayer
    public Player(Type t) {
        super();

        createAnimationFrames(t);


        super.setImageName(playerAnimation.getFrame(0));
        super.setImage(Assets.imagesMap.get(super.getImageName()));
        
        super.setPosition((Type.A == t) ? 100 : 30, 200);
    }

    private void createAnimationFrames() { //ANIMATERimagestring perchè cosi posso passare le immagini in string
        playerAnimation = new AnimatedImageString(); //INIZIALIZZo oggetto dichiarato sopra
        playerAnimation.frames = Assets.player_frames_A;//il mio oggetto ha un array di stringhe detto frames(klo inizializzo con le stringhe caricate nell'Assets)
        playerAnimation.duration = 0.1;//durata di ogni singolo frame
    }

    //multiplayer
    private void createAnimationFrames(Type t) {
        playerAnimation = new AnimatedImageString();
        playerAnimation.duration = 0.1;
        
        if(Type.A==t) {
            playerAnimation.frames = Assets.player_frames_A;
        } else if(Type.B==t) {
            playerAnimation.frames = Assets.player_frames_B;
        }
    }
    
    private void handlePlayerLimit() {
		if (getBoundary().getMinY() < 0) { //non ssupero il bordo superiore
			setPosition(getBoundary().getMinX(), 0);
		}
		if (getBoundary().getMinY() > Constants.PLAYER_MAXY) {
                    if(Constants.MORTAL) {//se sono mortale (se vado troppo in basso mi schianto e muoio, oltre ai nemici)
                        state = State.GAME_OVER;
                    } else {
			setPosition(getBoundary().getMinX(), Constants.PLAYER_MAXY); //se sono immortale mi blocco nella mia zona limite
                    }
		} //voglio che il mio player sta in un determinato intervallo(in aria)
    }

    public void update(double time, double t) {
        super.update(time);//quello che mi fa i movimenti veri e propri
        super.setVelocity(0, 0);//player a velocità costante
       // super.addVelocity(Constants.XPLAYER_SPEED, 0);
        super.addVelocity(0, Constants.GRAVITY_SPEED);//aggiiungo gravità al player(se clicco z salta e poi va giu per gravità)
        super.setImageName(playerAnimation.getFrame(t)); //mi da il nome dell'immagine relativa al tempo trascorso. questo per avere l'immagine giusta al tempo giusto
        handlePlayerLimit();
//        printFrameDistribution(t);
    }
    
    @Override
    public void render(GraphicsContext gc) {//setto l'immagine nel update setto il nome dell'immagine
        super.setImage(Assets.imagesMap.get(super.getImageName())); //con il get ottengo il valore associato alla stringa nel map
        super.render(gc);
    }

    public Command renderCommand() {
        return new DrawImageCommand(getImageName(),
                        getBoundary().getMinX(),
                        getBoundary().getMinY());
    }
    //singleplayer
    public void jump(){//0 perchè salto sulla y e non sulla x 
    	super.addVelocity(0, -Constants.JUMP_SPEED);
    }
    
    
//	public boolean maxHeightJump() {
//		System.out.println("y: " + yOrigin + " x: " + this.getBoundary().getMaxY());
////		if(yOrigin + this.getBoundary().getMinY() < maxJump) {
////			return true;
////		}
//		return false;
//	}

    //singleplayer
	public void intersectsEnemies(boolean intersectsEnemies) {
		if(intersectsEnemies) {
                    state = State.GAME_OVER;
                    // esplosione
                }
	}
}
