package multiplayer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledFuture;
import javafx.application.Platform;
import javafx.stage.Stage;
import utility.WrapperValue;

public class ClientConnectionThr implements Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Stage stage;


    public Object inputObj;
    public String message;
    public boolean conn;

    public ClientConnectionThr(Stage stage) {
        this.stage = stage;
        this.message = "play";

        try {
            socket = new Socket("127.0.0.1", 2121);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            conn = true;
        } catch (IOException e) {
            conn = false;
        }

        if (conn) {
            setOnClose();
        }
    }

    public final void setOnClose() {
        if (in != null && out != null && socket != null) {
            stage.setOnCloseRequest(e -> {
                message = "quit";
            });
        }
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

    @Override
    public void run() {
        try {
            writeMessage();
            inputObj = read();
            if (message.equals("quit")) {
                Platform.runLater(()-> {
                    writeMessage();
                    conn = false;
                });
            }
        } catch (Exception e) {

        }
    }
    
    ScheduledFuture<?> t;
    public void setScheduledFuture(ScheduledFuture<?> t) {
        this.t = t;
    }
}
