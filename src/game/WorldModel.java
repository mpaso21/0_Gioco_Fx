package game;

import entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utility.Constants;

import utility.WrapperValue;

public class WorldModel {

        private Map<String, Double> gameOverWait;
	public ArrayList<String> input = new ArrayList<String>();
        //WRAPPER VALORE SINGOLO!!
	public WrapperValue<Boolean> shoot = new WrapperValue<Boolean>(false);

	final World world;

	public WorldModel(final World world) {
		this.world = world;
                this.gameOverWait = new HashMap<>();
	}
        //aggiorno i movimenti, gli input 
        //LO chiamo 60 volte al secondo
	public void update(double elapsedTime, double t) {
            
            if(world.player.state != Player.State.GAME_OVER) {
            	if(Constants.MORTAL){ //se è mortale quindi true
                    //il player che collide i nemici(senza sparare)
            		world.player.intersectsEnemies(world.intersectsEnemies());
            	}
            	
                world.intersectsBullets();//che i miei proiettili colpiscano i nemici
                world.player.update(elapsedTime, t);
                handleInput();		
                world.updateBullets(elapsedTime); //update per bullet che si muovono               
            } else {                
                gameOverWait.putIfAbsent("initTime", t);//t dell updat del game controller
                gameOverWait.put("current", t);
                if (gameOverWait.get("current") - gameOverWait.get("initTime") >= 4) {
                    world.loadMenu();
                }//t punto game over poi passa un po di tempo current lo inizializzo con il valore che è passato poi verifico diff
            }
		world.updateEnemies(elapsedTime);//generiazione enemies e moviemtno
		
		world.updateBackground(elapsedTime);//movimento deol background(CHE sono due sprite che si muovono)
		
		world.updateExplosions(elapsedTime, t);
		
	}

	private void handleInput() {

		if (input.contains("Z")) {
			world.player.jump();
		}

		if (input.contains("X")) {

		    world.createBullet();
			shoot.value = true;
			input.remove("X");
		}//verifico se input contiene x allora sparo setto il valore di sparato a true e lo rimuovo dall'array
                //perchè? per fare in modo che se uno tenga premuto il tasto non ci sia una striscia di proiettili
		
		if (input.contains("I")){
			Constants.MORTAL = !Constants.MORTAL;
			input.remove("I");
		}
	}
}
