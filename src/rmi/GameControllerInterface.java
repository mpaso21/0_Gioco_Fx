package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import utility.Command;

public interface GameControllerInterface extends Remote {
    List<Command> render() throws RemoteException;
    boolean gameStarted() throws RemoteException;
    void keyPressed(int id, String code) throws RemoteException;
    void keyReleased(int id, String code) throws RemoteException;
}
