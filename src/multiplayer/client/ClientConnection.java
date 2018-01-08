package multiplayer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.WrapperValue;

public class ClientConnection {

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;

    private Stage stage;

    public String message;

    public ClientConnection(Stage stage) {
        this.stage = stage;
        this.message = "play";
    }

    public void setOnClose() {
        if (in != null && out != null && socket != null) {
            stage.setOnCloseRequest(e -> {
                message = "quit";
            });
        }
    }

    public boolean tryToConnect() {
        try {
            socket = new Socket("127.0.0.1", 2121);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Object read() {
        Object o = null;
        try {
            o = in.readObject();
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return o;
    }

    public void writeMessage() {
        try {
            out.writeObject(new WrapperValue<String>(message));
            out.flush();
            out.reset();
        } catch (IOException ex) {

        }
    }

    public void closeConnection() {
        
        try {
            in.close();
            out.close();
            socket.close();    
        } catch (IOException ex) {

        }
        
    }
}
