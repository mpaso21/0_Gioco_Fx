package entity;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Sprite;

public class Bullet extends Sprite {

	public Bullet(double x, double y){
		super();
                super.setImageName("bullet");
                super.setImage(Assets.imagesMap.get(super.getImageName()));// il super mi ritorna il nome dell'immagine
                super.setPosition(x, y); //il get mi ritorna la chiava associata al nome
        }// posizione bullets in funzione della posizione del player nel momento in cui schiaccio

        @Override
        public void render(GraphicsContext gc) {
            super.render(gc);
        }
}