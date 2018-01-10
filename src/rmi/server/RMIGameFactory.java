package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import rmi.RMIGameController;

public class RMIGameFactory extends UnicastRemoteObject implements GameFactory {
    
    Registry r;
    ExecutorService executor;
    List<RMIGameController> games;
    int i;
    int id;
    
    public RMIGameFactory(Registry r) throws RemoteException { 
        games = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
        this.r = r;
        this.i = 0;
        this.id = 1;
    }
    
    @Override
    public int[] connection() throws RemoteException {
                
        int[] values = new int[2];
        
        for(RMIGameController g : games) {
            if(g.playerOn<2) {
                g.playerOn++;
                values[0] = i++;
                values[1] = id;
                id = 1;
                return values;
            }
        }
        
        RMIGameController game = new RMIGameController();
        String s = "RMIGameController_"+i;
        
        games.add(game);
        r.rebind(s, game);
        f = executor.submit(game);
        values[0] = i;
        values[1] = id++;
        return values;
    }
    Future<?> f;
    
    @Override
    public void disconnect() throws RemoteException {
        games.get(0).disconnect();
        System.out.println(f.isDone());
    }
}
