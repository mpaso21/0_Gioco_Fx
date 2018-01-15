package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import resources.Assets;

public class RMIServerController extends Application {

    public static void main(String args[]) {
        Assets.load();//carico assets perchè avere le stringhe delle immagini
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            RMIGameFactory server = new RMIGameFactory(r);
            r.rebind("RMIGameFactory", server);
            System.out.println("Server on");

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }    
}