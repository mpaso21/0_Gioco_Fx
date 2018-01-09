package multiplayer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.Assets;
import utility.*;

public class ServerController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Assets.load();
        startServer();
    }

    private void startServer() throws Exception {
        ServerConnectionHandler[] clientList = new ServerConnectionHandler[2];
        
        ExecutorService executor = Executors.newCachedThreadPool();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
        
        ServerSocket serverSocket = new ServerSocket(2121);
        System.out.println("Server ready on port 2121");
     
        WrapperValue<Integer> connections = new WrapperValue<>(0);
                
        while(true) {
                try {
                    if(connections.value<2) {
                        Socket socket = serverSocket.accept();

                        clientList[connections.value] = new ServerConnectionHandler(socket, connections);
                        ScheduledFuture<?> t;
                        t = scheduler.scheduleAtFixedRate(clientList[connections.value], 0,  16666, TimeUnit.MICROSECONDS);
                        clientList[connections.value].setScheduledFuture(t);
//                        executor.submit(clientList[connections.value]);
                        connections.value++;
                    } else {
                      //inizia partita  
                      MultiplayerGame m = new MultiplayerGame(clientList);
                      ScheduledFuture<?> t;
                      t = scheduler.scheduleAtFixedRate(m, 0,  16666, TimeUnit.MICROSECONDS);
                      m.setScheduledFuture(t);
//                      executor.submit(new MultiplayerGame(clientList));
                      connections.value = 0;
                    }
                } catch (IOException e) {
                    break;
                }
        }
        executor.shutdown();
        scheduler.shutdown();
    }
}
