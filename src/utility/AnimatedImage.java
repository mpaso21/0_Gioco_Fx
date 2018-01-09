package utility;

import java.io.Serializable;
import javafx.scene.image.Image;

public class AnimatedImage implements Serializable {
    
    public Image[] frames;
    public double duration;//durata singolo frame
     
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}


/*
 * 
 * Esempio:
 * 
 * frames.lenght = 6
 * duration = 0.1
 * 
 * time viene passato in secondi e varia in questo modo:
 * 
 * 0.1 0.2 0.3 0.4 0.5 .... 1.1 1.2 1.3 .... X.1 X.2 X.3 ecc ecc
 * 
 * per il calcolo conta solo il decimale infatti succede questo:
 * 
 * X.1 % 0.6 ---> 0.1 / 0.1 ---> 1 frame[1]
 * X.2 % 0.6 ---> 0.2 / 0.1 ---> 2 frame[2]
 * X.6 % 0.6 ---> 0 / 0.1   ---> 0 frame[0]
 * 
 * X.7 % 0.6 ---> 0.1/0.1 ---> 1 frame[1]
 * 
 */
