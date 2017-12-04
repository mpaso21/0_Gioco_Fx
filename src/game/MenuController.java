package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController extends Group{

	final Stage primaryStage;//perchè devo rimpiazzare la scena con gamecontroller
	
	Button b1;
	
	public MenuController(final Stage primaryStage){
		this.primaryStage = primaryStage;
		
		b1 = new Button("START");
		b1.setPrefSize(100, 100);
		b1.setOnAction(e -> {
			Group controller = new GameController();
			Scene scena = new Scene(controller, 800, 600);
			this.primaryStage.setScene(scena);
//			this.primaryStage.show();

		});
		
		this.getChildren().add(b1);
		
	}
}
