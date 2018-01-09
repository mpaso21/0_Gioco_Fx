package menu;

import game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import multiplayer.client.MultiplayerController;
import rmi.client.RMIClientController;


public class FXMLMenuController {
    
    private Stage stage;
    
    @FXML private Text noConnection;

    
	
    @FXML protected void handleSinglePlayer(ActionEvent event) {//background single
        GameController controller = new GameController(stage);
        Scene scena = new Scene(controller, 800, 600);
        controller.initEvents(scena);
        stage.setScene(scena);
    }
    
    @FXML protected void handleMultiPlayer(ActionEvent event) {
//  	MultiplayerController controller = new MultiplayerController(stage); se uso socket
        RMIClientController controller = new RMIClientController(stage); //se uso RMI  ogni volta commento 
    	Scene scena = new Scene(controller, 800, 600);
        stage.setScene(scena);
    }
    
    public void init(Stage primaryStage, Parent root) {//menu
        this.stage = primaryStage;
        
        Scene scena = new Scene(root, 800, 600);
        stage.setScene(scena);
        stage.show();//Show fatto una volta va beene x sempre 
		
    }
    
    public void init(Stage primaryStage, Parent root, boolean noConnection) {//menu
        this.stage = primaryStage;
        this.noConnection.setVisible(noConnection);
        
        Scene scena = new Scene(root, 800, 600);
        stage.setScene(scena);
        stage.show();//Show fatto una volta va bene x sempre 
    }
}
