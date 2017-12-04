package application;
	
import game.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.Assets;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Assets.load(); //carico assets
		init(primaryStage);//setto il titolo 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void init(Stage primaryStage){ //mi si apre una finestra
		primaryStage.setTitle("MY FUCKING GAME");
		primaryStage.setResizable(false);//la finestra non può essere ridimensionata
	
		GameController controller = new GameController();
		Scene scena = new Scene(controller, 800, 600);
		controller.initEvents(scena);
		primaryStage.setScene(scena);
		
		primaryStage.show();
	}
}
