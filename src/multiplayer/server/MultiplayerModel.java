package multiplayer.server;

import entity.Player;
import multiplayer.MultiplayerWorld;
import utility.Constants;

public class MultiplayerModel {

    MultiplayerWorld world;

    public MultiplayerModel(MultiplayerWorld world) {
        this.world = world;
    }

    public void update(double elapsedTime, double t) {

        if (world.player1.state != Player.State.GAME_OVER
                && world.player2.state != Player.State.GAME_OVER) {

            if (Constants.MORTAL) {
                world.player1.intersectsEnemies(world.intersectsEnemies());
                world.player2.intersectsEnemies(world.intersectsEnemies());
            }

            world.intersectsBullets();

            world.player1.update(elapsedTime, t);
            world.player2.update(elapsedTime, t);
            world.updateBullets(elapsedTime);

        } else {

        }

        world.updateEnemies(elapsedTime);

        world.updateBackground(elapsedTime);

        world.updateExplosions(elapsedTime, t);

    }

}
