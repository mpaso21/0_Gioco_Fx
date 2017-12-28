package utility;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sprite {
	private Image image;  
    private Vector2d position;
    private Vector2d velocity;
    private double width;
    private double height;
    
    private boolean debug = true;
    
    public Sprite()
    {
    	position = new Vector2d(0, 0);   
    	velocity = new Vector2d(0, 0);
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setPosition(double x, double y)
    {
    	position.setX(x);
    	position.setY(y);
    }
    public void setPosition(Vector2d v)
    {
    	position.setX(v.getX());
    	position.setY(v.getY());
    }

    public void setVelocity(double x, double y)
    {
        velocity.setX(x);
        velocity.setY(y);
    }
    public void setVelocity(Vector2d v)
    {
        velocity.setX(v.getX());
        velocity.setY(v.getY());
    }

    public void addVelocity(double x, double y)
    {
    	velocity.addX(x);
    	velocity.addY(y);
    }
    public void addVelocity(Vector2d v)
    {
    	velocity.addX(v.getX());
    	velocity.addY(v.getY());
    }

    public void update(double time)
    {
    	position.addX(velocity.getX()*time);
    	position.addY(velocity.getY()*time);
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, position.getX(), position.getY() );

        if(debug) {
            gc.setStroke( Color.RED );
        	gc.strokeRect(position.getX(), position.getY(), width, height);
            gc.setStroke( Color.BLACK );
        }
    }//disegno immagine nella posizione corretta e con immagine giusta

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(position.getX(),position.getY(),width,height);
    }

    public boolean intersects(Sprite s)//le due immagini collidono si toccano o sovrappongono
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    public String toString()
    {
        return " Position: [" + position.getX() + "," + position.getY() + "]" 
        + " Velocity: [" + velocity.getX() + "," + velocity.getY() + "]";
    }
}
