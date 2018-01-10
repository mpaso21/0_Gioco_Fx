package utility;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;

public interface Command extends Serializable {
    void draw(GraphicsContext gc);
}
