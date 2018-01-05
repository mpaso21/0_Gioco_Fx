package multiplayer.client;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;
import menu.FXMLMenuController;
import multiplayer.MultiplayerWorld;

public class MultiplayerController extends Group {

	private MultiplayerView view;
	private ClientConnection connection;
	private AnimationTimer animation;
	private Stage stage;

	public MultiplayerController(Stage primaryStage) {
		this.stage = primaryStage;
		view = new MultiplayerView(this);
		connection = new ClientConnection(); // creo connession
		start();
	}

	boolean timeOver=false;
	
	public void start() {
		final long startNanoTime = (long) (System.nanoTime() / 1000000000.0);
		long currentNanoTime = (long) (System.nanoTime() / 1000000000.0);
		boolean tryToConnect = connection.tryToConnect();

		while (tryToConnect == false && (currentNanoTime - startNanoTime) <= 5) { // while
			tryToConnect = connection.tryToConnect();
			currentNanoTime = (long) (System.nanoTime() / 1000000000.0); // riaggiorno currentTime
			if ((currentNanoTime - startNanoTime) >= 5) {
				timeOver = true;
			}
		}

		animation = new AnimationTimer() {// interfaccia funzionale, implemento
			// handle
			@Override
			public void handle(long arg0) {
				if (timeOver) {
					loadMenu();
					animation.stop();
				} else {
					
					Object obj = connection.read();
					view.render(obj);
				}
			}
		};
		
		animation.start();
	}

	public void loadMenu() {

		Platform.runLater(() -> {

			try {
				animation.stop();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../../menu/Fxml_menu.fxml"));
				Parent root;
				root = loader.load();
				FXMLMenuController controller = loader.getController();
				controller.init(stage, root);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

	}
}
