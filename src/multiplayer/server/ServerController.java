package multiplayer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import multiplayer.MultiplayerWorld;
import utility.*;

public class ServerController extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startServer();
    }

    private void startServer() throws Exception {
        ServerConnectionHandler[] clientList = new ServerConnectionHandler[2];
        
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(2121);
        System.out.println("Server ready on port 2121");
     
        WrapperValue<Integer> connections = new WrapperValue<>(0);
                
        while(true) {
                try {
                    if(connections.value<2) {
                        Socket socket = serverSocket.accept();

                        clientList[connections.value] = new ServerConnectionHandler(socket, connections);

                        executor.submit(clientList[connections.value]);

                        connections.value++;
                    } else {
                      //inizia partita  

                      startGame(clientList);
                      connections.value = 0;
                    }
                } catch (IOException e) {
                    break;
                }
        }
        
        executor.shutdown();

    }
    
    private void startGame(ServerConnectionHandler[] clientList) {
        
        ServerConnectionHandler p1 = clientList[0];
        p1.notInGame = false;
        p1.sendId("1");
        
        ServerConnectionHandler p2 = clientList[1];
        p2.notInGame = false;
        p2.sendId("2");
        
        MultiplayerWorld world = new MultiplayerWorld();
        MultiplayerModel model = new MultiplayerModel(world);

        final long startNanoTime = System.nanoTime(); //startNanoTime

       AnimationTimer a = new AnimationTimer() {//interfaccia funzionale

            WrapperValue<Long> lastNanoTime = new WrapperValue<Long>(System.nanoTime());
            
            @Override
            public void handle(long currentNanoTime) {
            
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;    
                
                model.update(elapsedTime, t);
                
                p1.send(world);
                p2.send(world);

//                p1.send();
//                p2.send();

//                try {
////                    if(connections.value<2) {
////                        Socket socket = serverSocket.accept();
////                        executor.submit(new ServerConnectionHandler(socket));
////                        connections.value++;
////                    } else {
////                        
////                        
////                    }
////                    
////                    out.writeObject(new WrapperValue<String>("wait!"));
////                    out.flush();
////                    out.reset();
//
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
////					e.printStackTrace();
//                }
            }

        };

        a.start();
    }

}
