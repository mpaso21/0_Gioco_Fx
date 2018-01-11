package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import rmi.RMIGameController;

public class RMIGameFactory extends UnicastRemoteObject implements GameFactory {
    
    Random rand;
    Registry r;
    ExecutorService executor;
    Map<Integer, RMIGameController> games;
    int i;
    int id;
    
    public RMIGameFactory(Registry r) throws RemoteException { 
        games = new HashMap<>();
        executor = Executors.newCachedThreadPool();
        rand = new Random();
        this.r = r;
        this.i = rand.nextInt();
        this.id = 1;
    }
    
    @Override
    public int[] connection() throws RemoteException {
                
        int[] values = new int[2];
        
        for(RMIGameController g : games.values()) {
            if(g.playerOn<2) {
                g.playerOn++;
                values[0] = i;
                values[1] = id;
                id = 1;
                
                do {
                    i = rand.nextInt();
                } while(games.get(i)!=null); 
                
//                System.out.println(values[0] + " " + i);
                return values;
            }
        }
        
        RMIGameController game = new RMIGameController();
        String s = "RMIGameController_"+i;
        
        games.put(i, game);
        r.rebind(s, game);
        executor.submit(game);
        values[0] = i;
        values[1] = id++;
        return values;
    }
    
    @Override
    public void disconnect(int i) throws RemoteException {
        if(games.get(i).disconnect()) {
            try {
                r.unbind("RMIGameController_"+i);
            } catch (Exception ex) {      } 
            games.remove(i);
//            System.out.println("rmi.server.RMIGameFactory.disconnect() "+ games.size());
        }
    }
}
