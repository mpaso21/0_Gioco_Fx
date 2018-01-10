package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import utility.Command;
import utility.Constants;
import utility.WrapperValue;

public class RMIGameController extends UnicastRemoteObject implements GameControllerInterface, Runnable {

    public int playerOn;

    RMIWorld world;
    AnimationTimer at;
    
    public RMIGameController() throws RemoteException {
        playerOn = 1;
        world = new RMIWorld();
    }

    @Override
    public List<Command> render() throws RemoteException {
        return world.render(playerOn);
    }

    @Override
    public boolean gameStarted() throws RemoteException {
        return playerOn == 2;
    }

    @Override
    public boolean gameIsOver() throws RemoteException {
        return world.gameIsOver();
    }

    @Override
    public void keyPressed(int id, String code) {
        if (id == 1) {
            if (!world.inputP1.contains(code)) {

                if (code.equals("X")) {//fa in modo che la x non rimanga premuta, mentre tutti gli altri tasti possono rimanere premuti+
                    //sparo un bullets alla volta non a raffica mentre il jump clicco e continuo ad andare in alto fino a quando tocco il bordo
                    if (!world.shootP1.value) {
                        world.inputP1.add(code);
                    }
                } else {
                    world.inputP1.add(code);
                }
            }
        } else if (id == 2) {
            if (!world.inputP2.contains(code)) {

                if (code.equals("X")) {//fa in modo che la x non rimanga premuta, mentre tutti gli altri tasti possono rimanere premuti+
                    //sparo un bullets alla volta non a raffica mentre il jump clicco e continuo ad andare in alto fino a quando tocco il bordo
                    if (!world.shootP2.value) {
                        world.inputP2.add(code);
                    }
                } else {
                    world.inputP2.add(code);
                }
            }
        }
    }

    @Override
    public void keyReleased(int id, String code) {
        if (id == 1) {
            if (code.equals("X")) {
                world.shootP1.value = false;
                world.inputP1.remove(code);

            } else {
                world.inputP1.remove(code);
            }
        } else if (id == 2) {
            if (code.equals("X")) {
                world.shootP2.value = false;
                world.inputP2.remove(code);

            } else {
                world.inputP2.remove(code);
            }
        }
    }

    @Override
    public void run() {
        at = new AnimationTimer() {
            final long startNanoTime = System.nanoTime(); //startNanoTime
            WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());
            
            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                if (playerOn == 2) {
                    //inizio l'update...
                    world.update(elapsedTime, t);
                }
            }
        };
        at.start();
    }
    
    public void disconnect() {
        at.stop();
    }
}
