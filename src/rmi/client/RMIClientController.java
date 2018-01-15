package rmi.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private AnimationTimer at;

    public RMIClientController(Stage stage) {
        this.stage = stage;
        this.c = new Canvas(1600, 800);
        this.gc = c.getGraphicsContext2D();
        this.getChildren().add(c);
        this.gameOverWait = new HashMap<>();

        start();
    }

    private void start() {
//start non è il loop del gioco ma il loop di richista dei comandi e gestion input
        try {
            //io client cerco l'oggetto associato a RMIGameFactory, se lo trova lo restituisce
           //a questo punto ho un oggetto di tipo gameFactory sul quale posso chiamare connection e disconnect
           //io chiamo questi metodi ma questi metodi vengoo eseguiti effettivamente sul server(su un'altra JVM)
           //Io mando al server una richiesta di un metodo(remote invocation)
            GameFactory game //richiesta del gestore delle partite
                    = (GameFactory) Naming.lookup("rmi://localhost/RMIGameFactory");

            id = game.connection();
            //id[0] id relativo alla partita. mi ritorno l'oggetto game la partita vera e propria sul quale posso
            //chiamare i metodi dell'interfaccia GameControllerInterface
            //richiedo al server la mia partita
            controller = (GameControllerInterface) Naming.lookup("rmi://localhost/RMIGameController_" + id[0]);
            //quando clicco x in alto
            stage.setOnCloseRequest(e -> {
                try {
                    if(controller.gameStarted()) {
                        at.stop();
                    }
                    game.disconnect(id[0]);//stoppo animazione e disconetto partita
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
            //non è il loop vero del gioco. loop grafico in locale
            at = new AnimationTimer() {

                final long startNanoTime = System.nanoTime(); //startNanoTime
                WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());
                Background background = new Background();//l'unica roba che lascio in locale perch� non influisce sul gioco

                @Override
                public void handle(long currentNanoTime) {
                	//tempo trascorso ultima chiamata di handle
                    double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                    lastNanoTime.value = currentNanoTime;
                    //tempo trascorso da quando l'animazione � stata creata
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                    gc.clearRect(0, 0, 1600, 800);
                    try {
                        if (controller.gameStarted()) {
                            
                            background.update(elapsedTime);//background gira in locale perchè non serve averlo sincronizzato cosi evito peso alla serializzazione
                            background.render(gc);
                        }
                        //Command non sono altro che i render dei vari oggetti questo perchè non si poteva creare il render passandogli gc che non è serializable.
                        //tutto quello che va sulla rete deve essere serializable(trasformato in binario)
                        final List<Command> list = controller.render(); //mi ritorna una lista di comandi. ogni comandocontiene il metodo draw e quindi avviene il disegno.(dei bullets, explosions, player1, player2, enemy)
                        if(list!=null) {
                            for (Command c : list) {
                                c.draw(gc);
                            }
                        }
                        if (controller.gameIsOver()) {
                            renderGameOver(gc);
                            gameOverWait.putIfAbsent("initTime", t);
                            gameOverWait.put("current", t);
                            if (gameOverWait.get("current") - gameOverWait.get("initTime") >= 4) {
                                loadMenu();
                                game.disconnect(id[0]);//disconette la partita
                                this.stop();
                            }
                        }
                    } catch (RemoteException ex) {
//                        ex.printStackTrace();
                    }
                }
            };
            
            at.start();

        } catch (Exception ex) {
            loadMenu(true); //gestisco eccezione per es. problemi di connessione server offline caricando il menù 
        }
    }

    public void loadMenu() {//viene chiamato dove che la scritta game over si ingrandisce
        try {
            stage.setOnCloseRequest(e -> { });
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../menu/Fxml_menu.fxml"));
            Parent root = loader.load();
            FXMLMenuController controller = loader.getController();
            controller.init(stage, root);
        } catch (Exception ex) {
            System.err.println("UNABLE TO LOAD MAIN MENU");
            ex.printStackTrace();
        }
    }
    

    public void loadMenu(boolean noConnection) { //viene chiamato quando si verifica un errore di connessione
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../menu/Fxml_menu.fxml"));
                Parent root;
                root = loader.load();
                FXMLMenuController controller = loader.getController();
                controller.init(stage, root, noConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void initEvents(Scene scena) {
        scena.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            try { //id composta da due id partita e id giocatore
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
