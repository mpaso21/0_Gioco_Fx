package menu;

import game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rmi.client.RMIClientController;


public class FXMLMenuController {
    
    private Stage stage;
    //annotation FXML
    @FXML private Text noConnection;
//clicco pulsante e java intercetta l'evento che ho cliccato e chiama il metodo handlesingelpalyer
    @FXML protected void handleSinglePlayer(ActionEvent event) {//background single
        GameController controller = new GameController(stage); //creo un nuovo controller in codice non in fxml
        Scene scena = new Scene(controller, 800, 600); //finito l'animation.start ritorno a creare la scena a cui passo il controller (a cui ho associato canvas e gc)
        controller.initEvents(scena);
        stage.setScene(scena);//mi si carica la nuova scena cioè il nuovo group e tutti i suoi nodi
        //stage menu con fxml a stage ci attacco parent(che era associato a fxml)
        //stage background single player a stage ci attacco group
    }
    
    @FXML protected void handleMultiPlayer(ActionEvent event) {
       RMIClientController controller = new RMIClientController(stage); //se uso RMI  ogni volta commento 
    	Scene scena = new Scene(controller, 800, 600);
       controller.initEvents(scena);
        stage.setScene(scena);
    }
    
    public void init(Stage primaryStage, Parent root) {//menu
        this.stage = primaryStage; //inizializza stage per far sì che non sia null setta stage del controller
        
        Scene scena = new Scene(root, 800, 600); //crei la scena (root contiene le cose definite nel fxml)
        stage.setScene(scena);//setto la scena
        stage.show();//Show fatto una volta va bene x sempre  mostra la scena contenuta nella stage
	//da main con show si apre la pagina del menu	
    }
    
    public void init(Stage primaryStage, Parent root, boolean noConnection) {//menu
        this.stage = primaryStage;
        this.noConnection.setVisible(noConnection);
        
        Scene scena = new Scene(root, 800, 600);
        stage.setScene(scena);
        stage.show();//Show fatto una volta va bene x sempre 
    }
}
