package game;

import entity.Bullet;
import entity.Player;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.Assets;

public class WorldView {

    final World world;
    private GraphicsContext gc;

    public WorldView(final Group group, final World world) {
        this.world = world;
        init(group);
    }
    //aggiorna
    //pulisco e ridisegno per 60 volte usando le caratteristiche degli oggetti di world

    public void update(double elapsedTime) {
        render();
    }

    
    float font = 25f;

    private void render() {
        gc.clearRect(0, 0, 1600, 800);
        world.renderBackground(gc);
        world.renderExplosion(gc);
        //gc.drawImage(Assets.background, 0, 0);
        world.renderEnemies(gc);

        if (world.player.state != Player.State.GAME_OVER) {
            world.player.render(gc);
            world.renderBullets(gc);
        } else {
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            if(font < 110) {
                font += 0.3;
            }
            final Font theFont = Font.font("Bungee", FontWeight.BOLD, font);
            gc.setFont(theFont);
            gc.fillText("GAME OVER", 80, 300);
            gc.strokeText("GAME OVER", 80, 300);
        }


    }

    private void init(Group group) {

        group.getChildren().add(world.canvas);

        gc = world.canvas.getGraphicsContext2D();

//		gc.setFill(Color.RED);
//		gc.fillRect(0, 0, 1600, 800);
//		gc.setFill(Color.BLUE);
//		gc.fillRect(1300, 200, 100, 100);
        gc.setFill(Color.BLACK);
//		
//		canvas.setTranslateX(400);
//		
//		Random r = new Random();
//		for(int i=0; i<1000; i++) {
//			gc.setFill(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
//			gc.fillRect(r.nextInt(200), r.nextInt(200), r.nextInt(1800), r.nextInt(1600));
//		}
//		
    }
}
