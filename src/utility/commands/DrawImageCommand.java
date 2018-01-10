package utility.commands;

import javafx.scene.canvas.GraphicsContext;
import resources.Assets;
import utility.Command;

public class DrawImageCommand implements Command {
    
    String imgString;
    double x, y;

    public DrawImageCommand(String imgString, double x, double y) {
        this.imgString = imgString;
        this.x = x;
        this.y = y;
    }
       
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(Assets.imagesMap.get(imgString), x, y);    
    }
    
}
