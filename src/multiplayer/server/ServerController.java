package multiplayer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.*;

public class ServerController extends Application{

	private AnimationTimer a;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startServer();
	}
	
	private void startServer() throws Exception {
		ServerSocket serverSocket = new ServerSocket(2121);
		System.out.println("start server");
		Socket socket = serverSocket.accept();
		
	   	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	   	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	   	
	   	a = new AnimationTimer() {//interfaccia funzionale


	        @Override
	        public void handle(long currentNanoTime) {
	            try {
	            	out.writeObject(new WrapperValue<String>("wait!"));
	            	out.flush();
	            	out.reset();
	            	
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} 
	        }

	    };
	    
	    a.start();
	}

}
