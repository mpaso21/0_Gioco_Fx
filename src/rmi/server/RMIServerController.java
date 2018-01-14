package rmi.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.FXMLMenuController;
import resources.Assets;

public class RMIServerController extends Application {

    public static void main(String args[]) {
        Assets.load();//carico assets anche qui perchè gira su un altra virtual machine
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //classe Registry mi creare un registro dove posso associare dei domini a oggetti es. rmi://localhost/rmigame 
            //istanzio l'oggetto rmigame all'indirizzo rmi://localhost/rmigame
            //creo il server sul mio pc che risponde tramite protocollo rmi sulla porta 1099
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);//tiene una tabella con i nomi associati agli oggetti di tipo remote
            RMIGameFactory server = new RMIGameFactory(r);
            r.rebind("RMIGameFactory", server);//associo all'oggetto server il nome RMIGAameFactory
            
             primaryStage.setTitle("Server");
            primaryStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
            Parent root = loader.load();
            FXML_Server_controller controller = loader.getController();//CARICO la classe controller che ho associato nell fxml
            controller.init();
           Scene scena = new Scene(root, 400, 300); 
            primaryStage.setScene(scena);
            primaryStage.show();
            System.out.println("Server on");
            
            primaryStage.setOnCloseRequest(e -> {
                try {
                    r.unbind("RMIGameFactory");
                } catch (RemoteException ex) {
                    Logger.getLogger(RMIServerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(RMIServerController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Platform.exit();
                    System.exit(0);
                }
            });

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }    
}