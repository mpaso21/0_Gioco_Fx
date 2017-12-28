package application;
	
import java.io.IOException;

import game.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import resources.Assets;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Assets.load(); //carico assets
		init(primaryStage);//setto il titolo 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void init(Stage primaryStage) throws IOException{ //mi si apre una finestra
		primaryStage.setTitle("MY FUCKING GAME");
		primaryStage.setResizable(false);//la finestra non pu� essere ridimensionata
	
		//GameController controller = new GameController();
		Parent root = FXMLLoader.load(getClass().getResource("../menu/Fxml_menu.fxml"));
		Scene scena = new Scene(root, 800, 600);
		//Scene scena = new Scene(controller, 800, 600);
		//controller.initEvents(scena);
		primaryStage.setScene(scena);
		
		primaryStage.show();
	}
}
