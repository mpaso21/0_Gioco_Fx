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
    Map<Integer, RMIGameController> games;// ad ogni partita(RMIGameController) è associato un id partita.
    int i;//id partita
    
    public RMIGameFactory(Registry r) throws RemoteException { 
        games = new HashMap<>();//inizializzo prima per evitare che quando aggiungo l'oggetto sia null
        executor = Executors.newCachedThreadPool();//executor gestore dei thread. ogni partita gira su un thread separato e ha il suo animation
        rand = new Random();
        this.r = r;
    }
    //mi ritorna un array di values: o sono il giocatore 1 oppure il 2 e ritorna l'id della partita
    
    @Override
    public int[] connection() throws RemoteException {//implemento i metodi dell'interfaccia
                
        int[] values = new int[2];//1 valore id partita 2 valore id giocatore
        //RMIGameCOntroller è una singola partita associata a due giocatori
        for(RMIGameController g : games.values()) {//mi connetto come secondo giocatore quindi prima c'era gà un giocatore in attesa quindi entro nel for
            if(g.playerOn<2) {//player0n inzialmente vale 1 perch� c'� solo un giocatore 
                g.playerOn++;//diventa 2 perchè mi connetto io 
                values[0] = i; //id partita
                values[1] = 2; //id giocatore
//                System.out.println(values[0] + " " + i);
                return values; //esco dal metodo connection
            }
        }
        
        //se game.values è vuoto(cioè la prima volta) allora creo la partita
        //vado qua se non ho trovato nessuna partita in cerca del secondo giocatore
        RMIGameController game = new RMIGameController();
        do {//verifico che la mia chiave (I) non sia presente in games. se è gia presente la ricalcolo per trovarne una unico
            i = rand.nextInt(); //evito che ci siano due partite con lo stesso id
        } while(games.get(i)!=null); 
                
        String s = "RMIGameController_"+i; //potrò associate l'oggetto game alla path RMIGameController_i
        
        games.put(i, game);//riempio mappa con id partita associato a partita appena creata
        r.rebind(s, game);//questo mi permette che il client possa ottenenre l'oggetto game tramite RMI
        executor.submit(game);//game implementa anche runnable quinid lo butto in submit e parte il run che conterrà l'animation che chiamerà update e render
        values[0] = i;//id partita
        values[1] = 1;//io sono il giocatore uno
        return values;
    }
    
    @Override//id partita
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
