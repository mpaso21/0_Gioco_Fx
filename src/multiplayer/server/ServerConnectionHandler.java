package multiplayer.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import multiplayer.MultiplayerWorld;
import utility.WrapperValue;

public class ServerConnectionHandler implements Runnable {
    private Socket socket;
    private Object outputMessage;
    private String inputMessage;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private WrapperValue<Integer> connections;
    public boolean notInGame = true;
    
    public ServerConnectionHandler(Socket socket, WrapperValue<Integer> connections) {
        this.socket = socket;
        this.outputMessage = new WrapperValue<String>("wait!");
        this.connections = connections;
    }//wrapper ci passo l'indirizzo primitive ci passo il valore
    
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
          
          while(true) {
                WrapperValue<String> input = (WrapperValue<String>) in.readObject();
                
                if(input.value.equals("quit")) {
                    inputMessage = "quit";
                    if(notInGame) {
                        connections.value--;// con riferimento a seerver controller che gestisce le connections
                    }
                    break;
                } else {          
                    out.writeObject(outputMessage);
                    out.flush();
                    out.reset();
                }            
            }
            in.close();
            out.close();
            socket.close();
        } catch(Exception e) {
            
        }
    }
    
    public void sendId(String id) {
        this.outputMessage = new WrapperValue<String>(id);
    }
    
    public void send(MultiplayerWorld w) {
        outputMessage = w;
    }
    
}
