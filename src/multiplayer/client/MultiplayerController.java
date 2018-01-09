package multiplayer.client;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import menu.FXMLMenuController;
import multiplayer.MultiplayerWorld;
import utility.WrapperValue;

public class MultiplayerController extends Group {

    private MultiplayerView view;
    private ClientConnection connection;
    private AnimationTimer animation;
    private Stage stage;

    public MultiplayerController(Stage primaryStage) {
        this.stage = primaryStage;
        view = new MultiplayerView(this);
        connection = new ClientConnection(primaryStage); // creo connession
        start();
    }

    public void start() {

        boolean tryToConnect = connection.tryToConnect();
        
        if(tryToConnect) {
            connection.setOnClose();
        }

        animation = new AnimationTimer() {// interfaccia funzionale, implemento
            // handle
            @Override
            public void handle(long arg0) {
                if (!tryToConnect) {
                    loadMenu(true);
                    animation.stop();
                } else {
                    try {
                        connection.writeMessage();
                        Object obj = connection.read();
                        view.render(obj);

                        if (connection.message.equals("quit")) {
                            connection.closeConnection();
                            animation.stop();
                        }
                        
                        
                    } catch(Exception e) {
                        loadMenu(true);
                        animation.stop();
                    }
                }
            }
        };

        animation.start();
    }

    public void loadMenu(boolean noConnection) {

        Platform.runLater(() -> {

            try {
                animation.stop();
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
}
//
//package multiplayer.client;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//import javafx.animation.AnimationTimer;
//import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Group;
//import javafx.scene.Parent;
//import javafx.stage.Stage;
//import menu.FXMLMenuController;
//
//public class MultiplayerController extends Group {
//
//    private MultiplayerView view;
//    private ClientConnectionThr connection;
//    private AnimationTimer animation;
//    private Stage stage;
//
//    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
//    ScheduledFuture<?> t;
//
//    public MultiplayerController(Stage primaryStage) {
//        this.stage = primaryStage;
//        view = new MultiplayerView(this);
//        
//        connection = new ClientConnectionThr(primaryStage); // creo connessione
//        t = scheduler.scheduleAtFixedRate(connection, 0, 16666, TimeUnit.MICROSECONDS);
//        connection.setScheduledFuture(t);
//        start();
//    }
//
//    public final void start() {
//        
//        animation = new AnimationTimer() {// interfaccia funzionale, implemento
//            // handle
//            @Override
//            public void handle(long arg0) {
//                if(!connection.conn) {
//                    Platform.runLater(()-> {
//                        connection.closeConnection();
//                        t.cancel(true);
//                        scheduler.shutdown();
//                        loadMenu(true);
//                        animation.stop();
//                    });
//                } else {
//                    view.render(connection.inputObj);
//                }
//            }
//        };
//
//        animation.start();
//    }
//
//    public void loadMenu(boolean noConnection) {
//
//        Platform.runLater(() -> {
//
//            try {
////                animation.stop();
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../menu/Fxml_menu.fxml"));
//                Parent root;
//                root = loader.load();
//                FXMLMenuController controller = loader.getController();
//                controller.init(stage, root, noConnection);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//
//    }
//}

