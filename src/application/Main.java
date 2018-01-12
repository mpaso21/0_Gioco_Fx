package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import resources.Assets;
import javafx.scene.Parent;
import menu.FXMLMenuController;

//entry-point(punto di accesso della mia applicazione) clicco run e parte il metodo main
public class Main extends Application {//extends application perchè sono in javafx (usare animation timer, canvas, fxml) 
	@Override //2 inizia lo start //primary stage è stata creata nel launch e passata allo start
	public void start(Stage primaryStage) throws IOException {
		Assets.load(); //carico assets(varie immagini) prima di iniziare il gioco 
		init(primaryStage);//chiamo il metodo init(privato sotto) setto il titolo 
	}
	//1 eseguo sempre il main per prima come metodo
	public static void main(String[] args) {
		launch(args);//launch(metodo che fa parte della classe applicazione) chiama il metodo start
                
	}
	
	private void init(Stage primaryStage) throws IOException{ //mi si apre una finestra
		primaryStage.setTitle("MY FUCKING GAME");//setto il tito della finestra
		primaryStage.setResizable(false);//impongo che la finestra non pu� essere n� ingrandita n� rimpicciolita
                
        //Creo fxml del menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/Fxml_menu.fxml"));
		Parent root = loader.load();//carico tutta la struttura definita nel fxml partendo dal nodo padre che ho chiamato root ma è gridpane(figli  text button)
        //associata con fx:controller
        FXMLMenuController controller = loader.getController();//CARICO la classe controller che ho associato nell fxml
        controller.init(primaryStage, root); //perchèv faccio cosi? perchè cosi posso chiamare dei metodi su una struttura generata con codice fxml(per es. voglio creare un oggetto che in fxml non c'è)
	}
}
