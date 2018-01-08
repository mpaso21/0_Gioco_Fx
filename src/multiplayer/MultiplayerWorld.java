package multiplayer;

import entity.Player;
import java.io.Serializable;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import macroEntity.Background;
import macroEntity.Bullets;
import macroEntity.Enemies;
import macroEntity.Explosions;
import utility.Vector2d;

public class MultiplayerWorld implements Serializable {

    public Player player1;
    public Player player2;

    private final Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe

    private final Bullets bulletsP1;
    private final Bullets bulletsP2;
    private final Background backgrounds;
    private final Explosions explosions;

    public MultiplayerWorld() {
        player1 = new Player();
        player2 = new Player();
        enemies = new Enemies();
        bulletsP1 = new Bullets(player1);
        bulletsP2 = new Bullets(player2);
        backgrounds = new Background();
        explosions = new Explosions();
    }

    //////////////////////// ENEMIES ///////////////////////////////////////
    public void updateEnemies(double elapsedTime) {
        enemies.update(elapsedTime);
    }

    public boolean intersectsEnemies() {
        return enemies.intersects(player1) || enemies.intersects(player2);
    }
    //////////////////////// ENEMIES ///////////////////////////////////////

    /////////////////////////BULLETS/////////////////////////////////////////
//    public void createBullet() {
//        bullets.create();
//    }
    public void updateBullets(double elapsedTime) {
        bulletsP1.update(elapsedTime);
        bulletsP2.update(elapsedTime);
    }

    public void intersectsBullets() {
        List<Vector2d> list = bulletsP1.intersects(enemies);
        if (!list.isEmpty()) {
//			System.out.println(list);	
            explosions.create(list);
        }
        list = bulletsP2.intersects(enemies);
        if (!list.isEmpty()) {
//			System.out.println(list);	
            explosions.create(list);
        }
    }
    /////////////////////BULLETS/////////////////////////////////////////////////

    /////////////////////////////EXPLOSIONS///////////////////////////////////////
    public void updateExplosions(double elapsedTime, double t) {
        explosions.update(elapsedTime, t);
    }
    //////////////////////////////EXPLOSIONS///////////////////////////////////

    ///////////////////////BACKGROUND//////////////////////////////////////////
    public void updateBackground(double elapsedTime) {
        backgrounds.update(elapsedTime);
    }
    /////////////////////////BACKGROUNDS//////////////////////////////////////

    float font = 25f;

    public void renderAll(GraphicsContext gc) {
        backgrounds.render(gc);
        explosions.render(gc);
        enemies.render(gc);
        if (player1.state != Player.State.GAME_OVER
                && player2.state != Player.State.GAME_OVER) {
            player1.render(gc);
            player2.render(gc);
            bulletsP1.render(gc);
            bulletsP2.render(gc);
        } else {
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            if (font < 110) {
                font += 0.3;
            }
            final Font theFont = Font.font("Bungee", FontWeight.BOLD, font);
            gc.setFont(theFont);
            gc.fillText("GAME OVER", 80, 300);
            gc.strokeText("GAME OVER", 80, 300);
        }
    }
}
