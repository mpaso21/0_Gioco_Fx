package game;

import java.util.List;

import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import macroEntity.Background;
import macroEntity.Bullets;
import macroEntity.Enemies;
import macroEntity.Explosions;
import menu.FXMLMenuController;
import utility.Vector2d;

//contiene tutti gli oggetti del gioco
public class World {

    private Stage stage;
    private AnimationTimer at;

    public Player player;
    public Canvas canvas;

    private Enemies enemies;//oggetto privato non posso fare world.enemies e accedere ai metodi della classe
    private Bullets bullets;
    private Background backgrounds;
    private Explosions explosions;

    public World(Stage primaryStage) {
        this.stage = primaryStage;

        player = new Player();
        canvas = new Canvas(1600.0, 800.0);
        enemies = new Enemies();
        bullets = new Bullets(player);
        backgrounds = new Background();
        explosions = new Explosions();
    }

    //////////////////////// ENEMIES ///////////////////////////////////////
    public void updateEnemies(double elapsedTime) {
        enemies.update(elapsedTime);
    }
//	public void resetEnemies(double elapsedTime) {
//		enemies.reset(elapsedTime);
//	}

    public void renderEnemies(GraphicsContext gc) {
        enemies.render(gc);
    }

    public boolean intersectsEnemies() { //creo questo metoodo perch� player e enemies si "rivolgono" al world, non comunicano direttamente
        return enemies.intersects(player); //non metto paramentri perch� player ce l'ho nella classe
    }
    //////////////////////// ENEMIES ///////////////////////////////////////

    /////////////////////////BULLETS/////////////////////////////////////////
    public void createBullet() {
        bullets.create();
    }

    public void updateBullets(double elapsedTime) {
        bullets.update(elapsedTime);
    }

    public void renderBullets(GraphicsContext gc) {
        bullets.render(gc);
    }
//list è la lista delle posizioni dove generare esplosioni
    public void intersectsBullets() {
        List<Vector2d> list = bullets.intersects(enemies);
        if (!list.isEmpty()) {
//			System.out.println(list);	
            explosions.create(list);
        }
    }
    /////////////////////BULLETS/////////////////////////////////////////////////

    /////////////////////////////EXPLOSIONS///////////////////////////////////////
    public void updateExplosions(double elapsedTime, double t) {//(passo i due t per l'animazione
        explosions.update(elapsedTime, t);
    }

    public void renderExplosion(GraphicsContext gc) {
        explosions.render(gc);
    }
    //////////////////////////////EXPLOSIONS///////////////////////////////////

    ///////////////////////BACKGROUND//////////////////////////////////////////
    public void updateBackground(double elapsedTime) {
        backgrounds.update(elapsedTime);
    }

    public void renderBackground(GraphicsContext gc) {
        backgrounds.render(gc);
    }
    /////////////////////////BACKGROUNDS//////////////////////////////////////

    ////////////////////////////LOAD MENU///////////////////////////////////
    public void setAnimationTimer(AnimationTimer at) {
        this.at = at;
    }
    //viene chiamato quando passato i 4 sec del game over
    public void loadMenu() {
        try {
            //prima faccio il pezzo sotto poi chiamo il platform runlater che mi stoppa l'animazione del gioco
            Platform.runLater(() -> {
                at.stop();
            });

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/Fxml_menu.fxml"));
            Parent root = loader.load();
            FXMLMenuController controller = loader.getController();
            controller.init(stage, root);

        } catch (Exception ex) {
            System.err.println("UNABLE TO LOAD MAIN MENU");
        }
    }
    ///////////////////////////LOAD MENU////////////////////////////////////
}
