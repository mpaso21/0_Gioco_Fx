package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactory extends Remote {
    int[] connection() throws RemoteException;
}
