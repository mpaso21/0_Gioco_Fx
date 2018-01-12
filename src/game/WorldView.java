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
        this.world = world; //group è il game controller: Il nodo padre della nuova scena, quello che vedo principalmente
        init(group);
    }
    
    private void init(Group group) {
        group.getChildren().add(world.canvas); //aggiungo canvas al group

        gc = world.canvas.getGraphicsContext2D(); //gc è già un oggetto della canvas e permette di disegnare, colore ecc.. di operare sulla canvas
//tramite graphics context disegno sulla canvas
        gc.setFill(Color.BLACK); //setto il colore dei fill (colore scritta, colore rettangolo)
    }
    //aggiorna
    //pulisco e ridisegno per 60 volte usando le caratteristiche degli oggetti di world

    public void update(double elapsedTime) {
        render();
    }

    
    float font = 25f;

    private void render() {
        gc.clearRect(0, 0, 1600, 800);
        world.renderBackground(gc);//disegno bakcground x primna cosa
        world.renderExplosion(gc); //se ci sono disegno esplosioni
        //gc.drawImage(Assets.background, 0, 0);
        world.renderEnemies(gc); //sopra nemici

        if (world.player.state != Player.State.GAME_OVER) {
            world.player.render(gc);//SE NON e morto disegno anche player + proiettili
            world.renderBullets(gc);
        } else {
            gc.setFill(Color.BLACK);//se è morto disegno la scritta game over
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            if(font < 110) {
                font += 0.3;
            }
            final Font theFont = Font.font("Bungee", FontWeight.BOLD, font);
            gc.setFont(theFont);
            gc.fillText("GAME OVER", 80, 300);
            gc.strokeText("GAME OVER", 80, 300);//finisce di essere chiamato il render quando chiamo il load menu che fa animation.stop
        }


    }

}
