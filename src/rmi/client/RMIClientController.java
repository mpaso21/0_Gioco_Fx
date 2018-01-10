package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import macroEntity.Background;
import rmi.GameControllerInterface;
import rmi.RMIGameController;
import rmi.server.GameFactory;
import utility.Command;
import utility.WrapperValue;

public class RMIClientController extends Group {

    private Stage stage;
    Canvas c;
    GraphicsContext gc;
    int id[];
    GameControllerInterface controller;
    
    public RMIClientController(Stage stage) {
        this.stage = stage;
        this.c = new Canvas(1600, 800);
        this.gc = c.getGraphicsContext2D();
        this.getChildren().add(c);
        start();
    }

    private void start() {

        try {
            GameFactory game
                    = (GameFactory) Naming.lookup("rmi://localhost/RMIGameFactory");

            id = game.connection();

            controller = (GameControllerInterface) Naming.lookup("rmi://localhost/RMIGameController_" + id[0]);
            
            new AnimationTimer() {
                WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());
                Background background = new Background();

                @Override
                public void handle(long currentNanoTime) {
                    double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                    lastNanoTime.value = currentNanoTime;

                    gc.clearRect(0, 0, 1600, 800);
                    try {
                        if(controller.gameStarted()) {
                            
                            background.update(elapsedTime);
                            background.render(gc);
                        }
                        for (Command c : controller.render()) {
                            c.draw(gc);
                        }
                    } catch (RemoteException ex) {
//                        ex.printStackTrace();
                    }
                }
            }.start();

        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

    }
    
    public void initEvents(Scene scena) {
        scena.setOnKeyPressed(e -> { 
            String code = e.getCode().toString();
            try {
                controller.keyPressed(id[1], code);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        scena.setOnKeyReleased(e -> { 
            String code = e.getCode().toString();
            try {
                controller.keyReleased(id[1],code);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }
}
