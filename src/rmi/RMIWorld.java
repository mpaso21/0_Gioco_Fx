package rmi;

import entity.Player;
import java.util.ArrayList;
import java.util.List;
import macroEntity.Bullets;
import macroEntity.Enemies;
import macroEntity.Explosions;
import utility.Command;
import utility.Constants;
import utility.Vector2d;
import utility.WrapperValue;
import utility.commands.WaitCommand;

public class RMIWorld {

    public ArrayList<String> inputP1 = new ArrayList<String>();
    public WrapperValue<Boolean> shootP1 = new WrapperValue<Boolean>(false);
    
    public ArrayList<String> inputP2 = new ArrayList<String>();
    public WrapperValue<Boolean> shootP2 = new WrapperValue<Boolean>(false);

    public Player player1;
    public Player player2;

    private final Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe

    private final Bullets bulletsP1;
    private final Bullets bulletsP2;
    private final Explosions explosions;


    public RMIWorld() {
        player1 = new Player(Player.Type.A);
        player2 = new Player(Player.Type.B);
        enemies = new Enemies();
        bulletsP1 = new Bullets(player1);
        bulletsP2 = new Bullets(player2);
        explosions = new Explosions();;
    }

    public void updateEnemies(double elapsedTime) {
        enemies.update(elapsedTime);
    }

    public boolean intersectsEnemies() {
        return enemies.intersects(player1) || enemies.intersects(player2);
    }

    public void createBulletP1() {
        bulletsP1.create();
    }
    
    public void createBulletP2() {
        bulletsP2.create();
    }
    
    public void updateBullets(double elapsedTime) {
        bulletsP1.update(elapsedTime);
        bulletsP2.update(elapsedTime);
    }

    public void intersectsBulletsP1() {
        List<Vector2d> list = bulletsP1.intersects(enemies);
        if (!list.isEmpty()) {	
            explosions.create(list);
        }
    }
    
    public void intersectsBulletsP2() {
       List<Vector2d> list = bulletsP2.intersects(enemies);
        if (!list.isEmpty()) {	
            explosions.create(list);
        }
    }
    
        
    public void updateExplosions(double elapsedTime, double t) {
        explosions.update(elapsedTime, t);
    }

    public boolean gameIsOver() {//ritorno true se uno dei due giocatori Ã¨ nello stato di gameover
        return (player1.state == Player.State.GAME_OVER
                || player2.state == Player.State.GAME_OVER);
    }

    public void update(double elapsedTime, double t) {
        
        if (player1.state != Player.State.GAME_OVER
                && player2.state != Player.State.GAME_OVER) {

            if (Constants.MORTAL_MULTI) {
                player1.intersectsEnemies(intersectsEnemies());
                player2.intersectsEnemies(intersectsEnemies());
            }

            intersectsBulletsP1();
            intersectsBulletsP2();

            player1.update(elapsedTime, t);
            player2.update(elapsedTime, t);
            handleInputP1();
            handleInputP2();
            updateBullets(elapsedTime);

        }//se uno dei due è nello stato di game over e  lo faccio sempre\
        updateEnemies(elapsedTime);
        updateExplosions(elapsedTime, t);

    }

    public List<Command> render(int playerOn) {
        List<Command> commands = new ArrayList<>();

        if (playerOn == 2) {//addAll prendo una lista e l'aggiungo ad un'altra lista
        	//perchè explosion e enemeies e bulllets possono essere più di uno mentre player è uno
            commands.addAll(explosions.renderCommands());
            commands.addAll(enemies.renderCommands());
            
            if (player1.state != Player.State.GAME_OVER
                    && player2.state != Player.State.GAME_OVER) {
            	//render command del player fa un solo comando drawImageCommand
                commands.add(player1.renderCommand());
                commands.add(player2.renderCommand());
                commands.addAll(bulletsP1.renderCommands());
                commands.addAll(bulletsP2.renderCommands());
            } 
        } else {
            commands.add(new WaitCommand());
        }

        return commands;
    }
    
    private void handleInputP1() {
        if (inputP1.contains("Z")) {
                player1.jump();
        }

        if (inputP1.contains("X")) {
            createBulletP1();
            shootP1.value = true;
            inputP1.remove("X");
        }
    }
    private void handleInputP2() {
        if (inputP2.contains("Z")) {
                player2.jump();
        }

        if (inputP2.contains("X")) {
            createBulletP2();
            shootP2.value = true;
            inputP2.remove("X");
        }
    }
}
