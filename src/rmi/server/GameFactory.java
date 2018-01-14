package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactory extends Remote { //remote perchè se no non posso bindare il percorso all'istanza dell'oggetto
    int[] connection() throws RemoteException;//se il player si connette 
    void disconnect(int i) throws RemoteException; //se il player si disconette
}
