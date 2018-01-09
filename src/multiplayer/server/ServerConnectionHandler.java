package multiplayer.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledFuture;
import multiplayer.MultiplayerWorld;
import utility.WrapperValue;

public class ServerConnectionHandler implements Runnable {
    private Socket socket;
    private Object outputMessage;
    
    public String inputMessage;
    public boolean inGame = true;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private WrapperValue<Integer> connections;
    public boolean notInGame = true;
    
    public ServerConnectionHandler(Socket socket, WrapperValue<Integer> connections) {
        this.socket = socket;
        this.outputMessage = new WrapperValue<String>("wait!");
        this.connections = connections;
        
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(Exception e ) {
            
        }
    }//wrapper ci passo l'indirizzo primitive ci passo il valore
    
    @Override
    public void run() {
        try {
//          while(true) {

                WrapperValue<String> input = (WrapperValue<String>) in.readObject();
                
                if(input.value.equals("quit")) {
                    inputMessage = "quit";
                    if(notInGame) {
                        connections.value--;// con riferimento a seerver controller che gestisce le connections
                    } else {
                        inGame = false;                        
                    }
                    in.close();
                    out.close();
                    socket.close(); 
                    t.cancel(true);
//                    break;
                } else {          
                    out.writeObject(outputMessage);
                    out.flush();
                    out.reset();
//                }            
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void sendId(String id) {
        this.outputMessage = new WrapperValue<String>(id);
    }
    
    public void send(MultiplayerWorld w) {
        outputMessage = w;
    }
    
    ScheduledFuture<?> t;
    public void setScheduledFuture(ScheduledFuture<?> t) {
        this.t = t;
    }
}
