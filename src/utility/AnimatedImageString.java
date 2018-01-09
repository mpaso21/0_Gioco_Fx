package utility;

import java.io.Serializable;

public class AnimatedImageString implements Serializable {
     
    public String[] frames;
    public double duration;//durata singolo frame
     
    public String getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}