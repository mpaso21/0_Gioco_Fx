package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import macroEntity.Background;
import menu.FXMLMenuController;
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
    private Map<String, Double> gameOverWait;

    public RMIClientController(Stage stage) {
        this.stage = stage;
        this.c = new Canvas(1600, 800);
        this.gc = c.getGraphicsContext2D();
        this.getChildren().add(c);
        this.gameOverWait = new HashMap<>();

        start();
    }

    private void start() {

        try {
            GameFactory game
                    = (GameFactory) Naming.lookup("rmi://localhost/RMIGameFactory");

            id = game.connection();

            controller = (GameControllerInterface) Naming.lookup("rmi://localhost/RMIGameController_" + id[0]);
            
            new AnimationTimer() {

                final long startNanoTime = System.nanoTime(); //startNanoTime
                WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());
                Background background = new Background();

                @Override
                public void handle(long currentNanoTime) {
                    double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                    lastNanoTime.value = currentNanoTime;
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                    gc.clearRect(0, 0, 1600, 800);
                    try {
                        if (controller.gameStarted()) {

                            background.update(elapsedTime);
                            background.render(gc);
                        }
                        for (Command c : controller.render()) {
                            c.draw(gc);
                        }
                        if (controller.gameIsOver()) {
                            renderGameOver(gc);
                            gameOverWait.putIfAbsent("initTime", t);
                            gameOverWait.put("current", t);
                            if (gameOverWait.get("current") - gameOverWait.get("initTime") >= 4) {
                                loadMenu();
                                game.disconnect();
                                this.stop();
                            }
                        }
                    } catch (RemoteException ex) {
//                        ex.printStackTrace();
                    }
                }
            }.start();

        } catch (NotBoundException ex) {
//            ex.printStackTrace();
        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
        } catch (RemoteException ex) {
//            ex.printStackTrace();
        }
    }

    public void loadMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../menu/Fxml_menu.fxml"));
            Parent root = loader.load();
            FXMLMenuController controller = loader.getController();
            controller.init(stage, root);
        } catch (Exception ex) {
            System.err.println("UNABLE TO LOAD MAIN MENU");
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
                controller.keyReleased(id[1], code);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }


    float font = 25f;
    private void renderGameOver(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        if (font < 110) {
            font += 0.3;
        }
        final Font theFont = Font.font("Bungee", FontWeight.BOLD, font);
        gc.setFont(theFont);
        gc.fillText("GAME OVER", 80, 300);
        gc.strokeText("GAME OVER", 80, 300);
    }
}
