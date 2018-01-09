package multiplayer.server;

import java.util.concurrent.ScheduledFuture;
import javafx.animation.AnimationTimer;
import multiplayer.MultiplayerWorld;
import utility.WrapperValue;

public class MultiplayerGame implements Runnable {

    private final ServerConnectionHandler p1;
    private final ServerConnectionHandler p2;
    private MultiplayerWorld world;
    private MultiplayerModel model;
    public AnimationTimer a;

    private final long startNanoTime;
    private WrapperValue<Long> lastNanoTime;
    
    public MultiplayerGame(ServerConnectionHandler[] clientList) {
        p1 = clientList[0];
        p2 = clientList[1];

        p1.notInGame = false;
        p1.sendId("1");

        p2.notInGame = false;
        p2.sendId("2");

        world = new MultiplayerWorld();
        model = new MultiplayerModel(world);

        startNanoTime = System.nanoTime(); //startNanoTime
        lastNanoTime = new WrapperValue<>(System.nanoTime());

    }


//    int x=0;
    @Override
    public void run() {
//        System.out.println(System.currentTimeMillis() + " run "+x);
//        x++;
        if(p1.inGame && p2.inGame) {
            final long currentNanoTime = System.nanoTime(); //startNanoTime
            double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
            double t = (currentNanoTime - startNanoTime) / 1000000000.0;
            lastNanoTime.value = currentNanoTime;
            model.update(elapsedTime, t);

            p1.send(world);
            p2.send(world);
        } else {
            t.cancel(true);
        }
    }
    
    ScheduledFuture<?> t;
    public void setScheduledFuture(ScheduledFuture<?> t) {
        this.t = t;
    }

}
