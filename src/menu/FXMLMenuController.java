package menu;

import game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FXMLMenuController {
    
    private Stage stage;
    
    @FXML private Text actiontarget;

    
	
    @FXML protected void handleSinglePlayer(ActionEvent event) {
        GameController controller = new GameController(stage);
        Scene scena = new Scene(controller, 800, 600);
        controller.initEvents(scena);
        stage.setScene(scena);
    }
    
    @FXML protected void handleMultiPlayer(ActionEvent event) {

    }
    
    public void init(Stage primaryStage, Parent root) {
        this.stage = primaryStage;
        
        Scene scena = new Scene(root, 800, 600);
        stage.setScene(scena);
        stage.show();
		
    }
}
